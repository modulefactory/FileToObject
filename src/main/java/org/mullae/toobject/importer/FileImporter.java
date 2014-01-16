package org.mullae.toobject.importer;

import org.mullae.toobject.parser.Parser;

import java.io.File;

public interface FileImporter<T> {

    T imports(File file, Parser parser);
}
