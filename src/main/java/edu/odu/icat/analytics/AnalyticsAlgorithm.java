package edu.odu.icat.analytics;

public abstract class AnalyticsAlgorithm implements  Runnable
{
	public abstract String getName();

    public abstract void run();
}