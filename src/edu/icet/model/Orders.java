package edu.icet.model;

import java.util.ArrayList;

public class Orders {
    private String id;
    private String order;
    private String date;
    private String customerID;
    private ArrayList<OrderDetails>orderDetailsList;

    public Orders(String id, String order, String date, String customerID, ArrayList<OrderDetails> orderDetailsList) {
        this.id = id;
        this.order = order;
        this.date = date;
        this.customerID = customerID;
        this.orderDetailsList = orderDetailsList;
    }

    public Orders() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    public ArrayList<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(ArrayList<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", order='" + order + '\'' +
                ", date='" + date + '\'' +
                ", customerID='" + customerID + '\'' +
                ", orderDetailsList=" + orderDetailsList +
                '}';
    }
}
