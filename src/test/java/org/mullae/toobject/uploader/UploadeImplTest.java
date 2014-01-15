package org.mullae.toobject.uploader;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mullae.toobject.importer.Importer;
import org.mullae.toobject.parser.ExcelParser;
import org.mullae.toobject.parser.Parser;

import java.io.File;

public class UploadeImplTest extends TestCase {
    Uploader<DummyObject> uploader;

    @Before
    public void setUp() {
        uploader = new UploadeImpl();
    }
    @Test
    public void uploadTest() {
        Importer importer = new Importer.FileImporter(new File(""));
        Parser parser = new ExcelParser();
        DummyObject dummyObject = uploader.upload(importer, parser);
    }
}

