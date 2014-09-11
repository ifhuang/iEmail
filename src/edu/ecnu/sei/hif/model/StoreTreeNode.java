package edu.ecnu.sei.hif.model;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Node which represents a Store in the javax.mail apis.
 *
 * @author If
 */
public class StoreTreeNode extends DefaultMutableTreeNode {

    protected File store = null;
    protected File folder = null;

    /**
     * creates a tree node that points to the particular Store.
     *
     * @param what	the store for this node
     */
    public StoreTreeNode(File what) {
        super(what);
        store = what;
    }

    /**
     * a Store is never a leaf node. It can always contain stuff
     */
    @Override
    public boolean isLeaf() {
        return false;
    }

    /**
     * return the number of children for this store node. The first time this
     * method is called we load up all of the folders under the store's
     * defaultFolder
     */
    @Override
    public int getChildCount() {
        if (folder == null) {
            loadChildren();
        }
        return super.getChildCount();
    }

    protected void loadChildren() {
        try {
            // connect to the Store if we need to
            if (!store.exists() || !store.isDirectory()) {
                System.out.println("wrong store");
                System.exit(1);
            }

            // get the default folder, and list the
            // subscribed folders on it
            folder = store;
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
     * We override toString() so we can display the store URLName without the
     * password.
     */
    @Override
    public String toString() {
        return "ROOT";
    }
}
