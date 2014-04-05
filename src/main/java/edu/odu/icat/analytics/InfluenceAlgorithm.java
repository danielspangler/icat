package edu.odu.icat.analytics;

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