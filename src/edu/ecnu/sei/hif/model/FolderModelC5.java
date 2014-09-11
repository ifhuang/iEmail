package edu.ecnu.sei.hif.model;

import edu.ecnu.sei.hif.control.Client;
import edu.ecnu.sei.hif.model.Address;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.mail.*;
import javax.swing.table.AbstractTableModel;

/**
 * Maps the messages in a Folder to the Swing's Table Model
 *
 * @author	If
 */
public class FolderModelC5 extends AbstractTableModel {

    String label;
    File[] folder;
    Email[] messages;
    String[] columnNames = {"Date", "From", "Subject", "Label"};
    Class[] columnTypes = {String.class, String.class, String.class, String.class};

    public void setFolder(File[] what) throws MessagingException {

        List<String> pathtmp = new LinkedList<>();
        label = "0";
        for (EmailLabel el : Client.importantlabel) {
            if (el.getLabel().equals("0")) {
                pathtmp.add(el.getPath());
            }
        }

        if (pathtmp != null) {
            // get the messages
            messages = new Email[pathtmp.size()];
            folder = new File[pathtmp.size()];
            int i = 0;
            for (String path : pathtmp) {
                messages[i] = new EmailImpl(path);
                folder[i] = new File(path);
                i++;          
            }
            cached = new String[messages.length][];
        } else {
            messages = null;
            cached = null;
        }
        // close previous folder and switch to new folder
        fireTableDataChanged();
    }

    public Email getMessage(int which) {
        return messages[which];
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
        if (messages == null) {
            return 0;
        }

        return messages.length;
    }

    @Override
    public Object getValueAt(int aRow, int aColumn) {
        switch (aColumn) {
            case 0:	// date
            case 1: // From		String[] what = getCachedData(aRow);
            case 2: // Subject
            case 3:
                String[] what = getCachedData(aRow);
                if (what != null) {
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
                Email m = messages[row];

                String[] theData = new String[5];

                // Date
                Date date = m.getSentDate();
                if (date == null) {
                    theData[0] = "Unknown";
                } else {
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
                    theData[0] = dateformat.format(date);
                }

                // From
                Address[] adds = m.getFrom();
                if (adds != null && adds.length != 0) {
                    theData[1] = adds[0].toString();
                } else {
                    theData[1] = "";
                }

                // Subject
                String subject = m.getSubject();
                if (subject != null) {
                    theData[2] = subject;
                } else {
                    theData[2] = "(No Subject)";
                }

                if (label != null) {
                    theData[3] = label;
                } else {
                    theData[3] = "No Label";
                }

                cached[row] = theData;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return cached[row];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        cached[row][column] = value.toString();
        for (EmailLabel el : Client.importantlabel) {
            if (el.getPath().equals(folder[row].getAbsolutePath())) {
                el.setLabel(value.toString());
                break;
            }
        }
    }
}
