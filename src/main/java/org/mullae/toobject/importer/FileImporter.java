package org.mullae.toobject.importer;

import java.io.*;

public class FileImporter implements Importer {

    private File file;

    public FileImporter(File file) {
        this.file = file;
    }

    @Override
    public InputStream imports() {
        InputStream inputStream = getFileInputStream();
        return inputStream;
    }

    private BufferedInputStream getFileInputStream() {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bis;
    }
}