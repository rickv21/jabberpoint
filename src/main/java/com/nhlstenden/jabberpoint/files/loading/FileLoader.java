package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.FileAccessor;

import java.io.File;

public abstract class FileLoader implements FileAccessor {

    public abstract void loadPresentation(Presentation presentation, File file);
}
