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
        List<Force> forces = controller.getForces();

        HashMap<Entity, Integer> entities = new HashMap<Entity, Integer>();

        for(Force f: forces)
        {
            if(!dialog.getVisibility() || (f.getOrigin().isVisible() && f.getDestination().isVisible()))
            if(!dialog.getControllability() || (f.getDestination().isControllable()))
            {
                Integer i = entities.get(f.getOrigin());
                i+= f.getWeight();
                entities.put(f.getOrigin(), i);
            }
        }

		// Influence Algorithm in n^2 time
		//
		// Algorithm:  Influence Algorithm
		// Input: ICAT Data Model with n vertices and m edges
		// Output: Sorted List L of Entity-Influence pairs
		// 1.	L <- Ø
		// 2. 	for i <- 1 to n
		// 3. 		v1 <- vi
		// 4.		vsum <- 0
		// 5.		for j <- 1 to n
		// 6.			v2 <- vj
		// 7.			if edge (v1, v2) exists
		// 8.				vsum <- vsum + weight of edge
		// 9.			end if
		// 10.		end for
		// 11.		Let p <- pair (v1, sum)
		// 12.		Append p to L
		// 13.	end for
		// 14.  Sort L by sum
	}
}