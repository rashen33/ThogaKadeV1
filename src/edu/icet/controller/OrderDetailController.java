package edu.icet.controller;

import edu.icet.db.DBConnection;
import edu.icet.model.OrderDetails;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailController {
    public static boolean addOrderDetail(ObservableList<OrderDetails> list) throws ClassNotFoundException, SQLException{
        for (OrderDetails orderDetails : list) {
            boolean isAdded=addOrderDetail(orderDetails);
            if(!isAdded){
                return false;
            }
        }
        return true;
    }
    public static boolean addOrderDetail(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("Insert into OrderDetail values(?,?,?,?)");
        pst.setObject(1,orderDetails.getOrderID());
        pst.setObject(2,orderDetails.getItemCode());
        pst.setObject(3,orderDetails.getQty());
        pst.setObject(4,orderDetails.getUnitPrice());
        return pst.executeUpdate()>0;
    }
}
