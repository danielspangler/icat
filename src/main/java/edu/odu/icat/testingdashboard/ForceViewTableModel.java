package edu.odu.icat.testingdashboard;

import edu.odu.icat.model.Entity;
import edu.odu.icat.model.Force;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trueLove on 4/21/14.
 */
public class ForceViewTableModel extends AbstractTableModel{

    private String[] columnNames = {"Origin", "Destination", "Weight", "Metadata"};


    private List<Force> reportForces;

    public ForceViewTableModel()
    {
        reportForces = new ArrayList<Force>();
    }

    public int getRowCount() {
        return reportForces.size();
    }

    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int index)
    {
        return columnNames[index];
    }

    public void add(Force force) {
        int size = reportForces.size();
        reportForces.add(force);
        fireTableRowsInserted(size, size);
    }

    public void remove(Force force) {
        if (reportForces.contains(force))
        {
            int index = reportForces.indexOf(force);
            reportForces.remove(force);
            fireTableRowsDeleted(index, index);
        }
    }

    public void removeAll()
    {
        for(int i = reportForces.size() -1; i >= 0; i--)
        {
            remove(reportForces.get(i));
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Force force = reportForces.get(rowIndex);
        switch (columnIndex){
            case 0:
                return force.getOrigin();
            case 1:
                return force.getDestination();
            case 2:
                return force.getWeight();
            case 3:
                return force.getNotes();
        }
        return "";
    }
}
