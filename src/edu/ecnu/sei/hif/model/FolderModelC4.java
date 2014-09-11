package edu.ecnu.sei.hif.model;

import edu.ecnu.sei.hif.control.Client;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.mail.*;
import javax.swing.table.AbstractTableModel;

/**
 * Maps the messages in a Folder to the Swing's Table Model
 *
 * @author	If
 */
public class FolderModelC4 extends AbstractTableModel {

    String label;
    File[] folder;
    Email[] messages;
    String[] columnNames = {"Date", "From", "Subject", "Label"};
    Class[] columnTypes = {String.class, String.class, String.class, String.class};

    public void setFolder(File[] what) throws MessagingException {

        label = "1";
        File[] inboxFile = what[0].listFiles();
        Email[] inboxEmail = new Email[inboxFile.length];
        for (int i = 0; i < inboxFile.length; i++) {
            inboxEmail[i] = new EmailImpl(inboxFile[i].getAbsolutePath());
        }

        //System.out.println(inboxFile.length);

        File[] sentFile = what[1].listFiles();
        Email[] sentEmail = new Email[sentFile.length];
        for (int i = 0; i < sentFile.length; i++) {
            sentEmail[i] = new EmailImpl(sentFile[i].getAbsolutePath());
        }

        //System.out.println(sentFile.length);

        Set<String> importantSet = new HashSet<>();
        for (int i = 0; i < sentEmail.length; i++) {
            for (int j = 0; j < inboxEmail.length; j++) {
                if (sentEmail[i].getSentDate().after(inboxEmail[j].getSentDate())) {
                    continue;
                }
                int flag = 0;
                String inboxFrom = inboxEmail[j].getFrom()[0].getAddress();
                Address[] sentTo = sentEmail[i].getTo();
                Address[] sentCc = sentEmail[i].getCc();
                if (sentTo != null) {
                    for (int k = 0; k < sentTo.length; k++) {
                        if (sentTo[k].getAddress().equals(inboxFrom)) {
                            importantSet.add(inboxFile[j].getAbsolutePath());
                            flag = 1;
                            break;
                        }
                    }
                }
                if (flag == 0) {
                    if (sentCc != null) {
                        for (int k = 0; k < sentCc.length; k++) {
                            if (sentCc[k].getAddress().equals(inboxFrom)) {
                                importantSet.add(inboxFile[j].getAbsolutePath());
                                flag = 1;
                                break;
                            }
                        }
                    }
                }
                if (flag == 0) {
                    String inboxSubject = inboxEmail[j].getSubject();
                    String sentSubject = sentEmail[i].getSubject();
                    if (inboxSubject != null && sentSubject != null) {
                        if (inboxSubject.contains(sentSubject)) {
                            importantSet.add(inboxFile[j].getAbsolutePath());
                        }
                    }
                }
            }
        }

        //System.out.println(importantSet.size());

        for (int i = 0; i < inboxFile.length; i++) {
            if (importantSet.contains(inboxFile[i].getAbsolutePath())) {
                EmailLabel el = new EmailLabel();
                el.setPath(inboxFile[i].getAbsolutePath());
                el.setLabel("1");
                Client.importantlabel.add(el);
                //System.out.println(el);
            } else {
                EmailLabel el = new EmailLabel();
                el.setPath(inboxFile[i].getAbsolutePath());
                el.setLabel("0");
                Client.importantlabel.add(el);
                //System.out.println(el);
            }
        }

        if (importantSet != null) {
            // get the messages
            messages = new Email[importantSet.size()];
            folder = new File[importantSet.size()];
            int i = 0;
            for (String path : importantSet) {
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
                //System.out.println(m);
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
