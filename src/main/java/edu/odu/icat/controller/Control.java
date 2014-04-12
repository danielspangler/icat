package edu.odu.icat.controller;

import edu.odu.icat.model.Project;
import edu.odu.icat.model.Entity;
import edu.odu.icat.model.Force;
import edu.odu.icat.service.ProjectDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller will interact with the File System and the
 */
public class Control {

    private static Control ourInstance = new Control();

    Project currentProject = new Project("", "", "");
    ProjectDAO projectDAO = new ProjectDAO();
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

        getProject();
    }

    public List<Entity> getEntities(){

        return new ArrayList<Entity>(currentProject.getEntities());
    }

    public List<Force> getForces(){

        return new ArrayList<Force>(currentProject.getForces());
    }

    private void getProject(){
        /**
         * get a project from the File System
         */

    }

    /**
     * Search algorithms for an existing project
     */
    public void searchForProject(String name, ProjectDAO prject){

    }

    /**
     * Locate an Entity
     */
    public void searchForEntity(){
        /**
         * Do need a search box for an Entity within the Workspace?
         */
    }


}
