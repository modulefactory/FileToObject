package org.mullae.toobject.uploader;

import org.mullae.toobject.parser.Parser;
import org.mullae.toobject.importer.Importer;

public class UploadeImpl implements Uploader {

    @Override
    public <T> T upload(Importer importer, Parser parser, Class<T> clazz) {
        return parser.parse(importer.imports(), clazz);
    }
}
