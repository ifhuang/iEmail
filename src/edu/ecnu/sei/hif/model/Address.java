package edu.ecnu.sei.hif.model;

public class Address {

    private String personal;
    private String address;

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        if (personal != null) {
            sb.append(personal);
        }
        if (address != null) {
            sb.append("<").append(address).append(">");
        }
        return sb.toString();
    }
}
