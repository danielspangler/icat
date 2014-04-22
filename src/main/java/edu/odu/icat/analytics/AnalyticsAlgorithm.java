package edu.odu.icat.analytics;

import edu.odu.icat.model.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AnalyticsAlgorithm implements  Runnable
{
	protected AlgorithmDialogBox dialog;

    public abstract String getName();

    public abstract AlgorithmDialogBox getAlgorithmDialogBox();

    public abstract void run();

    public class AlgorithmDialogBox extends JPanel
    {
        protected JCheckBox visibilityCheck;
        protected JCheckBox controllabilityCheck;

        protected JPanel FilterContainer;
        protected JScrollPane AlgorithmOutputArea;

        protected JButton ExportButton;
        protected JButton PrintButton;

        protected JTable ReportTable;
        protected ReportTableModel ReportData;

        public AlgorithmDialogBox()
        {

            //Filter Selection

            FilterContainer = new JPanel();
            FilterContainer.setLayout(new BoxLayout(FilterContainer, BoxLayout.PAGE_AXIS));
            FilterContainer.add(Box.createRigidArea(new Dimension(0,10)));
            FilterContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            FilterContainer.add(new JLabel("Algorithm Filters"));
            visibilityCheck = new JCheckBox("Visibility");
            FilterContainer.add(visibilityCheck);
            controllabilityCheck = new JCheckBox("Controllability");
            FilterContainer.add(controllabilityCheck);
            //FilterContainer.add(new JSeparator());

            //Algorithm Output
            String[] columnHeaders = { "Name" , "Classification" , "AlgorithmData" };
            ReportTable = new JTable(new ReportTableModel());
            ReportData = (ReportTableModel) ReportTable.getModel();

            ReportTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
            ReportTable.setFillsViewportHeight(true);
            ReportTable.getTableHeader().setReorderingAllowed(false);
            JScrollPane AlgorithmOutputArea = new JScrollPane(ReportTable);

            //Area for Buttons

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

            JButton CancelButton = new JButton("Clear");
            CancelButton.addActionListener(new QuitAction());
            ButtonsArea.add(CancelButton);

            setLayout(new BorderLayout());
            //getContentPane().
            add(FilterContainer, BorderLayout.NORTH);
            //getContentPane().
            add(AlgorithmOutputArea, BorderLayout.CENTER);
            //getContentPane().
            add(ButtonsArea, BorderLayout.SOUTH);

            setPreferredSize(new Dimension(400, 300));

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

        public void addEntityToReport(Entity entity, int Degree)
        {
            ReportData.add(entity, Degree);
        }

        //-------Action listener for load button
        class RunAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                AlgorithmDialogBox.this.ReportData.removeAll();
                PrintButton.setEnabled(true);
                ExportButton.setEnabled(true);
                AnalyticsAlgorithm.this.run();
            }
        }

        //--------Action listener for exit button
        class QuitAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                AlgorithmDialogBox.this.ReportData.removeAll();
            }
        }

    }
}