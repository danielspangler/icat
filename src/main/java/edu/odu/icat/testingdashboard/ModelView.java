package edu.odu.icat.testingdashboard;

import edu.odu.icat.model.*;
import edu.odu.icat.controller.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by trueLove on 4/21/14.
 */
public class ModelView extends JFrame{

    protected JTable EntityViewTable;
    protected EntityViewTableModel EntityViewData;

    protected JTable ForceViewTable;
    protected ForceViewTableModel ForceViewData;

    protected JButton RefreshButton;

    public ModelView()
    {
        JPanel TablePanel = new JPanel();
        TablePanel.setLayout(new BoxLayout(TablePanel, BoxLayout.Y_AXIS));
        TablePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        TablePanel.add(Box.createVerticalGlue());


        EntityViewTable = new JTable(new EntityViewTableModel());
        EntityViewData = (EntityViewTableModel) EntityViewTable.getModel();

        EntityViewTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        EntityViewTable.setFillsViewportHeight(true);
        EntityViewTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane EntityViewPane = new JScrollPane(EntityViewTable);


        TablePanel.add(new JLabel("Entities"));
        TablePanel.add(EntityViewPane);
        TablePanel.add(new JSeparator());

        ForceViewTable = new JTable(new ForceViewTableModel());
        ForceViewData = (ForceViewTableModel) ForceViewTable.getModel();

        ForceViewTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        ForceViewTable.setFillsViewportHeight(true);
        ForceViewTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane ForceViewPane = new JScrollPane(ForceViewTable);

        TablePanel.add(new JLabel("Forces"));
        TablePanel.add(ForceViewPane);

        setLayout(new BorderLayout());
        add(TablePanel, BorderLayout.CENTER);

        JPanel ButtonsArea = new JPanel();
        ButtonsArea.setLayout(new BoxLayout(ButtonsArea, BoxLayout.LINE_AXIS));
        ButtonsArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        ButtonsArea.add(Box.createHorizontalGlue());


        RefreshButton = new JButton("Refresh");
        RefreshButton.setEnabled(true);
        RefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EntityViewData.removeAll();

                for(Entity e: Control.getInstance().getCurrentProject().getEntities())
                {
                    EntityViewData.add(e);
                }

                ForceViewData.removeAll();

                for(Force f: Control.getInstance().getCurrentProject().getForces())
                {
                    ForceViewData.add(f);
                }
            }
        });

        ButtonsArea.add(RefreshButton);

        add(ButtonsArea, BorderLayout.SOUTH);

        pack();
        setTitle("ICAT Data Model View");
        setVisible(false);
    }
}
