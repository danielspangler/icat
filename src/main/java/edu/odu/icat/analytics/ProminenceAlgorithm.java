package edu.odu.icat.analytics;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ProminenceAlgorithm extends AnalyticsAlgorithm
{
    public String getName()
    {
        return "Prominence Algorithm";
    }

    public void run()
    {
        new ProminenceAlgorithmDialogBox();
    }

    public class ProminenceAlgorithmDialogBox extends AnalyticsAlgorithm.AlgorithmDialogBox
    {
        protected  JCheckBox SomeotherCriteria;
        public ProminenceAlgorithmDialogBox()
        {

            super();
            SomeotherCriteria = new JCheckBox("Hello");
            FilterContainer.add(SomeotherCriteria, BorderLayout.CENTER);
            setVisible(true);
        }
    }
}