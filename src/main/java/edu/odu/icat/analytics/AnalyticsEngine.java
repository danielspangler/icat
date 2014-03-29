package edu.odu.icat.analytics;

import java.util.ArrayList;

import java.io.File;

import java.net.URL;
import java.net.URLClassLoader;

public class AnalyticsEngine
{
	public AnalyticsEngine()
	{
		loadAlgorithms();
	}

	private void loadAlgorithms()
	{
		/*
		 *	Load algorithms found in Algorithms directory
		 */

		File dir = new File("Algorithms");
		System.out.println(dir.getAbsolutePath());
		
		try
		{
			URL loadPath = dir.toURI().toURL();
			URL[] classUrl = new URL[]{loadPath};
			ClassLoader cl = new URLClassLoader(classUrl);

			try{Class loadedClass = cl.loadClass("classname");}
			catch(java.lang.ClassNotFoundException e){System.out.println("Class does not exist");}
		}
		catch(java.net.MalformedURLException e){}		

	}

	public void setCurrentAlgorithm()
	{
		/*
		 *	drop down in gui will be able to set algorithm
		 */

	}

	public void runCurrentAlgorithm()
	{
		/*
		 *	run the selected algorithm
		 */

	}

	public ArrayList<String> getAlgorithms()
	{
		return new ArrayList<String>();
	}
}