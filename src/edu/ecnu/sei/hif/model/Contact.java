/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.model;

import java.util.ArrayList;

/**
 *
 * @author If
 */
public class Contact {

    private ArrayList<String> personal;
    private ArrayList<String> address;
    private ArrayList<String> phone;
    private ArrayList<String> tax;
    private ArrayList<String> company;
    private ArrayList<String> title;
    private ArrayList<String> realaddress;
    private ArrayList<String> site;
    private ArrayList<String> imageurl;
    private ArrayList<String> birthday;
    private ArrayList<String> id;

    public Contact() {
        this.personal = new ArrayList<>();
        this.address = new ArrayList<>();
        this.phone = new ArrayList<>();
        this.tax = new ArrayList<>();
        this.company = new ArrayList<>();
        this.title = new ArrayList<>();
        this.realaddress = new ArrayList<>();
        this.site = new ArrayList<>();
        this.imageurl = new ArrayList<>();
        this.birthday = new ArrayList<>();
        this.id = new ArrayList<>();
    }

    public ArrayList<String> getPersonal() {
        return personal;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public ArrayList<String> getTax() {
        return tax;
    }

    public ArrayList<String> getCompany() {
        return company;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public ArrayList<String> getRealaddress() {
        return realaddress;
    }

    public ArrayList<String> getSite() {
        return site;
    }

    public ArrayList<String> getImageurl() {
        return imageurl;
    }

    public ArrayList<String> getBirthday() {
        return birthday;
    }

    public ArrayList<String> getId() {
        return id;
    }

    public void setPersonal(ArrayList<String> personal) {
        this.personal = personal;
    }

    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }

    public void setTax(ArrayList<String> tax) {
        this.tax = tax;
    }

    public void setCompany(ArrayList<String> company) {
        this.company = company;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public void setRealaddress(ArrayList<String> realaddress) {
        this.realaddress = realaddress;
    }

    public void setSite(ArrayList<String> site) {
        this.site = site;
    }

    public void setImageurl(ArrayList<String> imageurl) {
        this.imageurl = imageurl;
    }

    public void setBirthday(ArrayList<String> birthday) {
        this.birthday = birthday;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }

    public void addPersonal(String personal) {
        this.personal.add(personal);
    }

    public void addAddress(String address) {
        this.address.add(address);
    }

    public void addPhone(String phone) {
        this.phone.add(phone);
    }

    public void addTax(String tax) {
        this.tax.add(tax);
    }

    public void addCompany(String company) {
        this.company.add(company);
    }

    public void addTitle(String title) {
        this.title.add(title);
    }

    public void addRealaddress(String realaddress) {
        this.realaddress.add(realaddress);
    }

    public void addSite(String site) {
        this.site.add(site);
    }

    public void addImageurl(String imageurl) {
        this.imageurl.add(imageurl);
    }

    public void addBirthday(String birthday) {
        this.birthday.add(birthday);
    }

    public void addId(String id) {
        this.id.add(id);
    }

    public Contact merge(Contact other) {
        Contact all = new Contact();

        for (int i = 0; i < this.personal.size(); i++) {
            all.addPersonal(this.personal.get(i));
        }
        for (int i = 0; i < other.personal.size(); i++) {
            if (all.getPersonal().contains(other.personal.get(i))) {
                continue;
            } else {
                all.addPersonal(other.personal.get(i));
            }
        }

        for (int i = 0; i < this.address.size(); i++) {
            all.addAddress(this.address.get(i));
        }
        for (int i = 0; i < other.address.size(); i++) {
            if (all.getAddress().contains(other.address.get(i))) {
                continue;
            } else {
                all.addAddress(other.address.get(i));
            }
        }

        for (int i = 0; i < this.phone.size(); i++) {
            all.addPhone(this.phone.get(i));
        }
        for (int i = 0; i < other.phone.size(); i++) {
            if (all.getPhone().contains(other.phone.get(i))) {
                continue;
            } else {
                all.addPhone(other.phone.get(i));
            }
        }

        for (int i = 0; i < this.tax.size(); i++) {
            all.addTax(this.tax.get(i));
        }
        for (int i = 0; i < other.tax.size(); i++) {
            if (all.getTax().contains(other.tax.get(i))) {
                continue;
            } else {
                all.addTax(other.tax.get(i));
            }
        }

        for (int i = 0; i < this.company.size(); i++) {
            all.addCompany(this.company.get(i));
        }
        for (int i = 0; i < other.company.size(); i++) {
            if (all.getCompany().contains(other.company.get(i))) {
                continue;
            } else {
                all.addCompany(other.company.get(i));
            }
        }

        for (int i = 0; i < this.title.size(); i++) {
            all.addTitle(this.title.get(i));
        }
        for (int i = 0; i < other.title.size(); i++) {
            if (all.getTitle().contains(other.title.get(i))) {
                continue;
            } else {
                all.addTitle(other.title.get(i));
            }
        }

        for (int i = 0; i < this.realaddress.size(); i++) {
            all.addRealaddress(this.realaddress.get(i));
        }
        for (int i = 0; i < other.realaddress.size(); i++) {
            if (all.getRealaddress().contains(other.realaddress.get(i))) {
                continue;
            } else {
                all.addRealaddress(other.realaddress.get(i));
            }
        }

        for (int i = 0; i < this.site.size(); i++) {
            all.addSite(this.site.get(i));
        }
        for (int i = 0; i < other.site.size(); i++) {
            if (all.getSite().contains(other.site.get(i))) {
                continue;
            } else {
                all.addSite(other.site.get(i));
            }
        }

        for (int i = 0; i < this.imageurl.size(); i++) {
            all.addImageurl(this.imageurl.get(i));
        }
        for (int i = 0; i < other.imageurl.size(); i++) {
            if (all.getImageurl().contains(other.imageurl.get(i))) {
                continue;
            } else {
                all.addImageurl(other.imageurl.get(i));
            }
        }

        for (int i = 0; i < this.birthday.size(); i++) {
            all.addBirthday(this.birthday.get(i));
        }
        for (int i = 0; i < other.birthday.size(); i++) {
            if (all.getBirthday().contains(other.birthday.get(i))) {
                continue;
            } else {
                all.addBirthday(other.birthday.get(i));
            }
        }

        for (int i = 0; i < this.id.size(); i++) {
            all.addId(this.id.get(i));
        }
        for (int i = 0; i < other.id.size(); i++) {
            if (all.getId().contains(other.id.get(i))) {
                continue;
            } else {
                all.addId(other.id.get(i));
            }
        }

        return all;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append("personal\t");
        for (int i = 0; i < personal.size(); i++) {
            sb.append(personal.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("address\t");
        for (int i = 0; i < address.size(); i++) {
            sb.append(address.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("phone\t");
        for (int i = 0; i < phone.size(); i++) {
            sb.append(phone.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("tax\t");
        for (int i = 0; i < tax.size(); i++) {
            sb.append(tax.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("company\t");
        for (int i = 0; i < company.size(); i++) {
            sb.append(company.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("title\t");
        for (int i = 0; i < title.size(); i++) {
            sb.append(title.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("realaddress\t");
        for (int i = 0; i < realaddress.size(); i++) {
            sb.append(realaddress.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("site\t");
        for (int i = 0; i < site.size(); i++) {
            sb.append(site.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("imageurl\t");
        for (int i = 0; i < imageurl.size(); i++) {
            sb.append(imageurl.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("birthday\t");
        for (int i = 0; i < birthday.size(); i++) {
            sb.append(birthday.get(i)).append(";");
        }
        sb.append("\t");
        //sb.append("id\t");
        for (int i = 0; i < id.size(); i++) {
            sb.append(id.get(i)).append(";");
        }
        //sb.append("\n");

        return sb.toString();
    }
}
