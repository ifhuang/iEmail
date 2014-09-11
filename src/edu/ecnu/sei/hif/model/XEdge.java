package edu.ecnu.sei.hif.model;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;

public class XEdge extends DefaultEdge{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898539232402931887L;
	double weight = WeightedGraph.DEFAULT_EDGE_WEIGHT;
	public void setWeight(double w)
	{
		weight = w;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public String toString()
	{
		return Double.toString(weight) + " " + super.toString();
	}
		
}
