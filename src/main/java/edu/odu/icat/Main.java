package edu.odu.icat;

import edu.odu.icat.graphicsinterface.Dashboard;
import edu.odu.icat.analytics.AnalyticsEngine;

import java.awt.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World");

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard frame = new Dashboard();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        new AnalyticsEngine();
	}

}
