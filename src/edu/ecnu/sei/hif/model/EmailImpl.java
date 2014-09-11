package edu.ecnu.sei.hif.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class EmailImpl implements Email {

    private MimeMessage mimeMessage;

    public EmailImpl(MimeMessage m) {
        this.mimeMessage = m;
    }

    public EmailImpl(String path) {
        try {
            InputStream fis = new FileInputStream(path);
            Session mailSession = Session.getDefaultInstance(
                    System.getProperties(), null);
            try {
                this.mimeMessage = new MimeMessage(mailSession, fis);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address[] getFrom() {
        try {
            InternetAddress address[] = (InternetAddress[]) mimeMessage
                    .getFrom();
            Address[] from = new Address[address.length];

            for (int i = 0; i < address.length; i++) {
                from[i] = new Address();
                from[i].setAddress(address[i].getAddress());
                from[i].setPersonal(address[i].getPersonal());
            }
            return from;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setFrom(Address[] addr) {
        try {
            InternetAddress address = new InternetAddress();
            address.setAddress(addr[0].getAddress());
            address.setPersonal(addr[0].getPersonal());
            mimeMessage.setFrom(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address[] getSender() {
        try {
            InternetAddress address = (InternetAddress) mimeMessage.getSender();
            Address[] send = new Address[1];
            send[0] = new Address();
            send[0].setAddress(address.getAddress());
            send[0].setPersonal(address.getPersonal());
            return send;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setSender(Address[] addr) {
        try {
            InternetAddress address = new InternetAddress();
            address.setAddress(addr[0].getAddress());
            address.setPersonal(addr[0].getPersonal());
            mimeMessage.setSender(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Address[] getMailAddress(String type) {
        try {
            InternetAddress[] address = null;
            if (type.equals("TO")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.TO);
            } else if (type.equals("CC")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.CC);
            } else if (type.equals("BCC")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(Message.RecipientType.BCC);
            } else if (type.equals("NEWSGROUPS")) {
                address = (InternetAddress[]) mimeMessage
                        .getRecipients(MimeMessage.RecipientType.NEWSGROUPS);
            }
            Address[] addr = null;
            if (address != null) {
                addr = new Address[address.length];
                for (int i = 0; i < address.length; i++) {
                    addr[i] = new Address();
                    addr[i].setAddress(address[i].getAddress());
                    addr[i].setPersonal(address[i].getPersonal());
                }
            }
            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Address[] getTo() {
        return getMailAddress("TO");
    }

    private void setMailAddress(String type, Address[] addr) {
        try {
            InternetAddress address[] = new InternetAddress[addr.length];
            for (int i = 0; i < addr.length; i++) {
                address[i].setAddress(addr[i].getAddress());
                address[i].setPersonal(addr[i].getPersonal());
            }
            if (type.equals("TO")) {
                mimeMessage.setRecipients(Message.RecipientType.TO, address);
            } else if (type.equals("CC")) {
                mimeMessage.setRecipients(Message.RecipientType.CC, address);
            } else if (type.equals("BCC")) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, address);
            } else if (type.equals("NEWSGROUPS")) {
                mimeMessage.setRecipients(MimeMessage.RecipientType.NEWSGROUPS,
                        address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTo(Address[] addr) {
        setMailAddress("TO", addr);
    }

    @Override
    public Address[] getCc() {
        return getMailAddress("CC");
    }

    @Override
    public void setCc(Address[] addr) {
        setMailAddress("CC", addr);
    }

    @Override
    public Address[] getBcc() {
        return getMailAddress("BCC");
    }

    @Override
    public void setBcc(Address[] addr) {
        setMailAddress("BCC", addr);
    }

    @Override
    public Address[] getNewsgroups() {
        return getMailAddress("NEWSGROUPS");
    }

    @Override
    public void setNewsgroups(Address[] addr) {
        setMailAddress("NEWSGROUPS", addr);
    }

    @Override
    public Address[] getReplyTo() {
        try {
            InternetAddress address[] = (InternetAddress[]) mimeMessage
                    .getReplyTo();
            Address[] reply = new Address[address.length];
            for (int i = 0; i < address.length; i++) {
                reply[i] = new Address();
                reply[i].setAddress(address[i].getAddress());
                reply[i].setPersonal(address[i].getPersonal());
            }
            return reply;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setReplyTo(Address[] addr) {
        try {
            InternetAddress[] address = new InternetAddress[addr.length];
            for (int i = 0; i < addr.length; i++) {
                address[i].setAddress(addr[i].getAddress());
                address[i].setPersonal(addr[i].getPersonal());
            }
            mimeMessage.setReplyTo(address);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getSubject() {
        try {
            String subject = mimeMessage.getSubject();
            return subject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setSubject(String subj) {
        try {
            mimeMessage.setSubject(subj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Date getSentDate() {
        try {
            Date senddate = mimeMessage.getSentDate();
            return senddate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setSentDate(Date d) {
        try {
            mimeMessage.setSentDate(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Date getReceivedDate() {
        try {
            Date senddate = mimeMessage.getReceivedDate();
            return senddate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getSize() {
        try {
            int size = mimeMessage.getSize();
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getLineCount() {
        try {
            int count = mimeMessage.getLineCount();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getMessageID() {
        try {
            String id = mimeMessage.getMessageID();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getBodyPlain() {
        try {
            Part part = mimeMessage;
            if (part.isMimeType("text/plain")) {
                String body = (String) part.getContent();
                return body;
            } else if (part.isMimeType("multipart/*"))// 复合部分，分别递归
            {
                Multipart multipart = (Multipart) part.getContent();
                int counts = multipart.getCount();
                for (int i = 0; i < counts; i++) {
                    if (multipart.getBodyPart(i).isMimeType("text/plain")) {
                        String body = (String) multipart.getBodyPart(i).getContent();
                        return body;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public void setBodyPlain(String body) {
        try {
            mimeMessage.setText(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBodyHtml() {
        try {
            Part part = mimeMessage;
            if (part.isMimeType("text/html")) {
                String body = (String) part.getContent();
                return body;
            } else if (part.isMimeType("multipart/*"))// 复合部分，分别递归
            {
                Multipart multipart = (Multipart) part.getContent();
                int counts = multipart.getCount();
                for (int i = 0; i < counts; i++) {
                    if (multipart.getBodyPart(i).isMimeType("text/html")) {
                        String body = (String) multipart.getBodyPart(i).getContent();
                        return body;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public void setBodyHtml(String body) {
        try {
            mimeMessage.setText(body, null, "html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAttachment() {
        try {
            Part part = mimeMessage;
            String fileName = "";
            if (part.isMimeType("multipart/*")) {
                Multipart mp = (Multipart) part.getContent();
                for (int j = 0; j < mp.getCount(); j++) {
                    BodyPart mpart = mp.getBodyPart(j);
                    String disposition = mpart.getDescription();
                    if ((disposition != null)
                            && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                            .equals(Part.INLINE)))) {
                        String temp = MimeUtility.decodeText(mpart.getFileName());
                        fileName += temp + "\n";
                    } else if (mpart.isMimeType("multipart/*")) {
                        //fileName += mpart.getFileName() + "\n";
                    } else {
                        if (mpart.getFileName() != null) {
                            String temp = MimeUtility.decodeText(mpart.getFileName());
                            fileName += temp + "\n";
                        }
                    }
                }
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setAttachment(String attach) {
        try {
            String[] filename = attach.split("\n");
            Multipart mp = new MimeMultipart();
            for (int i = 0; i < filename.length; i++) {
                MimeBodyPart mbp = new MimeBodyPart();
                mbp.setFileName(filename[i]);
                mp.addBodyPart(mbp);
            }
            mimeMessage.setContent(mp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSignature() {
        String body = this.getBodyPlain();
        if (body != null) {
            String[] part = body.split("--");
            if (part.length == 1) {
                part = body.split("==");
            }
            if (part.length == 1) {
                part = body.split("~~");
            }
            if (part.length == 1) {
                part = body.split("\n\n");
            }
            return part[part.length - 1].replaceAll(" \n", "");
        } else {
            return null;
        }
    }
}
