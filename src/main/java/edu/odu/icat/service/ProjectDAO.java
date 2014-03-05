package edu.odu.icat.service;

import edu.odu.icat.model.Project;
import edu.odu.icat.model.ProjectSerializer;

import java.io.*;

import static com.google.common.base.Preconditions.*;

/**
 * The ProjectDAO provides the ability to get, save and search Projects.
 *
 */
public class ProjectDAO {

    private ProjectSerializer serializer = new ProjectSerializer();

    public Project getProject(String path) {
        File f = new File(path);
        checkArgument(f.exists(), "File does not exist at the specified path: %s", path);
        checkArgument(f.isFile(), "The provided path does not point to a file: %s", path);
        FileReader reader;
        try {
            reader = new FileReader(f);
            return serializer.deserializeFromXML(reader);
        } catch (FileNotFoundException e) {
            // not handling this more elegantly because we've already checked for this
            throw new RuntimeException(e);
        }
    }

    public void saveProject(String path, Project project) {
        File f = new File(path);
        checkArgument(!f.isDirectory(), "Path cannot be a directory");
        FileWriter writer;
        try {
            writer = new FileWriter(f, false);
            serializer.serializeToXML(project, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception while writing a project to the file system.", e);
        }
    }

    /**
     * This is primarily for testing purposes.
     * @param serializer the serializer that should be used
     */
    void setSerializer(ProjectSerializer serializer) {
        checkArgument(serializer!=null, "The serializer may not be null");
        this.serializer = serializer;
    }

}
