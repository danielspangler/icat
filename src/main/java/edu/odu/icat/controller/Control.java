package edu.odu.icat.controller;

import edu.odu.icat.model.*;
//import edu.odu.icat.model.Project;
import edu.odu.icat.service.ProjectDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller will interact with whatever
 */
public class Control {

    private static Control ourInstance = new Control();

    Project currentProject = new Project("Untitled Project", "This is just a test", "Dr, Patrick Hester");
    ProjectDAO projectDAO = new ProjectDAO();
    Entity entity = new Entity("Entity", "Type");
    /**
     * Case of Multiple threads
     * *
     * if (ourInstance == null){
     *     synchronized(Control.class){
     *         if (ourInstance == null){
     *             ourInstance = new Control();
     *         }
     *     }
     * }
     */

    public static Control getInstance() {
        return ourInstance;
    }

    private Control() {

        createProject("Path");
    }

    //return the list of Entities
    public List<Entity> getEntities(){

        return new ArrayList<Entity>(currentProject.getEntities());
    }


    public List<Force> getForces(){

        return new ArrayList<Force>(currentProject.getForces());
    }

    /**
     * Create a new project (a single instance)
     */
    public void createProject(String path){
        projectDAO.saveProject(path, currentProject);
    }




}
