/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.model;


/**
 *
 * @author 一夫
 */
public class Record {

    private String address1;
    private String address2;
    private int num;
    private DateSubject[] datesubject;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public DateSubject[] getDatesubject() {
        return datesubject;
    }

    public void setDatesubject(DateSubject[] datesubject) {
        this.datesubject = datesubject;
    }

    public String getDatesubjectString() {
        String result = "";
        for (int i = 0; i < datesubject.length; i++) {
            result +=  datesubject[i].getDate() + "\t" + datesubject[i].getSubject() + "\n";
        }
        return result;
    }
}
