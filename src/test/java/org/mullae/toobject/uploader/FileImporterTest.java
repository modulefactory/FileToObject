package org.mullae.toobject.uploader;

import org.junit.*;
import org.mullae.toobject.importer.model.Address;
import org.mullae.toobject.importer.FileImporter;
import org.mullae.toobject.parser.XlsParser;

import java.io.File;

/**
 * Created by yoe21c on 2014. 1. 17..
 */
public class FileImporterTest {

    FileImporter<Address> fileImporter;

    @Test
    public void testImport() {
        fileImporter.imports(getTestFolderPath() + "/test.xlsx", new XlsParser());
    }



    private String getTestFolderPath() {
        return this.getClass().getResource(File.separator + "sample").getPath() + File.separator;
    }
}
