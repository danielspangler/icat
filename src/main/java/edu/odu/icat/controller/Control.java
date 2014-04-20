package edu.odu.icat.controller;

import edu.odu.icat.model.*;
import edu.odu.icat.service.ProjectDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller will interact with whatever
 */
public class Control {

    private static Control ourInstance = new Control();

    private Project currentProject;
    private ProjectDAO projectDAO = new ProjectDAO();
    private List<String> entityClassifications = Arrays.asList("Problem", "Stakeholder", "Objective", "Attribute", "Resource");
    private int defaultForceWeight = 1;
    private int defaultEntityClassificationIndex = 1;

    public static Control getInstance() {
        return ourInstance;
    }

    private Control() {
        createProject();
    }


    public Project getCurrentProject() {
        if (currentProject == null) {
            currentProject = new Project("Name", "Description", "Author");
        }
        return currentProject;
    }

    //return the list of Entities
    public List<Entity> getEntities(){

        return new ArrayList<Entity>(currentProject.getEntities());
    }

    //return the list of Forces
    public List<Force> getForces(){

        return new ArrayList<Force>(currentProject.getForces());
    }

    public void loadProject(String project)
    {
        currentProject = projectDAO.getProject(project);
    }

    /**
     * Create a new project (a single instance)
     */
    public void createProject(){
        currentProject = new Project("Untitled Project", "This is test", "Dr. Patric Hester ©");
        projectDAO.saveProject("ICAT", currentProject);
    }

    public List<String> getEntityClassifications() {
        return entityClassifications;
    }

    public String getDefaultEntityClassification() {
        return entityClassifications.get(defaultEntityClassificationIndex);
    }

    public int getDefaultForceWeight() {
        return defaultForceWeight;
    }
}
