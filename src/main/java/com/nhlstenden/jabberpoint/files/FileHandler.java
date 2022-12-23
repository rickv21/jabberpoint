package com.nhlstenden.jabberpoint.files;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.loading.FileLoader;
import com.nhlstenden.jabberpoint.files.loading.JSONLoader;
import com.nhlstenden.jabberpoint.files.loading.XMLLoader;
import com.nhlstenden.jabberpoint.files.saving.FileSaver;
import com.nhlstenden.jabberpoint.files.saving.XMLSaver;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FileHandler {

    private final Set<FileLoader> fileLoaders = new HashSet<>();
    private final Set<FileSaver> fileSavers = new HashSet<>();

    public FileHandler(){
        loadLoaders();
        loadSavers();
    }

    public void loadFile(Presentation presentation, String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            JOptionPane.showMessageDialog(null,
                    "The file " + fileName + "does not exist.", "IO Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String fileExtension = String.valueOf(Optional.of(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1)));

        for(FileLoader loader : fileLoaders){
            if(fileExtension.equals(loader.getExtension())){
                loader.loadPresentation(presentation, file);
                return;
            }
        }

        JOptionPane.showMessageDialog(null,
                "The " + fileExtension + " extension is not supported.", "Unsupported file", JOptionPane.ERROR_MESSAGE);
    }

    public void saveFile(Presentation presentation, String fileName){
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            int answer = JOptionPane.showConfirmDialog(null,"The file " + fileName + "already exists, do you want to overwrite it?");
            if(answer != JOptionPane.YES_OPTION){
                return;
            }
        }
        String fileExtension = String.valueOf(Optional.of(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1)));

        for(FileSaver saver : fileSavers){
            if(fileExtension.equals(saver.getExtension())){
                try {
                    saver.savePresentation(presentation, file);
                    return;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "File save error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(null,
                "The " + fileExtension + " extension is not supported for saving.", "Unsupported file", JOptionPane.ERROR_MESSAGE);
    }

    private void loadLoaders(){
        fileLoaders.add(new XMLLoader());
        fileLoaders.add(new JSONLoader());
    }

    private void loadSavers(){
        fileSavers.add(new XMLSaver());
    }

    public Set<FileLoader> getFileLoaders() {
        return fileLoaders;
    }

    public Set<FileSaver> getFileSavers() {
        return fileSavers;
    }
}
