package edu.odu.icat.analytics;

import java.util.*;

import java.util.HashMap;

public class AnalyticsEngine
{
    private static AnalyticsEngine ourInstance = new AnalyticsEngine();
    private Map<String, AnalyticsAlgorithm> CurrentAlgorithms = new HashMap<String, AnalyticsAlgorithm>();

    private AnalyticsEngine()
	{
		loadAlgorithms();
	}

    public static AnalyticsEngine getInstance() {
        return ourInstance;
    }

	private void loadAlgorithms()
	{
		/*
		 *	Load algorithms found in Algorithms directory
		 *  Does not work yet
		 */

//		File dir = new File("Algorithms");
//		System.out.println(dir.getAbsolutePath());
//
//		try
//		{
//			URL loadPath = dir.toURI().toURL();
//			URL[] classUrl = new URL[]{loadPath};
//			ClassLoader cl = new URLClassLoader(classUrl);
//
//			try{Class loadedClass = cl.loadClass("classname");}
//			catch(java.lang.ClassNotFoundException e){System.out.println("Class does not exist");}
//		}
//		catch(java.net.MalformedURLException e){}

        AnalyticsAlgorithm a1 = new InfluenceAlgorithm();
        CurrentAlgorithms.put(a1.getName(), a1);
        a1 = new ProminenceAlgorithm();
        CurrentAlgorithms.put(a1.getName(), a1);
        a1 = new TestAlgorithm();
        CurrentAlgorithms.put(a1.getName(), a1);

	}

	public AnalyticsAlgorithm.AlgorithmDialogBox getAlgorithmDialog(String algorithm)
	{
		/*
		 *	drop down in gui will be able to set algorithm
		 */

        return CurrentAlgorithms.get(algorithm).getAlgorithmDialogBox();
	}

    /*
	 *	run the selected algorithm
	 *
	 * @param algorithm the name of the algorithm to run.
     */
	public void runAlgorithm(String algorithm)
	{

         CurrentAlgorithms.get(algorithm).run();
	}

	public List<String> getAlgorithms()
	{
        return new ArrayList<String>(CurrentAlgorithms.keySet());
	}
}