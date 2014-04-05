package edu.odu.icat.analytics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AnalyticsAlgorithm implements  Runnable
{
    protected boolean VisibilityFilter;
    protected boolean ControllabilityFilter;

	public abstract String getName();

    public abstract void run();

    public class AlgorithmDialogBox extends JFrame
    {
        protected JCheckBox visibilityCheck;
        protected JCheckBox controllabilityCheck;

        public AlgorithmDialogBox()
        {

            setSize(400, 300);
            setLayout(new BorderLayout());

            visibilityCheck = new JCheckBox("Visibility");
            add(visibilityCheck, BorderLayout.NORTH);
            controllabilityCheck = new JCheckBox("Controllability");
            add(controllabilityCheck, BorderLayout.SOUTH);
            setVisible(true);
        }

        public boolean getVisibility()
        {
            return visibilityCheck.isSelected();
        }

        public boolean getControllability()
        {
            return controllabilityCheck.isSelected();
        }
    }
}