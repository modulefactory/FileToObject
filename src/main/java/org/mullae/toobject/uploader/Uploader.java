package org.mullae.toobject.uploader;

import org.mullae.toobject.importer.FileImporter;
import org.mullae.toobject.parser.Parser;

public interface Uploader {
    <T> T upload(FileImporter importer, Parser parser, Class<T> clazz);
}
