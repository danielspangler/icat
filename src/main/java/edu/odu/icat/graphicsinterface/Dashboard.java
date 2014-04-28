/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.odu.icat.graphicsinterface;

import edu.odu.icat.controller.Control;
import edu.odu.icat.model.Configuration;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Dashboard extends JFrame {

	private JPanel contentPane;
    private WorkSpace currentWorkspace;

	/**
	 * Create the frame.
	 */
	public Dashboard() {
		setTitle("Dashboard");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		setBounds(100, 100, 507, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
        BoxLayout layout = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);
        contentPane.setLayout(layout);

        JLabel lblIcatdashboard = new JLabel("ICAT - Dashboard");
        lblIcatdashboard.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(lblIcatdashboard);
        contentPane.add(Box.createRigidArea(new Dimension(0,5)));


        try{
            BufferedImage myPicture = ImageIO.read(Dashboard.class.getResource("/logo.png"));
            JLabel logo = new JLabel(new ImageIcon(myPicture.getScaledInstance(150,75,Image.SCALE_SMOOTH)));
            logo.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPane.add(logo);
        }
        catch(IOException e){System.out.println("File not found: logo.png");}


        contentPane.add(Box.createRigidArea(new Dimension(0,40)));

        JButton btnNewButton = new JButton("New Project");
        btnNewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentWorkspace==null || !currentWorkspace.isVisible()) {
                    currentWorkspace = new WorkSpace();
                    currentWorkspace.setVisible(true);
                    Control controller = Control.getInstance();
                    controller.createProject();

                }

            }
        });
        contentPane.add(Box.createRigidArea(new Dimension(0,5)));
        contentPane.add(btnNewButton);

        JButton loadButton = new JButton("Load Project");
        loadButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                if (currentWorkspace==null || !currentWorkspace.isVisible()) {
                    currentWorkspace = new WorkSpace();
                    currentWorkspace.load();
                    currentWorkspace.setVisible(true);
                }
            }
        });
        contentPane.add(Box.createRigidArea(new Dimension(0,5)));
        contentPane.add(loadButton);


        java.util.List<Configuration.ProjectInfo> recentProjects = Control.getInstance().getConfig().getRecentProjects();
        if (!recentProjects.isEmpty()) {

            final Vector<String> options = new Vector<String>();
            options.add("<Open Recent Project>");
            for (Configuration.ProjectInfo info : recentProjects) {
                options.add(info.getPath());

            }
            final JComboBox recentProjectComboBox = new JComboBox(options) {
                /**
                 * @inherited <p>
                 */
                public Dimension getMaximumSize() {
                    Dimension max = super.getMaximumSize();
                    max.height = getPreferredSize().height;
                    return max;
                }
            };
            recentProjectComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent itemEvent) {
                    String selected = (String)recentProjectComboBox.getSelectedItem();
                    if (selected != options.firstElement()) {
                        if (currentWorkspace==null || !currentWorkspace.isVisible()) {
                            currentWorkspace = new WorkSpace();
                            currentWorkspace.load(selected);
                            currentWorkspace.setVisible(true);

                        }
                    }
                }
            });
            recentProjectComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            recentProjectComboBox.setAlignmentX(Component.TOP_ALIGNMENT);
            contentPane.add(Box.createRigidArea(new Dimension(0,5)));
            contentPane.add(recentProjectComboBox);
        }
	}
}
    

