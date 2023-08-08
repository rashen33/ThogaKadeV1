package edu.icet.controller;

import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import edu.icet.model.Item;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.*;

public class ItemController {
    public TextField txtCode;
    public TextField txtDes;
    public TextField txtUprice;
    public TextField txtQtyOnhand;
    public void addItemBtnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDes.getText();
        double unitPrice = Double.parseDouble(txtUprice.getText());
        String qtyOnHand = txtQtyOnhand.getText();
        Item item = new Item(code,description,unitPrice,qtyOnHand);
        System.out.println(item);
        String SQL = "Insert into Item values(?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL);
            pst.setObject(1,item.getCode());
            pst.setObject(2,item.getDes());
            pst.setObject(3,item.getUnitPrice());
            pst.setObject(4,item.getQtyOnHand());
            int i = pst.executeUpdate();
            if(i>0){
                System.out.println("Item added");
                JOptionPane.showMessageDialog(null, "Item Added!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Item not added!", "Alert", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Item not added!", "Alert", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    public void searchItemBtnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String SQL = "Select * from Item where code = '"+code+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement pst = connection.createStatement();
            ResultSet rst = pst.executeQuery(SQL);
            if(rst.next()){
                Item item = new Item(rst.getString("code"),rst.getString("description"),rst.getDouble("unitprice"),rst.getString("qtyonhand"));
                txtCode.setText(item.getCode());
                txtDes.setText(item.getDes());
                txtUprice.setText(String.valueOf(item.getUnitPrice()));
                txtQtyOnhand.setText(item.getQtyOnHand());
            }

        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void updateItemBtnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDes.getText();
        double unitPrice = Double.parseDouble(txtUprice.getText());
        String qtyOnHand = txtQtyOnhand.getText();
        Item item = new Item(code,description,unitPrice,qtyOnHand);
        String SQL = "Update Item set description=?,  unitPrice=?, qtyOnHand=? where code=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL);
            pst.setObject(1,item.getDes());
            pst.setObject(2,item.getUnitPrice());
            pst.setObject(3,item.getQtyOnHand());
            pst.setObject(4,item.getCode());
            int i = pst.executeUpdate();
            System.out.println(i>0 ? "Updated":"Failed");
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void deleteItemBtnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String SQL = "Delete From Item where code='"+code+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL);
            int i = pst.executeUpdate();
            System.out.println(i>0 ? "Deleted":"Not Deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
