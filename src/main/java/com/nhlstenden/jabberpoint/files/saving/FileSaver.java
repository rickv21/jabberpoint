package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.Presentation;

import java.io.File;
import java.io.IOException;

public abstract class FileSaver {

    public abstract void savePresentation(Presentation presentation, File file) throws IOException;

    public abstract String getExtension();
}
