/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.odu.icat.graphicsinterface;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;
import com.sun.org.apache.xalan.internal.xsltc.runtime.StringValueHandler;
import edu.odu.icat.analytics.AnalyticsEngine;
import edu.odu.icat.controller.Control;
import edu.odu.icat.graphicsinterface.editor.EditorActions;
import edu.odu.icat.model.Entity;
import edu.odu.icat.model.Force;
import edu.odu.icat.model.Project;
import sun.awt.resources.awt;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import edu.odu.icat.testingdashboard.*;
import edu.odu.icat.graphicsinterface.ProjectPropertiesPanel;

//import edu.odu.icat.controller.Utils;
//import javafx.scene.control.Cell;


public class WorkSpace extends JFrame {


    protected GraphEditor graphComponent;

    private JPanel contentPane;
    private JPanel attributePane;
    private JSplitPane split;

    private ModelView TestingDashboard;

    public static int MINIMUM_PANEL_SIZE = 300;

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
        TestingDashboard = new ModelView();

		setTitle("Workspace");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/resources/logo.png"));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                super.windowClosed(windowEvent);
                Control.getInstance().reset();
            }
        });
		setBounds(100, 100, 850, 600);

        setLayout(new BorderLayout());

        attributePane = new JPanel();
        attributePane.add(new JLabel("Nothing Selected"));
        graphComponent = new GraphEditor();

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, attributePane,
                graphComponent);
        split.setOneTouchExpandable(false);
        split.setDividerLocation(WorkSpace.MINIMUM_PANEL_SIZE);
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
                    Object obj = ((mxCell)cell).getValue();
                    if(obj instanceof Entity)
                        updateAttributePane(entityAttributes(graphComponent, (mxCell)cell));
                    else if(obj instanceof edu.odu.icat.model.Force)
                    {
                        updateAttributePane(forceAttributes(((mxCell)cell), graphComponent));
                    }
                }
                else 
                {
                    attributePane = new JPanel();
                    attributePane.add(new JLabel("Nothing Selected"));
                    attributePane.setMinimumSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE,500));
                	updateAttributePane(attributePane);
                }
            }
        });

	}
    
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
            saveAsItem.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
        JMenuItem exportItem = new JMenuItem("Export As Image");
            exportItem.setMnemonic('E');
            exportItem.setAccelerator(KeyStroke.getKeyStroke("control E"));
        JMenuItem pageItem = new JMenuItem("Page Setup");
            pageItem.setMnemonic('I');
            pageItem.setAccelerator(KeyStroke.getKeyStroke("control I"));
        JMenuItem printItem = new JMenuItem("Print");
            printItem.setMnemonic('P');
            printItem.setAccelerator(KeyStroke.getKeyStroke("control P")); 
        JMenuItem quitItem = new JMenuItem("Exit");
            quitItem.setMnemonic('X');
            quitItem.setAccelerator(KeyStroke.getKeyStroke("control X"));


        JMenu viewMenu = new JMenu("View");
        JMenuItem testItem = new JMenuItem("Testing Dashboard");
        testItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                TestingDashboard.setVisible(true);
            }
        });
        viewMenu.add(testItem);

        JMenuItem properties = new JMenuItem("Project Settings");
        properties.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                updateAttributePane(new ProjectPropertiesPanel());
            }
        });

        viewMenu.add(properties);

        //Build  menubar, menus, and add menuitems.
        JMenuBar menubar = new JMenuBar();  // Create new menu bar

            JMenu fileMenu = new JMenu("File"); // Create new menu
                fileMenu.setMnemonic('F');
                menubar.add(fileMenu);      // Add menu to the menubar
                fileMenu.add(saveItem);     // Add menu item to the menu
                fileMenu.add(saveAsItem);
                fileMenu.add(exportItem);
                fileMenu.add(pageItem);
                fileMenu.add(printItem);
                fileMenu.addSeparator();    // Add separator line to menu
                fileMenu.add(quitItem);

                menubar.add(viewMenu);

                fileMenu.setMnemonic('R');
                menubar.add(reportsMenu);



        //Add listeners to menu items
        quitItem.addActionListener(new QuitAction());
        printItem.addActionListener(new PrintAction());
        pageItem.addActionListener(new PageSetupAction());
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

    public JPanel forceAttributes(final mxCell cell, final GraphEditor editor)
    {
        final mxGraph graph = graphComponent.getGraphComponent().getGraph();

        final Force force = (Force)(cell.getValue());
        //final mxGraph graph = editor.getGraphComponent().getGraph();
        final JPanel newPanel = new JPanel();
        //Sets the Minimum Size of the Panel to 300 wide by 500 high
        newPanel.setMinimumSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE,500));

        final SpringLayout layout = new SpringLayout();
        newPanel.setLayout(layout);

        final JLabel Notes = new JLabel("Notes:");
        final JTextArea notesField = new JTextArea(4,20);
        notesField.setText(force.getNotes());
        final JLabel Weight = new JLabel("Weight:");
        final String[] ForceWeights = {"Low","Medium","High"};
        final JComboBox ForceBox = new JComboBox(ForceWeights);

        ForceBox.setSelectedItem(Integer.toString(force.getWeight()));
        ForceBox.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent actionEvent) {
                                           JComboBox cb = (JComboBox) actionEvent.getSource();
                                           if(cb.getSelectedItem() == "High") {
                                               force.setWeight(5);
                                               graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "5", new Object[]{cell});
                                               graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", new Object[]{cell});
                                           }
                                           else if(cb.getSelectedItem() == "Medium")
                                           {
                                               force.setWeight(3);
                                               graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", new Object[]{cell});
                                               graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "purple", new Object[]{cell});
                                           }
                                           else if (cb.getSelectedItem() == "Low")
                                           {
                                               force.setWeight(1);
                                               graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1", new Object[]{cell});
                                               graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "blue", new Object[]{cell});
                                           }
                                       }
                                   });


            //This constraint places the name 5 over from the top left corner of the Panel
            layout.putConstraint(SpringLayout.WEST,Notes,5,SpringLayout.WEST,newPanel);
            layout.putConstraint(SpringLayout.NORTH,Notes,5,SpringLayout.NORTH,newPanel);

            //This Constraint puts the titlePane 5 over from the Name Label and 5 under the Panel top y value
            layout.putConstraint(SpringLayout.WEST,notesField,5,SpringLayout.EAST,Notes);
            layout.putConstraint(SpringLayout.NORTH,notesField,5,SpringLayout.NORTH,newPanel);

            //This constraint puts the menubar 5 over from the titlePane and 5 under the panels top y value
            layout.putConstraint(SpringLayout.WEST,Weight,5,SpringLayout.WEST,newPanel);
            layout.putConstraint(SpringLayout.SOUTH,Weight,75,SpringLayout.SOUTH,Notes);

        //This constraint puts Notes label 5 from the west portion of the Panel but 30 units under the Name Label
            layout.putConstraint(SpringLayout.WEST,ForceBox,45,SpringLayout.WEST,Weight);
            layout.putConstraint(SpringLayout.SOUTH,ForceBox,75,SpringLayout.SOUTH,Notes);

            newPanel.add(Notes);
            newPanel.add(notesField);
            newPanel.add(ForceBox);
            newPanel.add(Weight);


        //-------Key listeners for notesField
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if(e.getSource()==notesField)
                {
                    String forceNotes = notesField.getText();
                    force.setNotes(forceNotes);
                }
            }
        };

        notesField.addKeyListener(keyListener);

            return newPanel;
        }

    public JPanel entityAttributes(final GraphEditor editor, final mxCell cell)
    {
        final mxGraph graph = graphComponent.getGraphComponent().getGraph();

        final Entity entity = (Entity)(cell.getValue());
        //final mxGraph graph = editor.getGraphComponent().getGraph();
        final JPanel newPanel = new JPanel();
        //Sets the Minimum Size of the Panel to 300 wide by 500 high
        newPanel.setMinimumSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE,500));

        //Sets the Panel layout to a SpringLayout
        final SpringLayout layout = new SpringLayout();
        newPanel.setLayout(layout);

        final JTextField titlePane = new JTextField("",10); //Creates a textfield to enter a title
        titlePane.setText(entity.getName());
        final JTextArea metaDataTextArea = new JTextArea(4,25); //Creates a textfield to enter Metadata
        metaDataTextArea.setText(entity.getNotes());
        final JLabel Name = new JLabel("Title:"); //Creates a label called Title:
        final JLabel Notes = new JLabel("Notes:"); //Creates a label called Notes:
        final JMenuBar bar = new JMenuBar(); //Creates a menu bar called bar
        final JCheckBox noncontrolCheckBox = new JCheckBox("Non-Controllable"); //Creates a checkbox for controlable entities.
        if(entity.isControllable())
        {
            noncontrolCheckBox.setSelected(false);
        }
        else
            noncontrolCheckBox.setSelected(true);

        final JCheckBox nonvisibleCheckBox = new JCheckBox("Non-Visible"); //Creates a checkbox for visible entities.
        if(entity.isVisible())
        {
            nonvisibleCheckBox.setSelected(false);
        }
        else
            nonvisibleCheckBox.setSelected(true);

        final JButton clearButton = new JButton("Clear"); //Creates a button in order to clear entity data.

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
        layout.putConstraint(SpringLayout.SOUTH,metaDataTextArea,100,SpringLayout.SOUTH,titlePane);

        //This constraint puts the control box 25 units from the west portion of the Panel and 75 units under the titlePane
        layout.putConstraint(SpringLayout.WEST,noncontrolCheckBox,25,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,noncontrolCheckBox,125,SpringLayout.SOUTH,titlePane);

        //This constraint puts the visible box 25 units from the west portion of the Panel and 100 units under the titlePane
        layout.putConstraint(SpringLayout.WEST,nonvisibleCheckBox,25,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,nonvisibleCheckBox,150,SpringLayout.SOUTH,titlePane);

        //This constraint puts the delete button 25 units from the west portion of the Panel and 150 units under the titlePane
        layout.putConstraint(SpringLayout.WEST,clearButton,25,SpringLayout.WEST,newPanel);
        layout.putConstraint(SpringLayout.SOUTH,clearButton,200,SpringLayout.SOUTH,titlePane);

        final String[] entityTypes = Control.getInstance().getEntityClassifications().toArray(new String[Control.getInstance().getEntityClassifications().size()]);
        final JComboBox entityTypeMenu = new JComboBox(entityTypes);
        entityTypeMenu.setSelectedItem(entity.getClassification());
        entityTypeMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox cb = (JComboBox) actionEvent.getSource();
                entity.setClassification((String) cb.getSelectedItem());
                //Change the color of the drawable entity
                if (entity.getClassification().equals("Problem")) {
                    graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "grey", new Object[]{cell});
                    graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "black", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "black", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new Object[]{cell});
                }
                if (entity.getClassification().equals("Stakeholder")) {
                    graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", new Object[]{cell});
                    graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "blue", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "blue", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new Object[]{cell});
                }
                if (entity.getClassification().equals("Objective")) {
                    graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", new Object[]{cell});
                    graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "orange", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "orange", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "black", new Object[]{cell});
                }
                if (entity.getClassification().equals("Attribute")) {
                    graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", new Object[]{cell});
                    graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "green", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new Object[]{cell});
                }
                if (entity.getClassification().equals("Resource")) {
                    graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", new Object[]{cell});
                    graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "yellow", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{cell}); //changes the color to red
                    graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "black", new Object[]{cell});
                }

                editor.getGraphComponent().refresh();

            }
        });
        bar.add(entityTypeMenu);

        //-------Item listener for Non-Controllable CheckBox
        noncontrolCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "5", new Object[]{cell});
                    entity.setControllable(false);
                }
                else if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    entity.setControllable(true);
                    graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1", new Object[]{cell});
                }
            }
        });

        //-------Item listener for Non-Visible CheckBox
        nonvisibleCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    graph.setCellStyles(mxConstants.STYLE_OPACITY, "50", new Object[]{cell});
                    entity.setVisible(false);
                }
                else if (e.getStateChange() == ItemEvent.DESELECTED)
                {
                    graph.setCellStyles(mxConstants.STYLE_OPACITY, "100", new Object[]{cell});
                    entity.setVisible(true);
                }
            }
        });

        //-------Action listener for Clear Button
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String classification = Control.getInstance().getDefaultEntityClassification();
                String newName = classification + "1";
                titlePane.setText(newName);
                metaDataTextArea.setText("");
                noncontrolCheckBox.setSelected(false);
                nonvisibleCheckBox.setSelected(false);
                entityTypeMenu.setSelectedItem(classification);
                entity.setNotes("");
                entity.setName(newName);
                entity.setClassification(classification);
                graph.refresh();
            }
        });

        newPanel.add(Name);
        newPanel.add(titlePane);
        newPanel.add(bar);
        newPanel.add(Notes);
        newPanel.add(metaDataTextArea);
        newPanel.add(nonvisibleCheckBox);
        newPanel.add(noncontrolCheckBox);
        newPanel.add(clearButton);

        newPanel.setVisible(true);

        //-------Key listeners for name and metadata
        KeyListener keyListener = new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {

            }

            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if(e.getSource()==titlePane)
                {

                    String entityName = titlePane.getText();
                    entity.setName(entityName);
                    editor.getGraphComponent().refresh();


                }
                if(e.getSource()==metaDataTextArea)
                {
                    String metaDataText = metaDataTextArea.getText();
                    entity.setNotes(metaDataText);
                }
            }
        };

        titlePane.addKeyListener(keyListener);
        metaDataTextArea.addKeyListener(keyListener);

        return newPanel;
    }

    public void load() {
        load(null);
    }

    public void load(String path) {
        if (path==null) {
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("ICAT Files", "icat");
            fc.setFileFilter(filter);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File openFiles = fc.getSelectedFile();
                path = openFiles.getAbsolutePath();
            } else {
                return;
            }
        }
        // load the file here
        Control.getInstance().loadProject(path);


        com.mxgraph.view.mxGraph graph = graphComponent.getGraphComponent().getGraph();
        com.mxgraph.model.mxGraphModel graphModel = (com.mxgraph.model.mxGraphModel)graph.getModel();
        graphModel.beginUpdate();
        try {
            graphModel.clear();
            Project project = Control.getInstance().getCurrentProject();
            if (project!=null) {
                Map<Entity, Object> internalCells = new HashMap<Entity, Object>();
                Hashtable<String, String> problemStyle = new Hashtable<String, String>();
                problemStyle.put(mxConstants.STYLE_FILLCOLOR,"black");
                problemStyle.put(mxConstants.STYLE_GRADIENTCOLOR,"black");
                for (Entity entity : project.getEntities()) {
                    //else
                    Object graphEntity = graph.insertVertex(graph.getDefaultParent(), null, entity, entity.getLocation().getX(), entity.getLocation().getY(), 100, 100,"shape=ellipse");
                    Object[] entities = {graphEntity};


                    if(entity.getClassification().equals("Problem")) {
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "grey", entities);
                        graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "black", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "black", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", entities);
                    }
                    if(entity.getClassification().equals("Stakeholder")) {
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", entities);
                        graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "blue", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "blue", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", entities);
                    }
                    if(entity.getClassification().equals("Objective")) {
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", entities);
                        graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "orange", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "orange", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "black", entities);
                    }
                    if(entity.getClassification().equals("Attribute")) {
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", entities);
                        graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "green", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", entities);
                    }
                    if(entity.getClassification().equals("Resource")) {
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "black", entities);
                        graph.setCellStyles(mxConstants.STYLE_GRADIENTCOLOR, "yellow", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", entities); //changes the color to red
                        graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "black", entities);

                    }
                    if(!entity.isVisible()){
                        graph.setCellStyles(mxConstants.STYLE_OPACITY, "50", entities);
                    }
                    if(!entity.isControllable()){
                        graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "5", entities);
                    }
                    internalCells.put(entity, graphEntity);
                    graphComponent.getGraphComponent().refresh();

                }
                for (Force force : project.getForces()) {
                    Object insertedEdge = graph.insertEdge(graph.getDefaultParent(), null, force, internalCells.get(force.getOrigin()), internalCells.get(force.getDestination()));
                    if(force.getWeight() == 1) {
                        graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, Integer.toString(force.getWeight()), new Object[]{insertedEdge});
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "blue", new Object[]{insertedEdge});
                    }
                    if(force.getWeight() == 3) {
                        graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, Integer.toString(force.getWeight()), new Object[]{insertedEdge});
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "purple", new Object[]{insertedEdge});
                    }
                    if(force.getWeight() == 5) {
                        graph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, Integer.toString(force.getWeight()), new Object[]{insertedEdge});
                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", new Object[]{insertedEdge});
                    }
                }
            }

        } finally {
            graphModel.endUpdate();
            setTitle(path + " - ICAT Editor");
        }

    }

    //-------Action listener for load button
    class LoadAction implements ActionListener {

        public void actionPerformed(ActionEvent e)
        {
            load();
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

    //--------Action listener for print format button
    class PageSetupAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            EditorActions.PageSetupAction.formatPage(graphComponent.getGraphComponent());
        }
    }

    //--------Action listener for export button
    class ExportAction implements ActionListener
    {
        JFileChooser ei = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("PNG Files", "png");

        public void actionPerformed(ActionEvent e)
        {
            mxGraphComponent gc = graphComponent.getGraphComponent();


            gc.setSize(gc.getPreferredSize());
            gc.addNotify();
            gc.doLayout();

            BufferedImage bi = new BufferedImage(gc.getSize().width, gc.getSize().height, BufferedImage.TYPE_INT_ARGB);
             //BufferedImage crop = bi.getSubimage(rect.x, rect.y, rect.width, rect.height);

            Graphics g = bi.createGraphics();

            gc.paintAll(g);

            g.dispose();

            ei.setFileFilter(filter);
            ei.setDialogTitle("Export");
            ei.setDialogType(JFileChooser.CUSTOM_DIALOG);

            if (ei.showSaveDialog(WorkSpace.this) == JFileChooser.APPROVE_OPTION)
            {
                File outFile = ei.getSelectedFile();
                if (!outFile.getPath().toLowerCase().endsWith(".png"))
                {
                    outFile = new File (outFile.getPath()+ ".png");
                }
                try{ImageIO.write(bi,"png", outFile);}catch (Exception x) {}
            }

        }

    }

    //-------Action listener for save button
    class SaveAction implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            try {
                Control.getInstance().saveCurrent();
            } catch (IllegalStateException e1){
                new SaveAsAction().actionPerformed(e);
            }
        }
    }

    //-------Action listener for save as button
    class SaveAsAction implements ActionListener {
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("ICAT Files", "icat");
        //Utils utils = new Utils();
        
        public void actionPerformed(ActionEvent e)
        {
            // JOptionPane.showMessageDialog(WorkSpace.this, "No Files Found.");
            fc.setFileFilter(filter);
            //fc.getSelectedFile();
            if (fc.showSaveDialog(WorkSpace.this) == JFileChooser.APPROVE_OPTION)
            {
                File saveFiles = fc.getSelectedFile();
                /**Check for the icat extension*/
                if (!saveFiles.getPath().toLowerCase().endsWith(".icat"))
                {
                    saveFiles = new File (saveFiles.getPath()+ ".icat");
                }
                Control.getInstance().saveCurrentAs(saveFiles.getAbsolutePath());
                setTitle(saveFiles.getPath() + " - ICAT Editor");
            }
        }
    }


}
