package org.mullae.toobject.parser;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mullae.toobject.property.Property;
import org.mullae.toobject.property.excelproperty.ExcelProperty;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XlsxParser implements Parser {


    @Override
    public <T, P extends Property> List<T> parse(InputStream inputStream, P p, Class<T> clazz) {
        //제네릭 다시 공부하기.
        ExcelProperty excelProp = (ExcelProperty) p;
        ExcelToObject excelToObject = new ExcelToObject();
        return excelToObject.getXlsxObjectList(inputStream, excelProp, clazz);
    }
}
