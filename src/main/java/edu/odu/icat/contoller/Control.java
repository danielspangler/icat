package edu.odu.icat.contoller;

import edu.odu.icat.model.*;
import edu.odu.icat.service.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ABDUL on 4/9/14.
 */
public class Control {
    private static Control ourInstance = new Control();
    public static Control getInstance() {
        return ourInstance;
    }


    private Project currentProject;
    private ProjectDAO projectDAO;
    private List<String> entityClassifications = Arrays.asList("Problem", "Stakeholder", "Objective", "Attribute", "Resource");

    private Control() {
        projectDAO = new ProjectDAO();
    }

    public Project getCurrentProject() {
        if (currentProject==null) {
            currentProject = new Project("Name", "Description", "Author");
        }
        return currentProject;
    }

    public void loadProject(String project)
    {
        currentProject = projectDAO.getProject(project);
    }

    public List<String> getEntityClassifications() {
        return entityClassifications;
    }

    public String getDefaultEntityClassification() {
        return entityClassifications.get(1);
    }

}
