package edu.odu.icat.analytics;

import edu.odu.icat.model.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AnalyticsAlgorithm implements  Runnable
{
	public abstract String getName();

    public abstract void run();

    public class AlgorithmDialogBox extends JDialog
    {
        protected JCheckBox visibilityCheck;
        protected JCheckBox controllabilityCheck;
        protected JPanel FilterContainer;
        protected JScrollPane AlgorithmOutputArea;
        protected JList DataList;
        protected DefaultListModel Data;
        protected JButton ExportButton;
        protected JButton PrintButton;

        public AlgorithmDialogBox()
        {
            //setModal(true);         //Will not continue until this box is closed
            setSize(400, 300);
            FilterContainer = new JPanel();
            FilterContainer.setLayout(new BoxLayout(FilterContainer, BoxLayout.PAGE_AXIS));
            FilterContainer.add(Box.createRigidArea(new Dimension(0,10)));
            FilterContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            FilterContainer.add(new JLabel("Algorithm Filters"));
            visibilityCheck = new JCheckBox("Visibility");
            FilterContainer.add(visibilityCheck);
            controllabilityCheck = new JCheckBox("Controllability");
            FilterContainer.add(controllabilityCheck);
            FilterContainer.add(new JSeparator());

            Data = new DefaultListModel();
            DataList = new JList(Data); //data has type Object[]
            DataList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            DataList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            DataList.setVisibleRowCount(-1);

            AlgorithmOutputArea = new JScrollPane(DataList);
            AlgorithmOutputArea.setPreferredSize(new Dimension(250, 80));

            JPanel ButtonsArea = new JPanel();
            ButtonsArea.setLayout(new BoxLayout(ButtonsArea, BoxLayout.LINE_AXIS));
            ButtonsArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            ButtonsArea.add(Box.createHorizontalGlue());

            PrintButton = new JButton("Print");
            PrintButton.setEnabled(false);
            ButtonsArea.add(PrintButton);
            ButtonsArea.add(Box.createRigidArea(new Dimension(5, 0)));


            ExportButton = new JButton("Export");
            ExportButton.setEnabled(false);
            ButtonsArea.add(ExportButton);
            ButtonsArea.add(Box.createRigidArea(new Dimension(5, 0)));

            JButton RunButton = new JButton("Run");
            ButtonsArea.add(RunButton);
            RunButton.addActionListener(new RunAction());
            ButtonsArea.add(Box.createRigidArea(new Dimension(5, 0)));

            JButton CancelButton = new JButton("Cancel");
            CancelButton.addActionListener(new QuitAction());
            ButtonsArea.add(CancelButton);

            getContentPane().add(FilterContainer, BorderLayout.NORTH);
            getContentPane().add(new JPanel(), BorderLayout.WEST);
            getContentPane().add(AlgorithmOutputArea, BorderLayout.CENTER);
            getContentPane().add(new JPanel(), BorderLayout.EAST);
            getContentPane().add(ButtonsArea, BorderLayout.SOUTH);

            setVisible(true);
        }

        public boolean getVisibility()
        {
            return visibilityCheck.isSelected();
        }

        public boolean getControllability()
        {
            return controllabilityCheck.isSelected();
        }

        public void addEntityToReport(Entity entity)
        {
            Data.addElement(entity);
            AlgorithmOutputArea.repaint();
        }

        //-------Action listener for load button
        class RunAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                PrintButton.setEnabled(true);
                ExportButton.setEnabled(true);
                setModal(false);
            }
        }

        //--------Action listener for exit button
        class QuitAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                AlgorithmDialogBox.this.dispose();
            }
        }

    }
}