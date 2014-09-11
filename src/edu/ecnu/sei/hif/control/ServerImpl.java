package edu.ecnu.sei.hif.control;

import java.security.Security;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class ServerImpl implements Server {

    private Properties props;
    private Session session;
    private URLName urln;
    private Store store;
    private Folder folder;

    public ServerImpl(String url) {
        System.out.println("add ssl provider...");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        System.out.println("get properties...");
        this.props = System.getProperties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.port", "993");
        props.setProperty("mail.imap.socketFactory.port", "993");

        System.out.println("get session...");
        this.session = Session.getInstance(this.props);

        System.out.println("get url name...");
        this.urln = new URLName(url);

        try {
            System.out.println("get store...");
            this.store = this.session.getStore(urln);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            System.out.println("store connect...");
            this.store.connect();
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            System.out.println("get folder...");
            this.folder = this.store.getDefaultFolder();
        } catch (MessagingException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public Store getStore() {
        return this.store;
    }

    @Override
    public Folder getRootFolder() {
        return this.folder;
    }
}
