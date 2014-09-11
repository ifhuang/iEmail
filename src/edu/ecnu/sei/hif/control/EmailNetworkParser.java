/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.control;

import edu.ecnu.sei.hif.model.Address;
import edu.ecnu.sei.hif.model.DateSubject;
import edu.ecnu.sei.hif.model.Email;
import edu.ecnu.sei.hif.model.EmailImpl;
import edu.ecnu.sei.hif.model.Record;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author 一夫
 */
public class EmailNetworkParser {

    private String rootDir;
    private Set<String> V;
    private Map<String, Integer> mape;
    private Map<String, String> mapds;
    private Map<String, String> mapp;

    public EmailNetworkParser(String rootDir) {
        this.rootDir = rootDir;
        this.V = new HashSet<String>();
        this.mape = new HashMap<String, Integer>();
        this.mapds = new HashMap<String, String>();
        this.mapp = new HashMap<String, String>();
    }

    private void parseSubRoot(File subroot) {
        File[] files = subroot.listFiles();

        for (int i = 0; i < files.length; i++) {
            Email pmm = new EmailImpl(files[i].getAbsolutePath());
            System.out.println("Now Parse " + files[i].getAbsolutePath()
                    + " ...");

            Address[] from = pmm.getFrom();
            Address[] to = pmm.getTo();
            Address[] cc = pmm.getCc();
            Date date = pmm.getSentDate();
            String subject = pmm.getSubject();

            if (from != null) {
                String fromaddr = from[0].getAddress();
                if (V.add(fromaddr)) {
                    String personal = from[0].getPersonal();
                    if (personal != null && !personal.equals("")) {
                        mapp.put(fromaddr, personal);
                    }
                }
            }
            if (to != null) {
                for (int j = 0; j < to.length; j++) {
                    String toaddr = to[j].getAddress();
                    if (V.add(toaddr)) {
                        String personal = to[j].getPersonal();
                        if (personal != null && !personal.equals("") && !personal.contains("")) {
                            mapp.put(toaddr, personal);
                        }
                    }
                }
            }
            if (cc != null) {
                for (int j = 0; j < cc.length; j++) {
                    String ccaddr = cc[j].getAddress();
                    if (V.add(ccaddr)) {
                        String personal = cc[j].getPersonal();
                        if (personal != null && !personal.equals("")) {
                            mapp.put(ccaddr, personal);
                        }
                    }
                }
            }

            if (from == null) {
                continue;
            }

            if (to != null) {
                for (int j = 0; j < to.length; j++) {
                    String tempf = from[0].getAddress();
                    String tempt = to[j].getAddress();
                    if (mape.containsKey(tempf + "\t" + tempt)) {
                        mape.put(tempf + "\t" + tempt,
                                mape.get(tempf + "\t" + tempt) + 1);
                        mapds.put(tempf + "\t" + tempt,
                                mapds.get(tempf + "\t" + tempt) + "\t" + date
                                + "\t" + subject);
                    } else {
                        mape.put(tempf + "\t" + tempt, 1);
                        mapds.put(tempf + "\t" + tempt, date + "\t" + subject);
                    }
                }
            }
            if (cc != null) {
                for (int j = 0; j < cc.length; j++) {
                    String tempf = from[0].getAddress();
                    String tempc = cc[j].getAddress();
                    if (mape.containsKey(tempf + "\t" + tempc)) {
                        mape.put(tempf + "\t" + tempc,
                                mape.get(tempf + "\t" + tempc) + 1);
                        mapds.put(tempf + "\t" + tempc,
                                mapds.get(tempf + "\t" + tempc) + "\t" + date
                                + "\t" + subject);
                    } else {
                        mape.put(tempf + "\t" + tempc, 1);
                        mapds.put(tempf + "\t" + tempc, date + "\t" + subject);
                    }
                }
            }
        }
    }

    public void parse() {
        File directory = new File(rootDir);

        if (!directory.exists()) {
            System.err.println("Invalid MIMEFileDir");
            System.exit(1);
        }

        File[] files = directory.listFiles();

        if (files.length == 0) {
            System.err.println("Empty MIMEFileDir");
            System.exit(1);
        }

        long start, end;
        System.out.println("Parse Begin");
        start = System.currentTimeMillis();

        if (files[0].isDirectory()) {
            for (int i = 0; i < files.length; i++) {
                parseSubRoot(files[i]);
            }
        } else {
            parseSubRoot(directory);
        }

        Client.addressBook = new Address[V.size()];

        int counter = 0;
        for (String each : V) {
            Client.addressBook[counter] = new Address();
            Client.addressBook[counter].setAddress(each);
            if (mapp.containsKey(each)) {
                Client.addressBook[counter].setPersonal(mapp.get(each));
            } else {
                if (each.indexOf("@") == -1) {
                    Client.addressBook[counter].setPersonal(each);
                } else {
                    Client.addressBook[counter].setPersonal(each.substring(0, each.indexOf("@")));
                }
            }
            counter++;
        }

        Client.record = new Record[mape.keySet().size()];

        counter = 0;
        for (String each : mape.keySet()) {
            String addr[] = each.split("\t");
            Client.record[counter] = new Record();
            Client.record[counter].setAddress1(addr[0]);
            Client.record[counter].setAddress2(addr[1]);
            Client.record[counter].setNum(mape.get(each));
            DateSubject[] datesubject = new DateSubject[mape.get(each)];
            String[] ds = mapds.get(each).split("\t");
            for (int j = 0; j < datesubject.length; j++) {
                datesubject[j] = new DateSubject();
                datesubject[j].setDate(new Date(ds[2 * j]));
                datesubject[j].setSubject(ds[2 * j + 1]);
            }
            Client.record[counter].setDatesubject(datesubject);
            counter++;
        }

        end = System.currentTimeMillis();
        System.out.println("Parse End");
        System.out.println("Spend:" + ((end - start) / 1000.) + "s");
    }

    /*
    public static void main(String[] args) {
        EmailNetworkParser test = new EmailNetworkParser("D:\\workspace\\netbeans\\Project\\data\\Sent Messages");
        test.parse();
        for (int i = 0; i < Client.addressBook.length; i++) {
            System.out.println(Client.addressBook[i].getAddress() + "\t" + Client.addressBook[i].getPersonal());
        }
        System.out.println("\n" + Client.addressBook.length + "\n");
        for (int i = 0; i < Client.record.length; i++) {
            System.out.println(Client.record[i].getAddress1() + "\t" + Client.record[i].getAddress2() + "\t" + Client.record[i].getNum() + "\t" + Client.record[i].getDatesubjectString());
        }
        System.out.println("\n" + Client.record.length + "\n");
    }
    */
}
