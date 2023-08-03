package edu.icet.model;

public class Order {
    private String code;
    private String des;
    private double unitPrice;
    private String qtyOnHand;

    public Order(String code, String des, double unitPrice, String qtyOnHand) {
        this.code = code;
        this.des = des;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public Order() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Order{" +
                "code='" + code + '\'' +
                ", des='" + des + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand='" + qtyOnHand + '\'' +
                '}';
    }
}
