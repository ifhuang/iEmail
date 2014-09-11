package edu.ecnu.sei.hif.model;

import java.util.ArrayList;
import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;

public interface XGraph<V, E> extends UndirectedGraph<V, E>
{

	public static double DEFAULT_EDGE_WEIGHT = 1.0;

	public void setEdgeWeight(E e, double weight);

	// 获取节点vertex的邻居节点，即有边与其连接
	public Set<V> neighbors(V vertex);

	// 获得若干节点vertices间相互连接的边数
	public int numEdgesOf(Set<V> vertices);

	// 获取节点vertex的聚集系数
	public double clusteringCoefOf(V vertex);

	// 判断给定的路径中是否含有给定的点
	public boolean pathPassVertex(DijkstraShortestPath<String, XEdge> dPath,
			V vertex);

	// 获取节点vertex的介数（中间度核心性或中介性核心性）
	public double betweennessCentralityOf(
			ArrayList<DijkstraShortestPath<String, XEdge>> paths, V vertex);

	// 获取节点vertex的凝聚度
	public double cohesionOf(V vertex);
	
	public double weightSum(V vertex);

}
