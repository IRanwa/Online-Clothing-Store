package com.example.imeshranawaka.styleomega.Models;

public class Address {
    private String name;
    private String address;
    private boolean def;

    public Address(String name, String address, boolean def) {
        this.name = name;
        this.address = address;
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isDef() {
        return def;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDef(boolean def) {
        this.def = def;
    }
}
