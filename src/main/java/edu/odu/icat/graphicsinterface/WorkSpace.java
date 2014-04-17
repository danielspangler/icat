/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.odu.icat.graphicsinterface;

import com.mxgraph.swing.mxGraphComponent;
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

import java.awt.print.*;
import java.util.List;


public class WorkSpace extends JFrame implements Printable{


    protected JPanel graphComponent;

    private JPanel contentPane;
    private JPanel attributePane;

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
		setBounds(100, 100, 564, 600);



        setLayout(new BorderLayout());

        attributePane = new JPanel();
        graphComponent = new GraphEditor();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, attributePane,
                graphComponent);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(200);
        split.setDividerSize(6);
        split.setBorder(null);

        add(split, BorderLayout.CENTER);

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
                   attributePane.removeAll();
                   JPanel dialog = AnalyticsEngine.getInstance().getAlgorithmDialog(name);
                   dialog.setPreferredSize(new Dimension(300,400));
                   attributePane.add(dialog);
                   attributePane.repaint();
                   dialog.repaint();
                   attributePane.setVisible(true);
                   System.out.println(name);
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


        //Add the (unused) text area to the content pane
        /*JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(contentPane, BorderLayout.CENTER);

        //Add menu items to popup menu, add popup menu to text area
        m_popup.add(new JMenuItem("Testing"));
        contentPane.setComponentPopupMenu(m_popup);

        //Set the JFrame's content pane and menu bar
        setContentPane(content);*/
        setJMenuBar(menubar);

        setTitle("WorkSpace");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center window
    }

    private void updateAttributePane(JPanel newPanel)
    {

    }

    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

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



}
