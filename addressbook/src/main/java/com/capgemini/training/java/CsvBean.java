package com.capgemini.training.java;

import com.opencsv.bean.CsvBindByName;

public class CsvBean {
    @CsvBindByName(column = "fname", required = true)
    private String fName;
    @CsvBindByName(column = "lname", required = true)
    private String lName;
    @CsvBindByName(column = "address", required = true)
    private String address;
    @CsvBindByName(column = "city", required = true)
    private String city;
    @CsvBindByName(column = "state", required = true)
    private String state;
    @CsvBindByName(column = "zip", required = true)
    private String zip;
    @CsvBindByName(column = "phone", required = true)
    private String phone;
    @CsvBindByName(column = "email", required = true)
    private String email;

    @Override
    public String toString() {
        return "CsvBean [address=" + address + ", city=" + city + ", email=" + email + ", fName=" + fName + ", lName="
                + lName + ", phone=" + phone + ", state=" + state + ", zip=" + zip + "]";
    }

}
