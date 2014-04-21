package edu.odu.icat.analytics;

import edu.odu.icat.model.*;
import edu.odu.icat.controller.*;

import java.util.*;

public class InfluenceAlgorithm extends AnalyticsAlgorithm
{

    public InfluenceAlgorithm()
    {
        dialog = new AlgorithmDialogBox();
    }

    public String getName()
    {
        return "Influence Algorithm";
    }

    public AlgorithmDialogBox getAlgorithmDialogBox()
    {
        return dialog;
    }

    public void run()
	{
        List<Force> forces = Control.getInstance().getCurrentProject().getForces();
        List<Entity> entities = Control.getInstance().getCurrentProject().getEntities();

        Map<Entity, Integer> EntityWeights = new HashMap<Entity, Integer>();
        
        for(Entity e: entities)     //Initialize
        {
            EntityWeights.put(e, 0);
        }

        for(Force f: forces)
        {
            if(!dialog.getVisibility() || (f.getOrigin().isVisible() && f.getDestination().isVisible()))    //if filter off, both EntityWeights must be visible
            if(!dialog.getControllability() || (f.getDestination().isControllable()))                       //if filter off, destination must be controllable
            {
                Integer i = EntityWeights.get(f.getOrigin());
                i+= f.getWeight();
                EntityWeights.put(f.getOrigin(), i);
            }
        }

        ValueComparator sort = new ValueComparator(EntityWeights);
        Map<Entity, Integer> SortedEntites = new TreeMap<Entity, Integer>(sort);
        SortedEntites.putAll(EntityWeights);

        for(java.util.Map.Entry<Entity, Integer> e : SortedEntites.entrySet())
        {
            dialog.addEntityToReport(e.getKey(), e.getValue());
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
}