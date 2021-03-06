package org.mullae.toobject.parser;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mullae.toobject.property.ExcelToObjectException;
import org.mullae.toobject.property.excelproperty.ClassType;
import org.mullae.toobject.property.excelproperty.Column;
import org.mullae.toobject.property.excelproperty.ExcelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 엑셀데이터를 객체로 변환한다.
 * 보통 복수의 데이터를 변환하므로 List형식을 먼저 진행하고, 필요에 따라 메서드를 추가시켜나간다.
 * 여기서 T는 필요없는 구문이다 어떻게 제거해야 하는지 파악한다.<br/>
 *
 * @param
 */
public class ExcelToObject {
    private static Logger logger = LoggerFactory.getLogger(ExcelToObject.class);

    /**
     * 엑셀데이터를 기준으로 List값을 반환한다.
     * <b>리팩토링 필요</b>
     *
     * @param excelProperty
     * @param clazz
     * @return
     */
    public <T> List<T> getObjectList(Sheet sheet, ExcelProperty excelProperty, Class<T> clazz) {
        //최종 결과가 여기 저장된다.
        List<T> resultObjectList = new ArrayList<>();

        // 첫번째 시트의 헤더 로우을 기반으로 column을 가져온다.
        Row row = sheet.getRow(0);
        Map<Integer, Column> columnMap = getColumnMap(excelProperty, row);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            T resultObject = createObject(sheet.getRow(i), columnMap, clazz);
            resultObjectList.add(resultObject);
        }

        return resultObjectList;
    }


    /**
     * 엑셀 데이터를 가져온다.
     * <b>리팩토링 필요</b>
     *
     * @param excelProperty
     * @param clazz
     * @return
     */
    public <T> List<T> getXlsObjectList(HSSFWorkbook hssfWorkbook, ExcelProperty excelProperty, Class<T> clazz) {
        // 첫번째 시트만 가져온다.
        Sheet sheet = hssfWorkbook.getSheetAt(0);
        return getObjectList(sheet, excelProperty, clazz);
    }

    /**
     * inputStream을 추출 데이터를 가지고 온다.
     *
     * @param inputStream
     * @param excelProperty
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getXlsObjectList(InputStream inputStream, ExcelProperty excelProperty, Class<T> clazz) {
        excelToObjectValidate(inputStream, excelProperty, clazz);
        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
            return getXlsObjectList(hssfWorkbook, excelProperty, clazz);
        } catch (IOException e) {
            throw new ExcelToObjectException("IOException발생", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     *
     * @param xssfWorkbook
     * @param excelProperty
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getXlsxObjectList(XSSFWorkbook xssfWorkbook, ExcelProperty excelProperty, Class<T> clazz) {
        // 첫번째 시트만 가져온다.
        Sheet sheet = xssfWorkbook.getSheetAt(0);
        return getObjectList(sheet, excelProperty, clazz);
    }

    public <T> List<T> getXlsxObjectList(InputStream inputStream, ExcelProperty excelProperty, Class<T> clazz) {
        excelToObjectValidate(inputStream, excelProperty, clazz);
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            return  getXlsxObjectList(xssfWorkbook, excelProperty, clazz);
        } catch (IOException e) {
            throw new ExcelToObjectException("IOException발생", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }



    /**
     * 엑셀 업로드에서 사용될 컬럼 맵을 생성한다.
     *
     * @param excelProperty
     * @param row
     * @return
     */
    private Map<Integer, Column> getColumnMap(ExcelProperty excelProperty, Row row) {
        Map<Integer, Column> columnMap = new HashMap<>();
        List<Column> columnList = excelProperty.getColumnList();

        //상단의 컬럼을 순환하면서 셀값과 매칭되는 객체속성을 가져올 수 있도록 한다.
        //복잡도가 크게 높지 않으므로, 단순 for문을 사용하여 진행하도록 한다
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            String headerCellValue = row.getCell(i).getStringCellValue();
            for (Column column : columnList) {
                if (column.getAliasName().equals(headerCellValue)) {
                    //컬럼형식을 가져온다.
                    columnMap.put(new Integer(i), column);
                    break;
                }
            }
        }

        return columnMap;
    }


    /**
     * 어노테이션 값을 기반으로 데이터를 가져온다.
     *
     * @param row
     * @param columnMap
     * @param className
     * @param <T>
     * @return
     */
    private <T> T createObject(Row row, Map<Integer, Column> columnMap, Class<T> className) {
        T objInstance = getObjectInstance(className);

        Iterator<Integer> columnMapIter = columnMap.keySet().iterator();
        while (columnMapIter.hasNext()) {
            int cellIndex = columnMapIter.next();
            Column column = columnMap.get(cellIndex);
            String propertyName = column.getPropertyName();

            logger.debug("작업 대상 셀 : " + cellIndex + "Processing cell propertyName" + propertyName);
            Cell cell = row.getCell(cellIndex);

            //cell값이 존재한다면 아래 구문을 실행한다.
            if (cell != null) {
                Object cellValue = generateObjectBaseOnProperty(column, cell);
                setObjectProperty(objInstance, propertyName, cellValue);
            }

        }
        return objInstance;
    }

    private <T> void setObjectProperty(T objInstance, String propertyName, Object cellValue) {
        //결과값을 insert 처리.
        try {
            PropertyUtils.setSimpleProperty(objInstance, propertyName, cellValue);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ExcelToObjectException("excelToObject Error.", e);
        }
    }

    /**
     * className.... 을 기준으로 객체를 생성한다.
     *
     * @param className
     * @param <T>
     * @return
     */
    private <T> T getObjectInstance(Class<T> className) {
        Class<T> obj;
        try {
            obj = (Class<T>) Class.forName(className.getName());
        } catch (ClassNotFoundException e) {
            throw new ExcelToObjectException("cannot find class", e);
        }

        T objInstance;
        try {
            objInstance = obj.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExcelToObjectException("excelToObject Error.", e);
        }
        return objInstance;
    }

    //CellValue를 기반으로 데이터를 가져옵니다.
    private Object generateObjectBaseOnProperty(Column column, Cell cell) {
        Object cellValue = null;

        //리팩터링 필요...
        if (column.getClassType() == ClassType.STRING) {
            cellValue = cell.getStringCellValue();
        } else if (column.getClassType() == ClassType.INTEGER) {
            cellValue = new Double(cell.getNumericCellValue()).intValue();
        } else if (column.getClassType() == ClassType.LONG) {
            cellValue = new Double(cell.getNumericCellValue()).longValue();
        } else if (column.getClassType() == ClassType.FLOAT) {
            cellValue = new Float(cell.getNumericCellValue()).floatValue();
        } else if (column.getClassType() == ClassType.DOUBLE) {
            cellValue = new Float(cell.getNumericCellValue()).doubleValue();
        }

        return cellValue;
    }

    private <T> void excelToObjectValidate(InputStream inputStream, ExcelProperty excelProperty, Class<T> clazz) {
        if (inputStream == null) {
            throw new ExcelToObjectException("inputStream is null!!");
        } else if (excelProperty == null) {
            throw new ExcelToObjectException("excelProperty is null!!");
        } else if (clazz == null) {
            throw new ExcelToObjectException("class is null!!");
        }
    }
}
