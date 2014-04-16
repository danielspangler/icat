package edu.odu.icat.analytics;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TestAlgorithm extends AnalyticsAlgorithm
{
    public String getName()
    {
        return "Test Algorithm";
    }

    public AlgorithmDialogBox getAlgorithmDialogBox()
    {
        return new TestAlgorithmDialogBox();
    }

    public void run()
    {
        new TestAlgorithmDialogBox();
    }

    public class TestAlgorithmDialogBox extends AnalyticsAlgorithm.AlgorithmDialogBox
    {
        protected  JCheckBox SomeotherCriteria;
        public TestAlgorithmDialogBox()
        {
            super();
            FilterContainer.removeAll();
            SomeotherCriteria = new JCheckBox("Hello");
            FilterContainer.add(SomeotherCriteria, BorderLayout.CENTER);
            setVisible(true);
        }
    }
}