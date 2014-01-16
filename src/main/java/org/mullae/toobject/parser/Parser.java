package org.mullae.toobject.parser;

import java.io.InputStream;

public interface Parser {
    <T> T parse(InputStream inputStream, Class<T> clazz);
}
