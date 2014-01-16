package org.mullae.toobject.parser;

import org.mullae.toobject.importer.Importer;

import java.io.InputStream;

public interface Parser {
    <T> T parse(InputStream inputStream, Class<T> clazz);
}
