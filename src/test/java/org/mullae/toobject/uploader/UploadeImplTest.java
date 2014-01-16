package org.mullae.toobject.uploader;

import org.junit.Before;
import org.junit.Test;
import org.mullae.toobject.importer.FileImporter;
import org.mullae.toobject.parser.ExcelParser;
import org.mullae.toobject.parser.Parser;

import java.io.File;

public class UploadeImplTest{

    Uploader uploader;

    @Before
    public void setUp() {
        uploader = new UploadeImpl();
    }
    @Test
    public void uploadTest() {
        FileImporter importer = new FileImporter(new File(getTestFolderPath() + "/test.xlsx"));
        Parser parser = new ExcelParser();
        DummyObject dummyObject = uploader.upload(importer, parser, DummyObject.class);
    }


    private String getTestFolderPath() {
        return this.getClass().getResource(File.separator + "sample").getPath() + File.separator;
    }
}

