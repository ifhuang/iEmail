package edu.ecnu.sei.hif.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.*;
import javax.swing.table.AbstractTableModel;

/**
 * Maps the messages in a Folder to the Swing's Table Model
 *
 * @author	If
 */
public class FolderModel extends AbstractTableModel {
    
    File folder;
    Email[] messages;
    String[] columnNames = {"Date", "From", "Subject"};
    Class[] columnTypes = {String.class, String.class, String.class};
    
    public void setFolder(File what) throws MessagingException {
        if (what != null) {

            // opened if needed
            if (!what.exists()) {
                System.out.println("wrong folder");
                System.exit(1);
            }

            // get the messages
            File[] messagesFile = what.listFiles();
            messages = new Email[messagesFile.length];
            for (int i = 0; i < messagesFile.length; i++) {
                messages[i] = new EmailImpl(messagesFile[i].getAbsolutePath());
            }
            cached = new String[messages.length][];
        } else {
            messages = null;
            cached = null;
        }
        // close previous folder and switch to new folder
        folder = what;
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
                
                String[] theData = new String[4];

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
                
                cached[row] = theData;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return cached[row];
    }
}
