package edu.odu.icat.controller;

import edu.odu.icat.model.Project;
import edu.odu.icat.model.Entity;
import edu.odu.icat.model.Force;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul on 4/9/14.
 */
public class Control {

    private static Control ourInstance = new Control();
    Project currentProject = new Project("", "", "");

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

    }



}
