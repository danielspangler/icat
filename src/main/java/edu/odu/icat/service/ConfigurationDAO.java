package edu.odu.icat.service;

import com.thoughtworks.xstream.XStream;
import edu.odu.icat.model.Configuration;

import java.io.*;

/**
 * Created by daniel on 4/26/14.
 */
public class ConfigurationDAO {
    XStream xstream = new XStream();

    private File getConfigFile() {
        String userHomePath = System.getProperty("user.home");
        File userHome = new File(userHomePath);
        File icatDir = new File(userHome, ".icat");
        if (!icatDir.exists()) {
            icatDir.mkdir();
        }
        File configFile = new File(icatDir, "config.xml");
        return configFile;
    }

    public Configuration getConfiguration() {

        FileReader reader;
        try {
            reader = new FileReader(getConfigFile());
            return (Configuration)xstream.fromXML(reader);
        } catch (FileNotFoundException e) {
            System.out.println("No previous configuration found, creating new");
            return new Configuration();
        }
    }

    public void saveConfiguration(Configuration config) {

        FileWriter writer;
        try {
            writer = new FileWriter(getConfigFile(), false);
            xstream.toXML(config, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception while writing a project to the file system.", e);
        }
    }


}
