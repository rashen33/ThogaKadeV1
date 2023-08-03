package edu.icet.controller;

import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
