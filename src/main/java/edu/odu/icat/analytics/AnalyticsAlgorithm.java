package edu.odu.icat.analytics;

import edu.odu.icat.model.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public abstract class AnalyticsAlgorithm implements  Runnable
{
	protected AlgorithmDialogBox dialog;

    public abstract String getName();

    public abstract String getReportOutputHeader();

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
            //Title Area
            JLabel Title = new JLabel(AnalyticsAlgorithm.this.getName());

            JPanel AlgorithmArea = new JPanel();
            AlgorithmArea.setLayout(new BoxLayout(AlgorithmArea, BoxLayout.Y_AXIS));
            AlgorithmArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            AlgorithmArea.add(Box.createVerticalGlue());

            //Filter Selection

            FilterContainer = new JPanel();
            FilterContainer.setLayout(new BoxLayout(FilterContainer, BoxLayout.PAGE_AXIS));
            FilterContainer.add(Box.createRigidArea(new Dimension(0,10)));
            FilterContainer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));


            FilterContainer.add(new JLabel("Algorithm Filters"));
            visibilityCheck = new JCheckBox("Visibility");
            FilterContainer.add(visibilityCheck);
            controllabilityCheck = new JCheckBox("Controllability");
            FilterContainer.add(controllabilityCheck);
            FilterContainer.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            //FilterContainer.add(new JSeparator());

            AlgorithmArea.add(FilterContainer);
            AlgorithmArea.add(new JSeparator());

            //Algorithm Output
            ReportTable = new JTable(new ReportTableModel());
            ReportData = (ReportTableModel) ReportTable.getModel();
            ReportData.setReportDataHeader(AnalyticsAlgorithm.this.getReportOutputHeader());

            ReportTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
            ReportTable.setFillsViewportHeight(true);
            ReportTable.getTableHeader().setReorderingAllowed(false);

            ReportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane AlgorithmOutputArea = new JScrollPane(ReportTable);

            AlgorithmOutputArea.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            AlgorithmArea.add(AlgorithmOutputArea);
            //Area for Buttons

            JPanel ButtonsArea = new JPanel();
            ButtonsArea.setLayout(new BoxLayout(ButtonsArea, BoxLayout.LINE_AXIS));
            ButtonsArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            ButtonsArea.add(Box.createHorizontalGlue());

            PrintButton = new JButton("Print");
            PrintButton.setEnabled(false);
            PrintButton.addActionListener(new PrintAction());
            ButtonsArea.add(PrintButton);
            ButtonsArea.add(Box.createRigidArea(new Dimension(5, 0)));

            ExportButton = new JButton("Export");
            ExportButton.setEnabled(false);
            ExportButton.addActionListener(new ExportAction());
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

            add(Title, BorderLayout.NORTH);
            add(AlgorithmArea, BorderLayout.CENTER);
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

        //-------Action listener for Print button
        class PrintAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {

                try {
                    AlgorithmDialogBox.this.ReportTable.print();
                } catch (PrinterException e1) {

                }
            }
        }

        //-------Export listener for load button
        class ExportAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                AlgorithmDialogBox.this.ReportData.removeAll();
                PrintButton.setEnabled(true);
                ExportButton.setEnabled(true);
                AnalyticsAlgorithm.this.run();
            }
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