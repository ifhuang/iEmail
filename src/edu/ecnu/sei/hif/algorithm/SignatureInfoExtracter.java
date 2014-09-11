/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.algorithm;

import edu.ecnu.sei.hif.model.Contact;
import edu.ecnu.sei.hif.model.Email;

/**
 *
 * @author If
 */
public class SignatureInfoExtracter {
    
    private final String pattern_name = "姓名";
    private final String pattern_address = "Email";
    private final String pattern_phone = "Tel";
    private final String pattern_tax = "Tax";
    private final String pattern_company = "Inc";
    private final String pattern_title = "长";
    private final String pattern_realaddress = "路";
    private final String pattern_site = "www.";
    private final String pattern_image = ".jpg";
    private Email email;
    
    public SignatureInfoExtracter(Email email) {
        this.email = email;
    }
    
    public Contact extract() {
        Contact contact = new Contact();
        String signature = email.getSignature();
        if (signature != null) {
            String[] part = signature.split("\t\n ");
            for (int i = 0; i < part.length; i++) {
                if (part[i].contains(pattern_name)) {
                    contact.addPersonal(part[i]);
                } else if (part[i].contains(pattern_address)) {
                    contact.addAddress(part[i]);
                } else if (part[i].contains(pattern_phone)) {
                    contact.addPhone(part[i]);
                } else if (part[i].contains(pattern_tax)) {
                    contact.addTax(part[i]);
                } else if (part[i].contains(pattern_company)) {
                    contact.addCompany(part[i]);
                } else if (part[i].contains(pattern_title)) {
                    contact.addTitle(part[i]);
                } else if (part[i].contains(pattern_realaddress)) {
                    contact.addRealaddress(part[i]);
                } else if (part[i].contains(pattern_site)) {
                    contact.addSite(part[i]);
                } else if (part[i].contains(pattern_image)) {
                    contact.addImageurl(part[i]);
                }
            }
        }
        return contact;
    }
}
