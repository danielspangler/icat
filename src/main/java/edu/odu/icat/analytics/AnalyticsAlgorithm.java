package edu.odu.icat.analytics;

import edu.odu.icat.graphicsinterface.WorkSpace;
import edu.odu.icat.model.Entity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.datatransfer.StringSelection;

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

            ReportTable.setPreferredScrollableViewportSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE, 70));
            ReportTable.setFillsViewportHeight(true);
            ReportTable.getTableHeader().setReorderingAllowed(false);

            ReportTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ReportTable.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener(){
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    if(listSelectionEvent.getValueIsAdjusting()){
                        System.out.println(ReportData.getValueAt(ReportTable.getSelectedRow(), 0));
                    }
                }
            });

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

            setPreferredSize(new Dimension(WorkSpace.MINIMUM_PANEL_SIZE, 300));
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

                StringBuilder contents=new StringBuilder();

                //Copy table headers to clipboard
                for(int i = 0; i < ReportTable.getColumnCount(); i++)
                {
                    contents.append(ReportData.getColumnName(i));
                    //if not the last column, insert tab character
                    if(i < ReportTable.getColumnCount() - 1)
                        contents.append("\t");
                }
                contents.append(System.getProperty("line.separator"));
                //copy entire contents to clipboard
                for (int i = 0; i < ReportTable.getRowCount(); i++)
                {
                    for (int j = 0; j < ReportTable.getColumnCount(); j++)
                    {
                        contents.append(ReportTable.getValueAt(i, j));
                        //if not the last column, insert tab character
                        if (j < ReportTable.getColumnCount() - 1)
                            contents.append("\t");
                    }
                    contents.append(System.getProperty("line.separator"));
                }

                //copy string to clipboard
                StringSelection TableContents  = new StringSelection(contents.toString());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(TableContents,TableContents);

                //Show dialog box notifying user
                JOptionPane.showMessageDialog(AlgorithmDialogBox.this, "Report output copied to clipboard");
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