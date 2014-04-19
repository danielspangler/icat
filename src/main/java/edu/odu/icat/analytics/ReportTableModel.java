package edu.odu.icat.analytics;

import edu.odu.icat.model.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;

/**
 * Created by trueLove on 4/19/14.
 */
public class ReportTableModel extends AbstractTableModel {
    private String[] columnNames = {"Name", "Classification", "Degree"};


    private List<Entity> reportEntities;
    private List<Integer> entityDegrees;

    public ReportTableModel()
    {
        reportEntities = new ArrayList<Entity>();
        entityDegrees = new ArrayList<Integer>();
    }

    public int getRowCount() {
        return reportEntities.size();
    }

    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int index)
    {
        return columnNames[index];
    }

    public void add(Entity entity, int Degree) {
        int size = reportEntities.size();
        reportEntities.add(entity);
        entityDegrees.add(Degree);
        fireTableRowsInserted(size, size);
    }

    public void remove(Entity entity) {
        if (reportEntities.contains(entity))
        {
            int index = reportEntities.indexOf(entity);
            reportEntities.remove(entity);
            entityDegrees.remove(index);
            ReportTableModel.this.fireTableRowsDeleted(index, index);
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
                return entityDegrees.get(rowIndex);
        }
        return "";
    }
}