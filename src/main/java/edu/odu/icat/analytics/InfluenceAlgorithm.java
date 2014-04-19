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
        System.out.println("Running " + getName());

        List<Entity> OutputData = new ArrayList<Entity>();
        OutputData.add(new Entity("Brandon", "Problem"));  //Lol
        OutputData.add(new Entity("Stephen", "Resource"));
        OutputData.add(new Entity("Kirby", "Stakeholder"));
        OutputData.add(new Entity("Abdul", "Objective"));
        OutputData.add(new Entity("Chris", "Resource"));
        OutputData.add(new Entity("Daniel", "Stakeholder"));
        OutputData.add(new Entity("Daniel", "Stakeholder"));
        OutputData.add(new Entity("Daniel", "Stakeholder"));
        OutputData.add(new Entity("Daniel", "Stakeholder"));
        OutputData.add(new Entity("Daniel", "Stakeholder"));

        for(Entity e: OutputData)
            dialog.addEntityToReport(e);

        Control controller = Control.getInstance();
        List<Force> forces = controller.getCurrentProject().getForces();
        List<Entity> entities = controller.getCurrentProject().getEntities();

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
                Integer i = EntityWeights.get(f.getOrigin());
                i+= f.getWeight();
                EntityWeights.put(f.getOrigin(), i);
            }
        }

        ValueComparator sort = new ValueComparator(EntityWeights);
        Map<Entity, Integer> SortedEntites = new TreeMap<Entity, Integer>(sort);

        for(Entity e : SortedEntites.keySet())
        {
            dialog.addEntityToReport(e);
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