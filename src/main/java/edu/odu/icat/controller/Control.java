package edu.odu.icat.controller;

import com.google.common.base.Strings;
import com.mxgraph.model.mxGeometry;
import edu.odu.icat.service.ConfigurationDAO;
import edu.odu.icat.testingdashboard.ModelView;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import edu.odu.icat.model.*;
import edu.odu.icat.service.ProjectDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Controller will interact with whatever
 */
public class Control {

    private static Control ourInstance = new Control();

    private Project currentProject;
    private String currentProjectPath;
    private ProjectDAO projectDAO = new ProjectDAO();
    private ConfigurationDAO configDAO = new ConfigurationDAO();
    private Configuration config = null;
    private List<String> entityClassifications = Arrays.asList("Problem", "Stakeholder", "Objective", "Attribute", "Resource");
    private double defaultForceWeight = 0.25;
    private int defaultEntityClassificationIndex = 1;
    private GraphEventListener graphEventListener = new GraphEventListener();

    public static Control getInstance() {
        return ourInstance;
    }

    private Control() {
        createProject();
        //new ModelView();
    }

    public void reset() {
        currentProjectPath = null;
        currentProject = null;
    }

    public Configuration getConfig() {
        if (config==null) {
            config = configDAO.getConfiguration();
        }
        return config;
    }

    public void saveConfig() {
        configDAO.saveConfiguration(getConfig());
    }

    public Project getCurrentProject() {
        if (currentProject == null) {
            currentProject = new Project("Name", "Description", "Author");
        }
        return currentProject;
    }

    public void saveCurrent() {
        if (!Strings.isNullOrEmpty(currentProjectPath)  && currentProject != null) {
            projectDAO.saveProject(currentProjectPath, currentProject);
        } else {
            throw new IllegalStateException("Could not find currentProjectPath or currentProject");
        }
    }

    public void saveCurrentAs(String newPath) {
        checkArgument(!Strings.isNullOrEmpty(newPath), "The path argument must be provided");
        if (currentProject!=null) {
            currentProjectPath = newPath;
            projectDAO.saveProject(newPath, currentProject);
            getConfig().addRecentProject(new Configuration.ProjectInfo(currentProject.getName(), currentProjectPath));
            saveConfig();
        } else {
            throw new IllegalStateException("Could not find the currentProject");
        }
    }

    public Project loadProject(String projectPath)
    {
        checkArgument(!Strings.isNullOrEmpty(projectPath), "The project path must be provided");
        currentProject = projectDAO.getProject(projectPath);
        currentProjectPath = projectPath;
        getConfig().addRecentProject(new Configuration.ProjectInfo(currentProject.getName(), currentProjectPath));
        saveConfig();
        return currentProject;
    }

    /**
     * Create a new project (a single instance)
     */
    public void createProject(){
        currentProject = new Project("Untitled Project", "This is test", "Dr. Patrick Hester ©.");
        projectDAO.saveProject("ICAT", currentProject);
    }

    public List<String> getEntityClassifications() {
        return entityClassifications;
    }

    public String getDefaultEntityClassification() {

        return entityClassifications.get(defaultEntityClassificationIndex);
    }

    public String getEntitySpecificClassification(int i){

        return entityClassifications.get(i);
    }

    public double getDefaultForceWeight() {
        return defaultForceWeight;
    }

    public mxEventSource.mxIEventListener getGraphEventListener() {
        return graphEventListener;
    }

    private class GraphEventListener implements mxEventSource.mxIEventListener {

        private void handleGeometryChange(mxGraphModel.mxGeometryChange change) {
            Object valueAsObject = ((com.mxgraph.model.mxCell)change.getCell()).getValue();
            if (valueAsObject instanceof Entity) {
                Entity entity = (Entity)valueAsObject;
                entity.getLocation().setX(change.getGeometry().getX());
                entity.getLocation().setY(change.getGeometry().getY());
            }
        }

        public void invoke(Object sender, mxEventObject evt) {
            List changes = (List)evt.getProperties().get("changes");
            // we have changes, now let's figure out what changed
            if (changes!=null) {
                List<mxGraphModel.mxTerminalChange> terminalChanges = new ArrayList<mxGraphModel.mxTerminalChange>();

                for (Object change : changes) {
                    if (change instanceof mxGraphModel.mxTerminalChange) {
                        terminalChanges.add((mxGraphModel.mxTerminalChange) change);
                    }
                    if (change instanceof mxGraphModel.mxGeometryChange) {
                        handleGeometryChange((mxGraphModel.mxGeometryChange) change);
                    }
                }
                if (terminalChanges.size()==2) {
                    // there were two changes, this means we are doing an insert
                    // NOTE: if there is a feature to swap direction, then we may need to account for that here as it may
                    //       result in a similar change set
                    Entity sourceEntity = null;
                    Entity destEntity = null;
                    if (terminalChanges.get(0).isSource()) {
                        sourceEntity = (Entity)((mxCell)terminalChanges.get(0).getTerminal()).getValue();
                        destEntity = (Entity)((mxCell)terminalChanges.get(1).getTerminal()).getValue();
                    } else {
                        sourceEntity = (Entity)((mxCell)terminalChanges.get(1).getTerminal()).getValue();
                        destEntity = (Entity)((mxCell)terminalChanges.get(0).getTerminal()).getValue();
                    }
                    Force force = new Force(sourceEntity, destEntity, getDefaultForceWeight());
                    ((mxCell)terminalChanges.get(0).getCell()).setValue(force);
                    getCurrentProject().addForce(force);
                } else if (terminalChanges.size()==1) {
                    // there was one change, so this is a change of one end of the edge
                    mxGraphModel.mxTerminalChange terminal = terminalChanges.get(0);
                    Force force = (Force)((mxCell)terminal.getCell()).getValue();
                    if (terminal.isSource()) {
                        force.setOrigin((Entity)((mxCell)terminal.getTerminal()).getValue());
                    } else {
                        force.setDestination((Entity) ((mxCell) terminal.getTerminal()).getValue());
                    }
                    //System.out.println("Only one changed");
                }
            }

            //System.out.println(evt.getName());
        }
    };



}
