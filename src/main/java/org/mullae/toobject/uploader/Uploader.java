package org.mullae.toobject.uploader;

import org.mullae.toobject.importer.Importer;
import org.mullae.toobject.parser.Parser;

public interface Uploader {
    <T> T upload(Importer importer, Parser parser, Class<T> clazz);
}
