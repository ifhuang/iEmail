/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.control;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author 一夫
 */
public class EmailNetworkXMLBuilder {

    public void build() {
        try {
            Client.network = DocumentHelper.createDocument();

            Element graphml = Client.network.addElement("graphml");
            graphml.addNamespace("xmlns",
                    "http://graphml.graphdrawing.org/xmlns");

            Element graph = graphml.addElement("graph");
            graph.addAttribute("edgedefault", "undirected");

            Element addressnode = graph.addElement("key");
            addressnode.addAttribute("id", "address");
            addressnode.addAttribute("for", "node");
            addressnode.addAttribute("attr.name", "address");
            addressnode.addAttribute("attr.type", "string");

            Element personalnode = graph.addElement("key");
            personalnode.addAttribute("id", "personal");
            personalnode.addAttribute("for", "node");
            personalnode.addAttribute("attr.name", "personal");
            personalnode.addAttribute("attr.type", "string");

            Element weightsumnode = graph.addElement("key");
            weightsumnode.addAttribute("id", "weightsum");
            weightsumnode.addAttribute("for", "node");
            weightsumnode.addAttribute("attr.name", "weightsum");
            weightsumnode.addAttribute("attr.type", "double");

            Element degreenode = graph.addElement("key");
            degreenode.addAttribute("id", "degree");
            degreenode.addAttribute("for", "node");
            degreenode.addAttribute("attr.name", "degree");
            degreenode.addAttribute("attr.type", "double");

            Element clusteringnode = graph.addElement("key");
            clusteringnode.addAttribute("id", "clustering");
            clusteringnode.addAttribute("for", "node");
            clusteringnode.addAttribute("attr.name", "clustering");
            clusteringnode.addAttribute("attr.type", "double");

            Element betweenessnode = graph.addElement("key");
            betweenessnode.addAttribute("id", "betweeness");
            betweenessnode.addAttribute("for", "node");
            betweenessnode.addAttribute("attr.name", "betweeness");
            betweenessnode.addAttribute("attr.type", "double");

            Element cohesionnode = graph.addElement("key");
            cohesionnode.addAttribute("id", "cohesion");
            cohesionnode.addAttribute("for", "node");
            cohesionnode.addAttribute("attr.name", "cohesion");
            cohesionnode.addAttribute("attr.type", "double");

            Element weightedge = graph.addElement("key");
            weightedge.addAttribute("id", "weight");
            weightedge.addAttribute("for", "edge");
            weightedge.addAttribute("attr.name", "weight");
            weightedge.addAttribute("attr.type", "integer");

            Element abstractedge = graph.addElement("key");
            abstractedge.addAttribute("id", "abstract");
            abstractedge.addAttribute("for", "edge");
            abstractedge.addAttribute("attr.name", "abstract");
            abstractedge.addAttribute("attr.type", "string");


            Map<String, Integer> addressid = new HashMap<String, Integer>();

            for (int i = 0; i < Client.computedAddress.length; i++) {
                String addr = Client.computedAddress[i].getAddress().getAddress();
                String personal = Client.computedAddress[i].getAddress().getPersonal();
                double[] coeff = Client.computedAddress[i].getCoefficient();

                addressid.put(addr, i + 1);

                Element node = graph.addElement("node");
                node.addAttribute("id", addressid.get(addr) + "");

                Element dataaddress = node.addElement("data");
                dataaddress.addAttribute("key", "address");
                dataaddress.setText(addr);

                Element datapersonal = node.addElement("data");
                datapersonal.addAttribute("key", "personal");
                datapersonal.setText(personal);

                Element dataweightsum = node.addElement("data");
                dataweightsum.addAttribute("key", "weightsum");
                dataweightsum.setText(coeff[0] + "");

                Element datadegree = node.addElement("data");
                datadegree.addAttribute("key", "degree");
                datadegree.setText(coeff[1] + "");

                Element dataclustering = node.addElement("data");
                dataclustering.addAttribute("key", "clustering");
                dataclustering.setText(coeff[2] + "");

                Element databetweeness = node.addElement("data");
                databetweeness.addAttribute("key", "betweeness");
                databetweeness.setText(coeff[3] + "");

                Element datacohesion = node.addElement("data");
                datacohesion.addAttribute("key", "cohesion");
                datacohesion.setText(coeff[4] + "");
            }

            for (int i = 0; i < Client.record.length; i++) {

                String addr1 = Client.record[i].getAddress1();
                String addr2 = Client.record[i].getAddress2();
                int num = Client.record[i].getNum();
                String temp = Client.record[i].getDatesubjectString();

                Element edge = graph.addElement("edge");
                edge.addAttribute("source", addressid.get(addr1) + "");
                edge.addAttribute("target", addressid.get(addr2) + "");

                Element dataweight = edge.addElement("data");
                dataweight.addAttribute("key", "weight");
                dataweight.setText(num + "");

                Element dataabstract = edge.addElement("data");
                dataabstract.addAttribute("key", "abstract");
                dataabstract.setText(temp);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /*
     public static void main(String[] args) {
     try {
     EmailNetworkParser test = new EmailNetworkParser("D:\\workspace\\netbeans\\Project\\data\\ALL");
     test.parse();
     EmailNetworkComputer test2 = new EmailNetworkComputer();
     test2.compute();
     EmailNetworkXMLBuilder test3 = new EmailNetworkXMLBuilder();
     test3.build();
     OutputFormat f = new OutputFormat("\t", true);
     f.setEncoding("utf8");
     XMLWriter w = new XMLWriter(new FileWriter("D:\\workspace\\netbeans\\Project\\data\\EmailNetwork_ALL.xml"), f);
     w.write(Client.network);
     w.flush();
     } catch (Exception e) {
     e.printStackTrace();
     }
     }
     */
}
