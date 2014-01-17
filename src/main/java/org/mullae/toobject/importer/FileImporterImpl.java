package org.mullae.toobject.importer;

import org.mullae.toobject.importer.model.Address;
import org.mullae.toobject.parser.Parser;
import org.mullae.toobject.property.Property;
import org.mullae.toobject.property.excelproperty.ExcelProperty;

import java.io.*;

public class  FileImporterImpl<T> implements FileImporter<T> {

    @Override
    public T imports(File file, Parser parser) {
        BufferedInputStream fileInputStream = getFileInputStream(file);
        return (parser.parse(fileInputStream, new ExcelProperty(), Address.class)).get(0);

    }

    private BufferedInputStream getFileInputStream(File file) {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bis;
    }
}