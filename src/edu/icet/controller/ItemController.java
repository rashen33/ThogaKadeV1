package edu.icet.controller;

import edu.icet.db.DBConnection;
import edu.icet.model.Customer;
import edu.icet.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ItemController implements Initializable{
    public TextField txtCode;
    public TextField txtDes;
    public TextField txtUprice;
    public TextField txtQtyOnhand;
    public TableView tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;

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
                loadTable();
                clearTxtFields();
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
            if(i>0){
                loadTable();
                clearTxtFields();
            }
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
            if(i>0){
                loadTable();
                clearTxtFields();
            }
            System.out.println(i>0 ? "Deleted":"Not Deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            if(null != newValue){
                setTextFromTable((Item)newValue);
            }
        });
    }

    private void setTextFromTable(Item item) {
        txtCode.setText(item.getCode());
        txtDes.setText(item.getDes());
        txtUprice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnhand.setText(item.getQtyOnHand());
    }

    public void clearTxtFields(){
        txtCode.setText("");
        txtDes.setText("");
        txtQtyOnhand.setText("");
        txtUprice.setText("");
    }
    public void loadTable(){
        colCode.setCellValueFactory(new PropertyValueFactory<Item, String>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<Item, Integer>("qtyOnHand"));
        String SQL = "Select * From Item";
        ObservableList<Item> list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet rst = pstm.executeQuery();

            while (rst.next()) {
                Item item = new Item(rst.getString("code"), rst.getString("description"), rst.getDouble("unitPrice"), rst.getString("qtyOnHand"));
                list.add(item);
            }
            tblItem.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> getAllItems() throws ClassNotFoundException, SQLException{

        ResultSet rst  = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT code FROM Item")
                .executeQuery();
        ArrayList<String> codeSet= new ArrayList<>();
        System.out.println();
        while (rst.next()) {
            codeSet.add(rst.getString(1));
        }
        return codeSet;
    }
}
