package edu.odu.icat.controller;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Azooz on 4/26/14.
 */
public abstract class FileExtensions extends FileFilter{
    public boolean approve(File file){
        if (file.isDirectory()){
            return true;
        }

        String Extension = Utils.getExtension(file);
        if (Extension != null){
            if (Extension.equals(Utils.icat) || Extension.equals(Utils.pdf))
                return true;
            else {
                return false;
            }
        }

        return false;
    }

    public String getDescription(){
        return "ICAT Files";
    }
}
