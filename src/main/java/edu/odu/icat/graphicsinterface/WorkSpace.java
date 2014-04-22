/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.odu.icat.graphicsinterface;

import edu.odu.icat.analytics.AnalyticsEngine;
import edu.odu.icat.controller.Control;
import edu.odu.icat.graphicsinterface.editor.EditorActions;
import edu.odu.icat.service.*;
import edu.odu.icat.model.Entity;

import com.mxgraph.model.mxCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.List;


public class WorkSpace extends JFrame {


    protected GraphEditor graphComponent;

    private JPanel contentPane;
    private JPanel attributePane;
    private JSplitPane split;

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
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);



        setLayout(new BorderLayout());

        attributePane = new JPanel();
        attributePane.add(new JLabel("Nothing Selected"));
        graphComponent = new GraphEditor();

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, attributePane,
                graphComponent);
        split.setOneTouchExpandable(false);
        split.setDividerLocation(300);
        split.setDividerSize(6);
        split.setBorder(null);

        add(split, BorderLayout.CENTER);
        MenuButtons();

        graphComponent.getGraphComponent().getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Object cell = graphComponent.getGraphComponent().getCellAt(e.getX(), e.getY());

                if (cell != null && cell instanceof mxCell)
                {
                    Entity entity =(Entity) ((mxCell) cell).getValue();
                    updateAttributePane(entityAttributes(entity));
                }
            }
        });

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
                   updateAttributePane(AnalyticsEngine.getInstance().getAlgorithmDialog(name));
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
        printItem.addActionListener(new PrintAction());
        exportItem.addActionListener(new ExportAction());
        saveItem.addActionListener(new SaveAction());
        saveAsItem.addActionListener(new SaveAsAction());

        setJMenuBar(menubar);

        setTitle("WorkSpace");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center window
    }

    public void updateAttributePane(JComponent newComponent)
    {
        split.setLeftComponent(newComponent);
    }

    public JPanel entityAttributes(Entity entity)
    {
        JPanel newPanel = new JPanel();

        //Sets the Minimum Size of the Panel to 300 wide by 500 high
        newPanel.setMinimumSize(new Dimension(300,500));

        //Sets the Panel layout to a SpringLayout
        SpringLayout layout = new SpringLayout();
        newPanel.setLayout(layout);

        final JTextField titlePane = new JTextField("",10); //Creates a textfield to enter a title
        JTextField metaDataTextArea = new JTextField("",25); //Creates a textfield to enter Metadata
        final JLabel Name = new JLabel("Title:"); //Creates a label called Title:
        final JLabel Notes = new JLabel("Notes:"); //Creates a label called Notes:
        JMenuBar bar = new JMenuBar(); //Creates a menu bar called bar
        JCheckBox noncontrolCheckBox = new JCheckBox("Non-Controllable"); //Creates a checkbox for controlable entities.
        JCheckBox nonvisibleCheckBox = new JCheckBox("Non-Visible"); //Creates a checkbox for visible entities.
        JButton deleteButton = new JButton("Delete"); //Creates a button in order to delete entity data.

        //This constraint places the name 5 over from the top left corner of the Panel
        layout.putConstraint(SpringLayout.WEST,Name,5,SpringLayout.WEST, newPanel);
        layout.putConstraint(SpringLayout.NORTH,Name,5,SpringLayout.NORTH, newPanel);

        //This Constraint puts the titlePane 5 over from the Name Label and 5 under the Panel top y value
        layout.putConstraint(SpringLayout.WEST,titlePane,5,SpringLayout.EAST, Name);
        layout.putConstraint(SpringLayout.NORTH,titlePane,5,SpringLayout.NORTH, newPanel);

        //This constraint puts the menubar 5 over from the titlePane and 5 under the panels top y value
        layout.putConstraint(SpringLayout.WEST,bar,5,SpringLayout.EAST,titlePane);
        layout.putConstraint(SpringLayout.NORTH,bar,5,SpringLayout.NORTH,newPanel);

        //This constraint puts Notes label 5 from the west portion of the Panel but 30 units under the Name Label
        layout.putConstraint(SpringLayout.WEST,Notes,5,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,Notes,30,SpringLayout.SOUTH,Name);

        //This constraint puts the MedadataTextArea 5 from the west portion of the Panel and 50 units under the titlePane.
        layout.putConstraint(SpringLayout.WEST,metaDataTextArea,5,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,metaDataTextArea,50,SpringLayout.SOUTH,titlePane);

        //This constraint puts the control box 25 units from the west portion of the Panel and 75 units under the titlePane
        layout.putConstraint(SpringLayout.WEST,noncontrolCheckBox,25,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,noncontrolCheckBox,75,SpringLayout.SOUTH,titlePane);

        //This constraint puts the visible box 25 units from the west portion of the Panel and 100 units under the titlePane
        layout.putConstraint(SpringLayout.WEST,nonvisibleCheckBox,25,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,nonvisibleCheckBox,100,SpringLayout.SOUTH,titlePane);

        //This constraint puts the delete button 25 units from the west portion of the Panel and 150 units under the titlePane
        layout.putConstraint(SpringLayout.WEST,deleteButton,25,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,deleteButton,150,SpringLayout.SOUTH,titlePane);


/* Not Using this Anymore.
        titlePane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                titlePane.setText("");
            }
        });*/


        JMenu entityTypeMenu = new JMenu("Type");
        bar.add(entityTypeMenu);

        for(String s: edu.odu.icat.controller.Control.getInstance().getEntityClassifications())
        {
            entityTypeMenu.add(new JMenuItem(s));
        }

        newPanel.add(Name);
        newPanel.add(titlePane);
        newPanel.add(bar);
        newPanel.add(Notes);
        newPanel.add(metaDataTextArea);
        newPanel.add(nonvisibleCheckBox);
        newPanel.add(noncontrolCheckBox);
        newPanel.add(deleteButton, newPanel);

        setVisible(true);

        return newPanel;
    }

    /*
    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) {return NO_SUCH_PAGE; }


//        graphComponent.getGraphComponent().getGraph();
//        graphComponent.getGraphComponent().print(g);


        Graphics2D g2d = (Graphics2D)g;

        java.awt.geom.AffineTransform originalTransform = g2d.getTransform();

        double scaleX = pf.getImageableWidth() / graphComponent.getComponent(0).getWidth();
        double scaleY = pf.getImageableHeight() / graphComponent.getComponent(0).getHeight();
        // Maintain aspect ratio
        double scale = Math.min(scaleX, scaleY);
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.scale(scale, scale);

        graphComponent.getComponent(0).printAll(g2d);

        g2d.setTransform(originalTransform);

        // tell the caller that this page is part of the printed document
        return PAGE_EXISTS;
    }
    */


    //-------Action listener for load button
    class LoadAction implements ActionListener {
        JFileChooser fc = new JFileChooser();

        public void actionPerformed(ActionEvent e)
        {
            //JOptionPane.showMessageDialog(WorkSpace.this, "No Files Found.");
            if (fc.showOpenDialog(WorkSpace.this) == JFileChooser.APPROVE_OPTION)
            {
                File openFils = fc.getSelectedFile();
                // load the file here
                Control.getInstance().loadProject(openFils.getAbsolutePath());

            }
        }
    }

    //--------Action listener for exit button
    class QuitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            WorkSpace.this.dispose();
        }
    }

    //--------Action listener for print button
    class PrintAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            EditorActions.PrintAction.printComp(graphComponent.getGraphComponent());
        }
    }

    //--------Action listener for export button
    class ExportAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }

    }

    //-------Action listener for save button
    class SaveAction implements ActionListener {
        JFileChooser fc = new JFileChooser();

        public void actionPerformed(ActionEvent e)
        {
            ProjectDAO psaver = new ProjectDAO();
           // JOptionPane.showMessageDialog(WorkSpace.this, "No Files Found.");
            if (fc.showSaveDialog(WorkSpace.this) == JFileChooser.APPROVE_OPTION)
            {
                File saveFils = fc.getSelectedFile();
                psaver.saveProject(saveFils.getAbsolutePath(), edu.odu.icat.controller.Control.getInstance().getCurrentProject());
            }
        }
    }

    //-------Action listener for save as button
    class SaveAsAction implements ActionListener {
        JFileChooser fc = new JFileChooser();

        public void actionPerformed(ActionEvent e)
        {
            ProjectDAO psaver = new ProjectDAO();
            // JOptionPane.showMessageDialog(WorkSpace.this, "No Files Found.");
            if (fc.showSaveDialog(WorkSpace.this) == JFileChooser.APPROVE_OPTION)
            {
                File saveFils = fc.getSelectedFile();
                psaver.saveProject(saveFils.getAbsolutePath(), edu.odu.icat.controller.Control.getInstance().getCurrentProject());
            }
        }
    }


}
