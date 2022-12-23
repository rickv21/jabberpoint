package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.Presentation;

import java.io.File;

public abstract class FileLoader {

    public abstract void loadPresentation(Presentation presentation, File file);

    public abstract String getExtension();
}
