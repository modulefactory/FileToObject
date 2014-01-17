package org.mullae.toobject.parser;

import org.mullae.toobject.property.Property;

import java.io.InputStream;
import java.util.List;

public interface Parser {
    <T, P extends Property> List<T> parse(InputStream inputStream, P p, Class<T> clazz);
}
