package org.mullae.toobject.importer;

import org.mullae.toobject.Model;
import org.mullae.toobject.Param;

import java.io.*;

public interface Importer {
    InputStream imports();
}

class FileImporter implements Importer {
    private File file;

    public FileImporter(File file) {
        this.file = file;
    }

    @Override
    public InputStream imports() {
        InputStream inputStream = getFileInputStream();
        return null;
    }

    private BufferedInputStream getFileInputStream() {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(new File("")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bis;
    }
}
