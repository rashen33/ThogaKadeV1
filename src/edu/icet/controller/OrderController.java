package edu.icet.controller;

import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public TextField txtOrderDate;
    public TextField txtDes;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public ComboBox cmbCustID;
    public ComboBox cmbCode;
    public Label txtOrderID;
    public Label txtCustName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDate();
        loadAllCustomerIds();
        setOrderID();
    }

    private void loadDate() {
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
    private void loadAllCustomerIds() {
        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            for (String tempId : CustomerController.getAllCustomerIds()) {
                list.add(tempId);
            }
            cmbCustID.setItems(list);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getLastOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1"); //gets the highest order id from the order list.
            return rst.next() ? rst.getString("id") : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void setOrderID(){
        String lastOrderId = getLastOrderId();
        if(lastOrderId != null){
            lastOrderId = lastOrderId.split("[A-Z]")[1]; // D001==> 001
            System.out.println(lastOrderId);
            lastOrderId = String.format("D%03d", (Integer.parseInt(lastOrderId) + 1));
            txtOrderID.setText(lastOrderId);
        }else{
            txtOrderID.setText("D001");
        }
    }

}
