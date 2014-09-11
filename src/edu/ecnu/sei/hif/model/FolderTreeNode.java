package edu.ecnu.sei.hif.model;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Node which represents a Folder in the javax.mail apis.
 *
 * @author If
 */
public class FolderTreeNode extends DefaultMutableTreeNode {

    protected File folder = null;
    protected boolean hasLoaded = false;

    /**
     * creates a tree node that points to the particular Store.
     *
     * @param what	the store for this node
     */
    public FolderTreeNode(File what) {
        super(what);
        folder = what;
    }

    /**
     * a Folder is a leaf if it cannot contain sub folders
     */
    @Override
    public boolean isLeaf() {
        try {
            if (!folder.isDirectory()) {
                return true;
            }
        } catch (Exception me) {
            me.printStackTrace();
        }

        // otherwise it does hold folders, and therefore not
        // a leaf
        return false;
    }

    /**
     * returns the folder for this node
     */
    public File getFolder() {
        return folder;
    }

    /**
     * return the number of children for this folder node. The first time this
     * method is called we load up all of the folders under the store's
     * defaultFolder
     */
    @Override
    public int getChildCount() {
        if (!hasLoaded) {
            loadChildren();
        }
        return super.getChildCount();
    }

    protected void loadChildren() {
        // if it is a leaf, just say we have loaded them
        if (isLeaf()) {
            hasLoaded = true;
            return;
        }

        try {
            // Folder[] sub = folder.listSubscribed();
            File[] sub = folder.listFiles();

            // add a FolderTreeNode for each Folder
            int num = sub.length;
            for (int i = 0, j = 0; i < num; i++) {
                if (sub[i].isDirectory()) {
                    FolderTreeNode node = new FolderTreeNode(sub[i]);
                    // we used insert here, since add() would make
                    // another recursive call to getChildCount();
                    insert(node, j);
                    j++;
                }
            }

        } catch (Exception me) {
            me.printStackTrace();
        }
    }

    /**
     * override toString() since we only want to display a folder's name, and
     * not the full path of the folder
     */
    @Override
    public String toString() {
        return folder.getName();
    }
}
