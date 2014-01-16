package org.mullae.toobject.importer;

import org.mullae.toobject.parser.Parser;

import java.io.*;

public class FileImporterImpl<T> implements FileImporter<T> {

    @Override
    public T imports(File file, Parser parser) {
        BufferedInputStream fileInputStream = getFileInputStream(file);
        parser.parse(fileInputStream, T.class);
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