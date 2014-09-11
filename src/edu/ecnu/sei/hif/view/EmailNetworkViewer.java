/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.FontAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.SubtreeDragControl;
import prefuse.controls.ToolTipControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.GraphicsLib;
import prefuse.util.display.DisplayLib;
import prefuse.util.ui.JFastLabel;
import prefuse.visual.EdgeItem;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;

/**
 *
 * @author 一夫
 */
public class EmailNetworkViewer {

    private static Graph graph;
    private static Visualization vis;
    private static Display display;
    private static LabelRenderer labelrenderer;
    private static EdgeRenderer edgerenderer;
    private static String GRAPH = "graph";
    private static String GRAPHEDGES = "graph.edges";
    private static String GRAPHNODES = "graph.nodes";
    private static String ADDRESS = "address";
    private static String PERSONAL = "personal";
    private static String WEIGHTSUM = "weightsum";
    private static String DEGREE = "degree";
    private static String CLUSTERING = "clustering";
    private static String BETWEENESS = "betweeness";
    private static String COHESION = "cohesion";
    private static String WEIGHT = "weight";
    private static String ABSTRACT = "abstract";

    public EmailNetworkViewer(String path) {
        try {
            //OutputFormat f = new OutputFormat("\t", true);
            //f.setEncoding("gb2312");
            //XMLWriter w = new XMLWriter(new FileWriter("D:\\workspace\\netbeans\\Project\\data\\EmailNetwork.graphml"), f);
            //w.write(Client.network);
            //w.flush();
            graph = new GraphMLReader().readGraph(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel view() {

        System.out.println("The 1 step ending.");
        System.out.println("Begin the 2 step.");

        vis = new Visualization();
        vis.add(GRAPH, graph);

        System.out.println("The 2 step ending.");
        System.out.println("Begin the 3 step.");

        labelrenderer = new LabelRenderer(PERSONAL);
        labelrenderer.setRoundedCorner(8, 8);
        edgerenderer = new EdgeRenderer();
        DefaultRendererFactory dr = new DefaultRendererFactory(labelrenderer);
        dr.add(new InGroupPredicate(GRAPHEDGES), edgerenderer);
        vis.setRendererFactory(dr);

        System.out.println("The 3 step ending.");
        System.out.println("Begin the 4 step.");

        DataColorAction fill = new DataColorAction(GRAPHNODES, WEIGHTSUM,
                Constants.NOMINAL, VisualItem.FILLCOLOR);
        ColorAction text = new ColorAction(GRAPHNODES, VisualItem.TEXTCOLOR,
                ColorLib.gray(0));
        ColorAction edges = new ColorAction(GRAPHEDGES, VisualItem.STROKECOLOR,
                ColorLib.gray(200));
        FontAction fonts = new FontAction(GRAPHNODES, FontLib.getFont("微软雅黑",
                16));

        ActionList color = new ActionList();
        color.add(fill);
        color.add(text);
        color.add(edges);
        color.add(fonts);

        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(new MyForceDirectedLayout(GRAPH));
        layout.add(new RepaintAction());
        vis.putAction("color", color);
        vis.putAction("layout", layout);

        System.out.println("The 4 step ending.");
        System.out.println("Begin the 5 step.");

        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        title.setFont(FontLib.getFont("微软雅黑", Font.PLAIN, 16));

        display = new Display(vis);
        display.setSize(1200, 630);
        display.addControlListener(new DragControl());
        display.addControlListener(new SubtreeDragControl());
        display.addControlListener(new PanControl());
        display.addControlListener(new WheelZoomControl());
        display.addControlListener(new ToolTipControl(ADDRESS));
        display.addControlListener(new ToolTipControl(ABSTRACT));
        display.addControlListener(new ZoomToFitControl());
        display.addControlListener(new ControlAdapter() {
            public void itemEntered(VisualItem item, MouseEvent e) {
                if (item.canGetString(PERSONAL) || item.canGetString(ADDRESS)
                        || item.canGetDouble(WEIGHTSUM)
                        || item.canGetDouble(DEGREE)
                        || item.canGetDouble(CLUSTERING)
                        || item.canGetDouble(BETWEENESS)
                        || item.canGetDouble(COHESION)) {
                    title.setText(PERSONAL + ": " + item.getString(PERSONAL)
                            + "     " + ADDRESS + ": "
                            + item.getString(ADDRESS) + "     " + WEIGHTSUM
                            + ": " + item.getDouble(WEIGHTSUM) + "     "
                            + DEGREE + ": " + item.getDouble(DEGREE) + "     "
                            + CLUSTERING + ": " + item.getDouble(CLUSTERING)
                            + "     " + BETWEENESS + ": "
                            + item.getDouble(BETWEENESS) + "     " + COHESION
                            + ": " + item.getDouble(COHESION));
                    item.setFont(FontLib.getFont("微软雅黑", 24));
                }
                if (item.canGetString(ABSTRACT)) {
                    NodeItem source = ((EdgeItem) item).getSourceItem();
                    NodeItem target = ((EdgeItem) item).getTargetItem();
                    title.setText(" source: " + source.getString(PERSONAL)
                            + "     target:" + target.getString(PERSONAL)
                            + "     " + WEIGHT + ": " + item.getInt(WEIGHT)
                            + "     " + ABSTRACT + ": "
                            + item.getString(ABSTRACT));
                    item.setSize(5);
                    source.setFont(FontLib.getFont("微软雅黑", 24));
                    target.setFont(FontLib.getFont("微软雅黑", 24));
                }
            }

            public void itemExited(VisualItem item, MouseEvent e) {
                if (item.canGetString(PERSONAL) || item.canGetString(ADDRESS)
                        || item.canGetDouble(WEIGHTSUM)
                        || item.canGetDouble(DEGREE)
                        || item.canGetDouble(CLUSTERING)
                        || item.canGetDouble(BETWEENESS)
                        || item.canGetDouble(COHESION)) {
                    title.setText(null);
                    item.setFont(FontLib.getFont("微软雅黑", 16));
                }
                if (item.canGetString(ABSTRACT)) {
                    title.setText(null);
                    item.setSize(1);
                    (((EdgeItem) item).getSourceItem()).setFont(FontLib
                            .getFont("微软雅黑", 16));
                    (((EdgeItem) item).getTargetItem()).setFont(FontLib
                            .getFont("微软雅黑", 16));
                }

            }
        });

        System.out.println("The 5 step ending.");
        System.out.println("Begin the 6 step.");

        JPanel panel = new JPanel();

        panel.add(display, BorderLayout.CENTER);
        panel.add(title, BorderLayout.SOUTH);
        panel.setVisible(true);


        vis.run("color");
        vis.run("layout");
        ZoomToFitByStart();

        return panel;
    }

    public static void ZoomToFitByStart() {
        boolean b = true;
        if (!display.isTranformInProgress() && b) {
            b = false;
            String m_group = Visualization.ALL_ITEMS;
            long m_duration = 1000;
            Rectangle2D bounds = vis.getBounds(m_group);
            GraphicsLib.expand(bounds, 300 + (int) (1 / display.getScale()));
            DisplayLib.fitViewToBounds(display, bounds, m_duration);
        }
    }
}

class MyForceDirectedLayout extends ForceDirectedLayout {

    public MyForceDirectedLayout(String graph) {
        super(graph);
    }

    @Override
    protected float getSpringLength(EdgeItem e) {
        float f;
        f = e.canGetFloat("weight") ? 300 / e.getFloat("weight") : 20;
        return f;
    }
    /*
     public static void main(String[] args)
     {
     EmailNetworkViewer test = new EmailNetworkViewer();
        
     JFrame frame = new JFrame("EmailNetworkViewer");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.add(test.view());
     frame.pack();
     frame.setVisible(true);
     }
     */
}
