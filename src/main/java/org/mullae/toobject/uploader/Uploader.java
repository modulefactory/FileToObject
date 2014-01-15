package org.mullae.toobject.uploader;

import org.mullae.toobject.Model;
import org.mullae.toobject.parser.Parser;
import org.mullae.toobject.importer.Importer;

public interface Uploader {
    <T> T upload(Importer importer, Parser parser, Class<T> clazz);
}
