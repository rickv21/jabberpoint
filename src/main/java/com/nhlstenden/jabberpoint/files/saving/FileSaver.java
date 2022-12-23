package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.FileAccessor;

import java.io.File;
import java.io.IOException;

public abstract class FileSaver implements FileAccessor {

    public abstract void savePresentation(Presentation presentation, File file) throws IOException;

}
