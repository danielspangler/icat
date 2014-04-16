package edu.odu.icat.contoller;

import edu.odu.icat.model.*;
import edu.odu.icat.service.*;

import java.util.List;

/**
 * Created by ABDUL on 4/9/14.
 */
public class Control {
    private static Control ourInstance = new Control();
    private Project currentProject;
    private ProjectDAO projectDAO;
    public static Control getInstance() {
        return ourInstance;
    }

    private Control() {
        projectDAO = new ProjectDAO();
    }

    public void loadProject(String project)
    {
        currentProject = projectDAO.getProject(project);
    }

    public List<Entity> getEntities()
    {
        return currentProject.getEntities();
    }

    public List<Force> getForces()
    {
        return currentProject.getForces();
    }
}
