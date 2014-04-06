package edu.odu.icat.analytics;

import edu.odu.icat.model.Entity;

import java.util.*;
import javax.swing.*;

public class InfluenceAlgorithm extends AnalyticsAlgorithm
{
	public String getName()
    {
        return "Influence Algorithm";
    }

    public void run()
	{

        AlgorithmDialogBox adb = new AlgorithmDialogBox();

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
        adb.addEntityToReport(e);

        System.out.println(adb.getVisibility());
        System.out.println(adb.getControllability());
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