package edu.odu.icat.controller;

import java.io.File;


/**
 * Created by Azooz on 4/26/14.
 */
public class Utils {
    public final static String icat = "icat";
    public final static String pdf = "pdf";

    /**
     *
     * @param file
     * @return Extension of file
     */

    public static String getExtension(File file){
        String extension = null;
        String s = file.getName();
        int integer = s.lastIndexOf(".");

        if (integer > 0 && integer < s.length() -1){
            extension = s.substring(integer + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * Return null if the path is invalid
     */

}
