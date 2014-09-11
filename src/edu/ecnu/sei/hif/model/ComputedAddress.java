/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ecnu.sei.hif.model;

/**
 *
 * @author 一夫
 */
public class ComputedAddress {
    private Address address;
    private double coefficient[];

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public double[] getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double[] coefficient) {
        this.coefficient = coefficient;
    }
    
}
