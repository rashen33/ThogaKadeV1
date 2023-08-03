package edu.icet.controller;

import com.mysql.cj.protocol.Resultset;
import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CustomerController {

    public TextField txtName;
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSalary;


    public void addBtnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        Customer customer = new Customer(id,name,address,salary);
        System.out.println(customer);

        String SQL = "Insert into Customer Values(?,?,?,?)";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL);
            pst.setObject(1,customer.getId());
            pst.setObject(2,customer.getName());
            pst.setObject(3,customer.getAddress());
            pst.setObject(4,customer.getSalary());
            int i = pst.executeUpdate();
            System.out.println(i > 0 ? "Added" : "Failed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void searchBtnAction(ActionEvent actionEvent) {
        String id = txtID.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement pst = connection.createStatement();
            String SQL = "Select * from Customer where id = '"+id+"'";
            ResultSet rst = pst.executeQuery(SQL);
            if(rst.next()){
                Customer customer = new Customer(rst.getString("id"),rst.getString("name"), rst.getString("address"), rst.getDouble("salary") );
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtSalary.setText(String.valueOf(customer.getSalary()));
                System.out.println(id);
            }else{
                System.out.println("Customer not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBtnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        Customer customer = new Customer(id,name,address,salary);
        String SQL = "Update Customer set name=?, address=?, salary=? where id=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement ppst = connection.prepareStatement(SQL);
            ppst.setObject(1,customer.getName());
            ppst.setObject(2,customer.getAddress());
            ppst.setObject(3,customer.getSalary());
            ppst.setObject(4,customer.getId());
            int i = ppst.executeUpdate();
            System.out.println(i>0 ? "Updated":"Update Fail");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBtnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String SQL = "Delete From Customer where id='"+id+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(SQL);
            int i = pst.executeUpdate();
            System.out.println(i>0 ? "Deleted" : "Delete Failed");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
