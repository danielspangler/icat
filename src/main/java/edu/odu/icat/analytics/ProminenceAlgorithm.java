package edu.odu.icat.analytics;

import edu.odu.icat.controller.Control;
import edu.odu.icat.model.Entity;
import edu.odu.icat.model.Force;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.List;
import java.util.*;

public class ProminenceAlgorithm extends AnalyticsAlgorithm
{
    public ProminenceAlgorithm()
    {
        dialog = new AlgorithmDialogBox();
    }

    @Override
    public String getName()
    {
        return "Prominence Algorithm";
    }

    @Override
    public String getReportOutputHeader()
    {
        return "Prominence";
    }

    @Override
    public AlgorithmDialogBox getAlgorithmDialogBox()
    {
        return dialog;
    }

    @Override
    public void run()
    {
        java.util.List<Force> forces = Control.getInstance().getCurrentProject().getForces();
        java.util.List<Entity> entities = Control.getInstance().getCurrentProject().getEntities();

        Map<Entity, Double> EntityWeights = new HashMap<Entity, Double>();

        for(Entity e: entities)
        {

            EntityWeights.put(e, 0.0);
        }

        for(Force f: forces)
        {
            if(!dialog.getVisibility() || (f.getOrigin().isVisible() && f.getDestination().isVisible()))    //if filter off, both EntityWeights must be visible
                if(!dialog.getControllability() || (f.getDestination().isControllable()))                       //if filter off, destination must be controllable
                {
                    Double i = EntityWeights.get(f.getDestination());
                    i+= f.getWeight();
                    EntityWeights.put(f.getDestination(), i);
                }
        }

        ValueComparator sort = new ValueComparator(EntityWeights);
        Map<Entity, Double> SortedEntites = new TreeMap<Entity, Double>(sort);
        SortedEntites.putAll(EntityWeights);

        for(java.util.Map.Entry<Entity, Double> e : SortedEntites.entrySet())
        {
            dialog.addEntityToReport(e.getKey(), e.getValue());
        }

    }

    class ValueComparator implements Comparator<Entity> {

        Map<Entity, Double> base;
        public ValueComparator(Map<Entity, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with equals.
        public int compare(Entity a, Entity b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}