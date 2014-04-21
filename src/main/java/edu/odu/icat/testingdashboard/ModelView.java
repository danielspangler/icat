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

    protected JButton RefreshButton;

    public ModelView()
    {
        EntityViewTable = new JTable(new EntityViewTableModel());
        EntityViewData = (EntityViewTableModel) EntityViewTable.getModel();

        EntityViewTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        EntityViewTable.setFillsViewportHeight(true);
        EntityViewTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane EntityViewPane = new JScrollPane(EntityViewTable);

        setLayout(new BorderLayout());
        add(EntityViewPane, BorderLayout.CENTER);

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
            }
        });

        ButtonsArea.add(RefreshButton);

        add(ButtonsArea, BorderLayout.SOUTH);

        setVisible(true);
    }
}
