package edu.ecnu.sei.hif.control;

import com.sun.mail.imap.IMAPFolder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.mail.Folder;
import javax.mail.Message;

public class Receiver extends Thread {

    private Folder rootFolder;
    private String storePath;

    public Receiver(Folder folder, String path) {
        this.rootFolder = folder;
        this.storePath = path;
    }

    public void saveMail() {
        try {
            if ((this.rootFolder.getType() & Folder.HOLDS_FOLDERS) != 0) {
                Folder[] f = this.rootFolder.list("%");
                for (int i = 0; i < f.length; i++) {
                    dumpFolder(f[i], true, "\t", this.storePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void dumpFolder(Folder folder, boolean recurse, String tab,
            String path) throws Exception {
        System.out.println(tab + "Name:      " + folder.getName());
        System.out.println(tab + "Full Name: " + folder.getFullName());
        System.out.println(tab + "URL:       " + folder.getURLName());

        if (true) {
            if (!folder.isSubscribed()) {
                System.out.println(tab + "Not Subscribed");
            }

            if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
                if (folder.hasNewMessages()) {
                    System.out.println(tab + "Has New Messages");
                }

                System.out.println(tab + "Total Messages:  "
                        + folder.getMessageCount());
                System.out.println(tab + "New Messages:    "
                        + folder.getNewMessageCount());
                System.out.println(tab + "Unread Messages: "
                        + folder.getUnreadMessageCount());

                File f = new File(path + "\\" + folder.getName());
                f.mkdir();
                folder.open(Folder.READ_ONLY);
                Message[] m = folder.getMessages();
                int count = 0;
                BufferedOutputStream bos = null;

                System.out.println("Total mail " + m.length);
                System.out.println("-----start save mail-----");
                for (int i = 0; i < m.length; i++) {
                    System.out.print("Now save " + i + " mail...");
                    File temp = new File(path + "\\" + folder.getName() + "\\"
                            + i);
                    bos = new BufferedOutputStream(new FileOutputStream(temp));
                    try {
                        m[i].writeTo(bos);
                        System.out.println("success");
                        count++;
                    } catch (Exception e) {
                        System.out.println("fail");
                    } finally {
                        bos.close();
                        if (temp.length() == 0) {
                            temp.delete();
                        }
                    }
                }
                System.out.println("-----end save mail-----");
                System.out.println(count + "/" + m.length + " have been saved");
            }
            if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
                System.out.println(tab + "Is Directory");
            }

            if (folder instanceof IMAPFolder) {
                IMAPFolder f = (IMAPFolder) folder;
                String[] attrs = f.getAttributes();
                if (attrs != null && attrs.length > 0) {
                    System.out.println(tab + "IMAP Attributes:");
                    for (int i = 0; i < attrs.length; i++) {
                        System.out.println(tab + "\t" + attrs[i]);
                    }
                }
            }
        }
        System.out.println();
        if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
            if (recurse) {
                Folder[] f = folder.list();
                for (int i = 0; i < f.length; i++) {
                    dumpFolder(f[i], recurse, tab + "\t", path);
                }
            }
        }
    }
}
