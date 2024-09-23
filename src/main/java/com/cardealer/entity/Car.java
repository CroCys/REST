package com.cardealer.entity;

import java.util.List;

public class Car {
    private int id;
    private String model;
    private Manufacturer manufacturer;
    private List<Customer> customers;

    public Car() {
    }

    public Car(int id, String model, Manufacturer manufacturer, List<Customer> customers) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.customers = customers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                ", customers=" + customers +
                '}';
    }
}
