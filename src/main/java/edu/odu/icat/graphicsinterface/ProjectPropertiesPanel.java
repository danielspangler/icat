package edu.odu.icat.graphicsinterface;

import javax.swing.*;
import javax.swing.border.Border;

import edu.odu.icat.controller.*;
import edu.odu.icat.model.Project;

import java.awt.*;

/**
 * Created by trueLove on 4/28/14.
 */
public class ProjectPropertiesPanel extends JPanel {

    private Project currentProject;
    private JTextField projectName;

    public ProjectPropertiesPanel()
    {
        currentProject = Control.getInstance().getCurrentProject();
        projectName = new JTextField(currentProject.getName());

        setLayout(new FlowLayout());
        add(new JLabel("Title"));
        add(projectName);

        add(new JLabel("Stuff"));
        setVisible(true);
    }
}
