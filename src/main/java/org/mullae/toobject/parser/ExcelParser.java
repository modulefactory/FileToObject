package org.mullae.toobject.parser;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class ExcelParser implements Parser {

    @Override
    public <T> T parse(InputStream inputStream, Class<T> clazz) {

        try {
            XSSFWorkbook hssfWorkbook = new XSSFWorkbook(inputStream);
            System.out.println(hssfWorkbook.getSheetAt(0).getRow(0));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }


        return null;
    }
}
