package edu.icet.controller;

import com.mysql.cj.protocol.Resultset;
import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public TextField txtName;
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSalary;
    @FXML
    private TableView tblCustomer;

    @FXML
    private TableColumn colID;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colSalary;

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
            if(i>0){
                loadTable();
                clearTxtFields();
            }
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
                loadTable();
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
            if(i>0){
                loadTable();
                clearTxtFields();
            }
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
            if(i>0){
                loadTable();
                clearTxtFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearBtnAction(ActionEvent actionEvent) {
        clearTxtFields();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            if(null != newValue){
                setTextFromTable((Customer) newValue);
            }
        });
    }
    public void loadTable(){
        //This loads once you run the programme
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        String SQL = "Select * From Customer";
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(SQL);
            while(rst.next()){
                Customer customer = new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4));
                list.add(customer);
            }
            tblCustomer.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTextFromTable(Customer customer){
        txtID.setText(customer.getId());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtSalary.setText(String.valueOf(customer.getSalary()));
    }
    public void clearTxtFields(){
        txtID.setText("");
        txtName.setText("");
        txtSalary.setText("");
        txtAddress.setText("");
    }
    public static ArrayList<String> getAllCustomerIds() throws ClassNotFoundException, SQLException{

        ResultSet rst  = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT id FROM Customer")
                .executeQuery();
        ArrayList<String> idSet= new ArrayList<>();
        System.out.println();
        while (rst.next()) {
            idSet.add(rst.getString(1));
        }
        return idSet;
    }

}
