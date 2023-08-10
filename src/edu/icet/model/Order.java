package edu.icet.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Order {
    private String id;
    private String date;
    private String customerID;
    ObservableList<OrderDetails> list;

    public Order(String id, String date, String customerID, ObservableList<OrderDetails> list) {
        this.id = id;
        this.date = date;
        this.customerID = customerID;
        this.list = list;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public ObservableList<OrderDetails> getList() {
        return list;
    }

    public void setList(ObservableList<OrderDetails> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", customerID='" + customerID + '\'' +
                ", list=" + list +
                '}';
    }
}
