package edu.odu.icat.graphicsinterface;

import javax.swing.*;
import javax.swing.border.Border;


import edu.odu.icat.controller.*;
import edu.odu.icat.model.Project;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Created by trueLove on 4/28/14.
 */
public class ProjectPropertiesPanel extends JPanel {

    private Project currentProject;
    private JTextField projectName;
    private JTextField projectAuthor;
    private JTextArea projectDescription;
    private JTextArea projectNotes;

    public ProjectPropertiesPanel()
    {
        currentProject = Control.getInstance().getCurrentProject();
        projectName = new JTextField(currentProject.getName());
        projectAuthor = new JTextField(currentProject.getAuthor());

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel TitlePanel = new JPanel();
        TitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        TitlePanel.add(new JLabel("Title"));
        TitlePanel.add(projectName);
        projectName.addActionListener(new TitleUpdate());

        JPanel AuthorPanel = new JPanel();
        AuthorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        AuthorPanel.add(new JLabel("Author"));
        AuthorPanel.add(projectAuthor);
        projectAuthor.addActionListener(new AuthorUpdate());

        JPanel DescriptionPanel = new JPanel();
        DescriptionPanel.setLayout(new BorderLayout());

        DescriptionPanel.add(new JLabel("Description"), BorderLayout.NORTH);
        projectDescription = new JTextArea(currentProject.getDescription());
        projectDescription.setSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE, 300));
        projectDescription.addKeyListener(new DescriptionUpdate());
        DescriptionPanel.add(new JScrollPane(projectDescription), BorderLayout.CENTER);

        JPanel NotesPanel = new JPanel();
        NotesPanel.setLayout(new BorderLayout());

        NotesPanel.add(new JLabel("Project Rationale"), BorderLayout.NORTH);
        projectNotes = new JTextArea(currentProject.getNotes());
        projectDescription.setSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE, 800));
        projectNotes.addKeyListener(new NotesUpdate());
        NotesPanel.add(new JScrollPane(projectNotes), BorderLayout.CENTER);

        add(TitlePanel);
        add(AuthorPanel);
        add(DescriptionPanel);
        add(NotesPanel);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMinimumSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE, 500));
        setVisible(true);
    }

    private class TitleUpdate implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            currentProject.setName(projectName.getText());
        }
    }

    private class AuthorUpdate implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            currentProject.setAuthor(projectAuthor.getText());
        }
    }

    private class DescriptionUpdate implements KeyListener
    {
        public void keyTyped(KeyEvent e){}

        public void keyPressed(KeyEvent e)
        {

        }

        public void keyReleased(KeyEvent e)
        {
            currentProject.setDescription(projectDescription.getText());
        }
    }

    private class NotesUpdate implements KeyListener
    {
        public void keyTyped(KeyEvent e){}

        public void keyPressed(KeyEvent e)
        {

        }

        public void keyReleased(KeyEvent e)
        {
            currentProject.setNotes(projectNotes.getText());
        }
    }
}
