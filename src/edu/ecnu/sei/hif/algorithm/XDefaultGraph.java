package edu.ecnu.sei.hif.algorithm;

import edu.ecnu.sei.hif.model.XEdge;
import edu.ecnu.sei.hif.model.XGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.EdgeFactory;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;

public class XDefaultGraph<V, E> extends AbstractBaseGraph<V, E> implements
		XGraph<V, E>
{

	private static final long serialVersionUID = -8863419065506501111L;
	protected final Map<E, Double> weightMap;

	public XDefaultGraph(Class<? extends E> edgeClass)
	{
		this(new ClassBasedEdgeFactory<V, E>(edgeClass));
	}

	public XDefaultGraph(EdgeFactory<V, E> ef)
	{
		super(ef, false, true);
		weightMap = new HashMap<E, Double>();
	}

	public void setEdgeWeight(E e, double weight)
	{
		weightMap.put(e, weight);
	}

	public double getEdgeWeight(E e)
	{
		return weightMap.get(e);
	}

	public double weightSum(V vertex)
	{
		double sum = 0;
		Set<E> eSet = super.edgesOf(vertex);
		for (E e : eSet)
		{
			sum += getEdgeWeight(e);
		}
		return sum;
	}

	public Set<V> neighbors(V vertex)
	{
		Set<V> neigSet = new LinkedHashSet<V>();
		Set<E> eSet = super.edgesOf(vertex);
		for (E e : eSet)
		{
			V sV = super.getEdgeSource(e);
			V tV = super.getEdgeTarget(e);
			if (!vertex.equals(sV))
			{
				neigSet.add(sV);
			}
			else if (!vertex.equals(tV))
			{
				neigSet.add(tV);
			}
			else
			{

			}
		}
		return neigSet;
	}

	// 获得若干节点vertices间相互连接的边数
	public int numEdgesOf(Set<V> vertices)
	{
		LinkedHashSet<E> eAllSet = new LinkedHashSet<E>();
		for (V v : vertices)
		{
			Set<E> eOutSet = super.edgesOf(v);
			for (E e : eOutSet)
			{
				V tgt = super.getEdgeTarget(e);
				V src = super.getEdgeSource(e);
				if (vertices.contains(tgt) && vertices.contains(src))
				{
					eAllSet.add(e);
				}
			}
		}
		// System.out.println("邻居间的相互连接数:" + eAllSet.toString());
		return eAllSet.size();
	}

	// 获取节点vertex的聚集系数
	// 对于节点v，如果有k个节点与v相连，则这k个节点称为v的邻居
	// 这k个邻居之间可能存在k(k-1)条边
	// 这k个邻居之间实际存在的边与总的可能边数之比定义为v的聚集系数
	public double clusteringCoefOf(V vertex)
	{
		Set<V> neigSet = neighbors(vertex);
		int k = neigSet.size();
		if (k == 1 || k == 0)// k * (k - 1)=0
			return 0;
		return (double) numEdgesOf(neigSet) / (double) (k * (k - 1));
	}

	/*
	 * 判断给定的路径中是否含有给定的点
	 */
	public boolean pathPassVertex(DijkstraShortestPath<String, XEdge> dPath,
			V vertex)
	{
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<E> edges = (List<E>) dPath.getPathEdgeList();
		if (edges == null)
			return false;
		for (E e : edges)
		{
			V tgt = super.getEdgeTarget(e);
			V src = super.getEdgeSource(e);
			if (tgt.equals(vertex) || src.equals(vertex))
				return true;
		}
		return false;
	}

	public ArrayList<DijkstraShortestPath<String, XEdge>> getPaths()
	{
		System.out.print("getAllShortestPaths...");
		ArrayList<DijkstraShortestPath<String, XEdge>> paths = new ArrayList<DijkstraShortestPath<String, XEdge>>();
		int i, j;
		Set<V> vset = this.vertexSet();
		Object[] varray = vset.toArray();
		for (i = 0; i < varray.length - 1; i++)
			for (j = i + 1; j < varray.length; j++)
			{
				@SuppressWarnings("unchecked")
				DijkstraShortestPath<String, XEdge> dPath = new DijkstraShortestPath<String, XEdge>(
						(XDefaultGraph<String, XEdge>) this,
						varray[i].toString(), varray[j].toString());
				paths.add(dPath);
			}
		System.out.println("success");
		return paths;
	}

	// 获取节点vertex的介数
	/*
	 * 对于节点v，网络中包含v的所有最短路径的条数为shortestv 网络中所有的最短路径的条数为shortest
	 * shortestv/shortest定义为v的介数 这里默认shortest为n*(n-1)/2
	 */
	public double betweennessCentralityOf(
			ArrayList<DijkstraShortestPath<String, XEdge>> paths, V vertex)
	{
		int shortestv = 0;
		Set<V> vset = this.vertexSet();
		int n = vset.size();
		double shortest = n * (n - 1) / 2;
		for (int i = 0; i < paths.size(); i++)
		{
			if (pathPassVertex(paths.get(i), vertex))
			{
				shortestv++;
			}
		}
		return (double) shortestv / (double) shortest;
	}

	// 获取节点vertex的凝聚度
	/*
	 * 对于节点v，从该节点到网络中其他所有节点所需路径之和为该点的凝聚度
	 */
	public double cohesionOf(V vertex)
	{
		double cohesion = 0;
		Set<V> vset = this.vertexSet();
		for (V v : vset)
		{
			if (v.equals(vertex))// 排除自身节点
				continue;
			@SuppressWarnings("unchecked")
			DijkstraShortestPath<String, XEdge> dPath = new DijkstraShortestPath<String, XEdge>(
					(XDefaultGraph<String, XEdge>) this, vertex.toString(),
					v.toString());
			if (Double.isInfinite(dPath.getPathLength()))
				continue;
			cohesion += dPath.getPathLength();
		}
		return cohesion;
	}

}
