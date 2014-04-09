/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.odu.icat.graphicsinterface;

import edu.odu.icat.graphicsinterface.GraphEditor;
import edu.odu.icat.analytics.AnalyticsAlgorithm;
import edu.odu.icat.analytics.AnalyticsEngine;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JScrollBar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.List;


public class WorkSpace extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkSpace frame = new WorkSpace();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WorkSpace() {
		setTitle("Workspace");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 600);
        contentPane = new GraphEditor();
		setContentPane(contentPane);
        MenuButtons();
	}

    private JPopupMenu m_popup = new JPopupMenu();
    
    public void MenuButtons() {


        JMenu reportsMenu = new JMenu("Reports");
        List<String> list = AnalyticsEngine.getInstance().getAlgorithms();  //get list of loaded algorithms
        for(String s: list)
        {
            final String name = s;
            JMenuItem temp = new JMenuItem(s);
            temp.setEnabled(true);
            //temp.setAccelerator(KeyStroke.getKeyStroke("control I"));

            reportsMenu.add(temp);
            temp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    AnalyticsEngine.getInstance().runAlgorithm(name);       //run selected algorithm
                }
            });
        }

        JMenuItem saveItem = new JMenuItem("Save");
            saveItem.setMnemonic('S');
            saveItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
        JMenuItem saveAsItem = new JMenuItem("Save As");
            saveAsItem.setMnemonic('A');
            saveAsItem.setAccelerator(KeyStroke.getKeyStroke("control A"));
        JMenuItem loadItem = new JMenuItem("Load");
            loadItem.setMnemonic('L');
            loadItem.setAccelerator(KeyStroke.getKeyStroke("control L"));
        JMenuItem exportItem = new JMenuItem("Export");
            exportItem.setMnemonic('E');
            exportItem.setAccelerator(KeyStroke.getKeyStroke("control E"));
        JMenuItem printItem = new JMenuItem("Print");
            printItem.setMnemonic('P');
            printItem.setAccelerator(KeyStroke.getKeyStroke("control P")); 
        JMenuItem quitItem = new JMenuItem("Exit");
            quitItem.setMnemonic('X');
            quitItem.setAccelerator(KeyStroke.getKeyStroke("control X"));

        //Build  menubar, menus, and add menuitems.
        JMenuBar menubar = new JMenuBar();  // Create new menu bar
            JMenu fileMenu = new JMenu("File"); // Create new menu
                fileMenu.setMnemonic('F');
                menubar.add(fileMenu);      // Add menu to the menubar
                fileMenu.add(saveItem);     // Add menu item to the menu
                fileMenu.add(saveAsItem);
                fileMenu.add(loadItem);
                fileMenu.add(exportItem);
                fileMenu.add(printItem);
                fileMenu.addSeparator();    // Add separator line to menu
                fileMenu.add(quitItem);

                fileMenu.setMnemonic('R');
                menubar.add(reportsMenu);

        //Add listeners to menu items
        loadItem.addActionListener(new LoadAction());
        quitItem.addActionListener(new QuitAction());


        //Add the (unused) text area to the content pane
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(contentPane, BorderLayout.CENTER);

        //Add menu items to popup menu, add popup menu to text area
        m_popup.add(new JMenuItem("Testing"));
        contentPane.setComponentPopupMenu(m_popup);

        //Set the JFrame's content pane and menu bar
        setContentPane(content);
        setJMenuBar(menubar);

        setTitle("WorkSpace");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center window
    }
    
    //-------Action listener for load button
    class LoadAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(WorkSpace.this, "No Files Found.");
        }
    }

    //--------Action listener for exit button
    class QuitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            WorkSpace.this.dispose();
        }
    }
}
