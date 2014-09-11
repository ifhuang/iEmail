package edu.ecnu.sei.hif.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import javax.mail.*;
import javax.swing.table.AbstractTableModel;

/**
 * Maps the messages in a Folder to the Swing's Table Model
 *
 * @author	If
 */
public class FolderModelI1 extends AbstractTableModel {

    File folder;
    String[] columnNames = {"Personal", "Address", "Phone", "Tax", "Company", "Title", "Realaddress", "Site", "Imageurl", "Birthday", "Id"};
    Class[] columnTypes = {String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    List<String> lineList;
    int count;

    public void setFolder(File what) throws MessagingException {
        if (what != null) {

            // opened if needed
            if (!what.exists()) {
                System.out.println("wrong folder");
                System.exit(1);
            }

            lineList = new LinkedList<>();
            count = 0;
            folder = what;
            // get the messages
            try {
                BufferedReader br = new BufferedReader(new FileReader(folder));
                String line = br.readLine();
                while (line != null) {
                    lineList.add(line);
                    line = br.readLine();
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cached = new String[count][];
        } else {
            cached = null;
        }
        // close previous folder and switch to new folder        
        fireTableDataChanged();
    }

    //---------------------
    // Implementation of the TableModel methods
    //---------------------
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnTypes[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return count;
    }

    @Override
    public Object getValueAt(int aRow, int aColumn) {
        switch (aColumn) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                String[] what = getCachedData(aRow);
                if (aColumn < what.length) {
                    return what[aColumn];
                } else {
                    return "";
                }
            default:
                return "";
        }
    }
    protected static String[][] cached;

    protected String[] getCachedData(int row) {
        if (cached[row] == null) {
            try {
                String line = lineList.get(row);
                String[] theData = line.split("\t");
                cached[row] = theData;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return cached[row];
    }
}
