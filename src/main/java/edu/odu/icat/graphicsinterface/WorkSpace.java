/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.odu.icat.graphicsinterface;

import edu.odu.icat.analytics.AnalyticsEngine;
import edu.odu.icat.controller.Control;
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


public class WorkSpace extends JFrame implements Printable{


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
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));

        final JTextPane titlePane = new JTextPane();
        titlePane.setSize( titlePane.getPreferredSize() );
        newPanel.add(titlePane, newPanel);
        titlePane.setText("Title");
        titlePane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                titlePane.setText("");
            }
        });

        newPanel.add(Box.createVerticalGlue());

        JMenuBar bar = new JMenuBar();
        JMenu entityTypeMenu = new JMenu("Type");
        bar.add(entityTypeMenu);

        for(String s: edu.odu.icat.controller.Control.getInstance().getEntityClassifications())
        {
            entityTypeMenu.add(new JMenuItem(s));
        }

        newPanel.add(bar);
        newPanel.add(Box.createVerticalGlue());
        JTextField metaDataTextArea = new JTextField("",20);
        newPanel.add(metaDataTextArea);

        newPanel.add(Box.createVerticalGlue());

        JCheckBox noncontrolCheckBox = new JCheckBox("Non-Controllable");
        JCheckBox nonvisibleCheckBox = new JCheckBox("Non-Visible");

        newPanel.add(nonvisibleCheckBox);
        newPanel.add(noncontrolCheckBox);

        newPanel.add(new JSeparator());

        JButton deleteButton = new JButton("Delete");
        newPanel.add(deleteButton, newPanel);

        setVisible(true);

        return newPanel;
    }

    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }


//        graphComponent.getGraphComponent().getGraph();
//        graphComponent.getGraphComponent().print(g);

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;

        java.awt.geom.AffineTransform originalTransform = g2d.getTransform();

        double scaleX = pf.getImageableWidth() / graphComponent.getComponent(0).getWidth();
        double scaleY = pf.getImageableHeight() / graphComponent.getComponent(0).getHeight();
        // Maintain aspect ratio
        double scale = Math.min(scaleX, scaleY);
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.scale(scale, scale);

        /* Now print the window and its visible contents */
        graphComponent.getComponent(0).printAll(g2d);

        g2d.setTransform(originalTransform);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

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
        public void actionPerformed(ActionEvent e) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(WorkSpace.this);
            if (job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException ex) {
              /* The job did not successfully complete */
                }
            }
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