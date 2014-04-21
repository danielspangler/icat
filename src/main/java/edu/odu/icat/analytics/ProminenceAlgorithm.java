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
        dialog = new ProminenceAlgorithmDialogBox();
    }

    public String getName()
    {
        return "Prominence Algorithm";
    }

    public AlgorithmDialogBox getAlgorithmDialogBox()
    {
        return dialog;
    }

    public void run()
    {
        java.util.List<Force> forces = Control.getInstance().getCurrentProject().getForces();
        java.util.List<Entity> entities = Control.getInstance().getCurrentProject().getEntities();

        Map<Entity, Integer> EntityWeights = new HashMap<Entity, Integer>();

        for(Entity e: entities)
        {

            EntityWeights.put(e, 0);
        }

        for(Force f: forces)
        {
            if(!dialog.getVisibility() || (f.getOrigin().isVisible() && f.getDestination().isVisible()))    //if filter off, both EntityWeights must be visible
                if(!dialog.getControllability() || (f.getDestination().isControllable()))                       //if filter off, destination must be controllable
                {
                    Integer i = EntityWeights.get(f.getDestination());
                    i+= f.getWeight();
                    EntityWeights.put(f.getDestination(), i);
                }
        }

        ValueComparator sort = new ValueComparator(EntityWeights);
        Map<Entity, Integer> SortedEntites = new TreeMap<Entity, Integer>(sort);
        SortedEntites.putAll(EntityWeights);

        for(Entity e : SortedEntites.keySet())
        {
            dialog.addEntityToReport(e,0);
        }

    }

    class ValueComparator implements Comparator<Entity> {

        Map<Entity, Integer> base;
        public ValueComparator(Map<Entity, Integer> base) {
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

    public class ProminenceAlgorithmDialogBox extends AnalyticsAlgorithm.AlgorithmDialogBox
    {
        protected  JCheckBox SomeotherCriteria;
        public ProminenceAlgorithmDialogBox()
        {
            super();
            SomeotherCriteria = new JCheckBox("Hello");
            FilterContainer.add(SomeotherCriteria, BorderLayout.CENTER);
            setVisible(true);
        }
    }
}