/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.control;

import edu.ecnu.sei.hif.algorithm.XDefaultGraph;
import edu.ecnu.sei.hif.model.Address;
import edu.ecnu.sei.hif.model.ComputedAddress;
import edu.ecnu.sei.hif.model.XEdge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.alg.DijkstraShortestPath;

/**
 *
 * @author 一夫
 */
public class EmailNetworkComputer {

    private static XDefaultGraph<String, XEdge> g;
    private static Map<String, String> map;

    public EmailNetworkComputer() {
        g = new XDefaultGraph<String, XEdge>(XEdge.class);
        map = new HashMap<String, String>();
    }

    private void initial() {
        for (int i = 0; i < Client.addressBook.length; i++) {
            map.put(Client.addressBook[i].getAddress(), Client.addressBook[i].getPersonal());
            g.addVertex(Client.addressBook[i].getAddress());
        }
        for (int i = 0; i < Client.record.length; i++) {
            XEdge edge = g.addEdge(Client.record[i].getAddress1(), Client.record[i].getAddress2());
            if (edge != null) {
                edge.setWeight(Client.record[i].getNum());
                g.setEdgeWeight(edge, Client.record[i].getNum());
            }
        }
        System.out.println("initial success...");
    }

    public void compute() {
        initial();
        Client.computedAddress = new ComputedAddress[Client.addressBook.length];
        double t1, t2, t3, t4, t5;
        ArrayList<DijkstraShortestPath<String, XEdge>> paths = g.getPaths();
        int counter = 0;
        for (String eachnode : g.vertexSet()) {
            System.out.println("compute:" + eachnode + "...");

            t1 = g.weightSum(eachnode);
            t2 = g.degreeOf(eachnode);
            t3 = g.clusteringCoefOf(eachnode);
            t4 = g.betweennessCentralityOf(paths, eachnode);
            t5 = g.cohesionOf(eachnode);

            Address addr = new Address();
            addr.setAddress(eachnode);
            addr.setPersonal(map.get(eachnode));
            double[] coefficient = {t1, t2, t3, t4, t5};

            Client.computedAddress[counter] = new ComputedAddress();
            Client.computedAddress[counter].setAddress(addr);
            Client.computedAddress[counter].setCoefficient(coefficient);

            counter++;
        }
    }

    /*
     public static void main(String[] agrs) {
     EmailNetworkParser test = new EmailNetworkParser("D:\\workspace\\netbeans\\Project\\data\\Sent Messages");
     test.parse();
     EmailNetworkComputer test2 = new EmailNetworkComputer();
     test2.compute();
     System.out.println(Client.computedAddress.length);
     for (int i = 0; i < Client.computedAddress.length; i++) {
     System.out.print(Client.computedAddress[i].getAddress().getAddress());
     System.out.print("\t");
     System.out.print(Client.computedAddress[i].getAddress().getPersonal());
     System.out.print("\t");
     for (int j = 0; j < Client.computedAddress[i].getCoefficient().length; j++) {
     System.out.print(Client.computedAddress[i].getCoefficient()[j]);
     System.out.print("\t");
     }
     System.out.println();
     }


     }
     */
}
