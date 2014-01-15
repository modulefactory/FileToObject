package org.mullae.toobject.parser;

import org.mullae.toobject.importer.Importer;

public interface Parser {
    <T> T parse(Importer importer, Class<T> clazz);
}
