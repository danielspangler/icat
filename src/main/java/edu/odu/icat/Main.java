package edu.odu.icat;

import edu.odu.icat.graphicsinterface.Dashboard;

import java.awt.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("This is your Dashboard");

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
	}

}
