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

    @Override
    public String getName()
    {
        return "Influence Algorithm";
    }

    @Override
    public String getReportOutputHeader()
    {
        return "Influence";
    }

    @Override
    public AlgorithmDialogBox getAlgorithmDialogBox()
    {
        return dialog;
    }

    @Override
    public void run()
	{
        List<Force> forces = Control.getInstance().getCurrentProject().getForces();
        List<Entity> entities = Control.getInstance().getCurrentProject().getEntities();

        Map<Entity, Double> EntityWeights = new HashMap<Entity, Double>();
        
        for(Entity e: entities)     //Initialize
        {
            EntityWeights.put(e, 0.0);
        }

        for(Force f: forces)
        {
            if(!dialog.getVisibility() || (f.getOrigin().isVisible() && f.getDestination().isVisible()))    //if filter off, both EntityWeights must be visible
            if(!dialog.getControllability() || (f.getDestination().isControllable()))                       //if filter off, destination must be controllable
            {
                Double i = EntityWeights.get(f.getOrigin());
                i+= f.getWeight();
                EntityWeights.put(f.getOrigin(), i);
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