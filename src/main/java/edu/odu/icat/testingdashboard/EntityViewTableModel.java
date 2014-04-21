package edu.odu.icat.testingdashboard;

import edu.odu.icat.model.Entity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trueLove on 4/21/14.
 */
public class EntityViewTableModel extends AbstractTableModel{

    private String[] columnNames = {"Name", "Classification", "Visibility", "Controllability", "Metadata"};


    private List<Entity> reportEntities;

    public EntityViewTableModel()
    {
        reportEntities = new ArrayList<Entity>();
    }

    public int getRowCount() {
        return reportEntities.size();
    }

    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int index)
    {
        return columnNames[index];
    }

    public void add(Entity entity) {
        int size = reportEntities.size();
        reportEntities.add(entity);
        fireTableRowsInserted(size, size);
    }

    public void remove(Entity entity) {
        if (reportEntities.contains(entity))
        {
            int index = reportEntities.indexOf(entity);
            reportEntities.remove(entity);
            fireTableRowsDeleted(index, index);
        }
    }

    public void removeAll()
    {
        for(int i = reportEntities.size() -1; i >= 0; i--)
        {
            remove(reportEntities.get(i));
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Entity entity = reportEntities.get(rowIndex);
        switch (columnIndex){
            case 0:
                return entity.getName();
            case 1:
                return entity.getClassification();
            case 2:
                return entity.isVisible();
            case 3:
                return entity.isControllable();
            case 4:
                return entity.getNotes();
        }
        return "";
    }
}
