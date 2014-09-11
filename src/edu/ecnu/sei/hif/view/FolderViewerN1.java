package edu.ecnu.sei.hif.view;

import edu.ecnu.sei.hif.model.FolderModelN1;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;

/**
 * @author	If
 */
public class FolderViewerN1 extends JPanel {

    FolderModelN1 model;
    JScrollPane scrollpane;
    JTable table;

    public FolderViewerN1() {
        this(null);
    }

    public FolderViewerN1(File what) {
        super(new GridLayout(1, 1));
        model = new FolderModelN1();
        table = new JTable(model);
        table.setShowGrid(false);

        scrollpane = new JScrollPane(table);

        // setup the folder we were given
        setFolder(what);

        // find out what is pressed
        table.getSelectionModel().addListSelectionListener(
                new FolderPressed());
        scrollpane.setPreferredSize(new Dimension(700, 300));
        add(scrollpane);
    }

    /**
     * Change the current Folder for the Viewer
     *
     * @param what	the folder to be viewed
     */
    public void setFolder(File what) {
        try {
            table.getSelectionModel().clearSelection();
            model.setFolder(what);
            scrollpane.invalidate();
            scrollpane.validate();
        } catch (Exception me) {
            me.printStackTrace();
        }
    }

    class FolderPressed implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (model != null && !e.getValueIsAdjusting()) {
                ListSelectionModel lm = (ListSelectionModel) e.getSource();
                int which = lm.getMaxSelectionIndex();
                if (which != -1) {
                    // get the message and display it
                }
            }
        }
    }
}
