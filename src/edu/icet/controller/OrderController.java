package edu.icet.controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import edu.icet.db.DBConnection;
import edu.icet.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.awt.SubRegionShowable;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public Pane rootPane;

    public TextField txtOrderDate;
    public TextField txtDes;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtQty;
    public ComboBox cmbCustID;
    public ComboBox cmbCode;
    public Label txtOrderID;
    public Label txtCustName;
    public TableView tblOrderDetail;
    public TableColumn colCode;
    public TableColumn colDes;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTot;
    public Label txtTot;
    ObservableList<ShoppingCart> shoppingList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDate();
        loadAllCustomerIds();
        setOrderID();
        loadAllItemCodes();
    }
    public void loadDate() {
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
    public void loadAllCustomerIds() {
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
    public void loadAllItemCodes(){
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            for (String orderID : ItemController.getAllItems()) {
            list.add(orderID);
            }
            cmbCode.setItems(list);
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
    public void customerIdAction(ActionEvent actionEvent) {
        String id = (String) cmbCustID.getValue();
        System.out.println(id);
        String SQL = "Select * from Customer where id = '"+id+"'";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet rst = connection.createStatement().executeQuery(SQL);
            if(rst.next()){
                Customer customer = new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4));
                txtCustName.setText(customer.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void itemCodeAction(ActionEvent actionEvent) {
        String code = (String) cmbCode.getValue();
        System.out.println(code);
        String SQL = "Select * from Item where code='"+code+"'";
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if(rst.next()){
                Item item = new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getString(4));
                txtDes.setText(item.getDes());
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                txtQtyOnHand.setText(item.getQtyOnHand());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void addBtnAction(ActionEvent actionEvent) {
        String code = (String) cmbCode.getValue();
        String description = txtDes.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double qty = Double.parseDouble(txtQty.getText());
        double total = calculateTotal(qty,unitPrice);
        ShoppingCart shoppingCart = new ShoppingCart(code,description,unitPrice,qty,total);

        int row = isAlreadyExist(shoppingCart);
        if(row == -1){
            shoppingList.add(shoppingCart);
            System.out.println(row);
        }else{
            ShoppingCart temp = shoppingList.get(row);
            ShoppingCart newCart = new ShoppingCart(temp.getCode(),temp.getDescription(),temp.getUnitPrice(), temp.getQty() + qty, temp.getTotal() + total);
            shoppingList.remove(row);
            shoppingList.add(row,newCart);
        }
        tblOrderDetail.setItems(shoppingList);
        setCellValueFactory();
        double sub = calculateSubTotal(shoppingList);
        txtTot.setText(String.valueOf(sub) + "/=");
        txtQty.clear();
    }

    public double calculateSubTotal(ObservableList<ShoppingCart> shoppingList){
        double subtotal = 0;
        for(int i=0; i<shoppingList.size(); i++){
            subtotal += shoppingList.get(i).getTotal();
        }
        return subtotal;
    }
    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private int isAlreadyExist(ShoppingCart shoppingCart) {
        for(int i=0; i<shoppingList.size(); i++){
            if(shoppingCart.getCode().equals(shoppingList.get(i).getCode())){
                return i;
            }
        }
        return -1;
    }

    private double calculateTotal(double qty, double unitPrice) {
        qty = Double.parseDouble(txtQty.getText());
        unitPrice = Double.parseDouble(txtUnitPrice.getText());
        return qty*unitPrice;
    }

    public void btnPlaceOrderAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String orderID = txtOrderID.getText();
        String date = txtOrderDate.getText();
        String custID = (String) cmbCustID.getValue();
        ObservableList<OrderDetails> list = FXCollections.observableArrayList();

        for(int i=0; i<shoppingList.size(); i++){
            String code = shoppingList.get(i).getCode();
            double qty = shoppingList.get(i).getQty();
            double unitPrice = shoppingList.get(i).getUnitPrice();
            OrderDetails orderDetails = new OrderDetails(orderID,code,qty,unitPrice);
            list.add(orderDetails);
        }
        Order order = new Order(orderID,date,custID,list);

        boolean isAdded = placeOrder(order);
        if(isAdded){
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Order Success!").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Order Fail!").show();
        }
    }

    private boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("Insert into Orders values(?,?,?)");
            stm.setObject(1,order.getId());
            stm.setObject(2,order.getDate());
            stm.setObject(3,order.getCustomerID());
            boolean orderIsAdded = stm.executeUpdate()>0;
            if(orderIsAdded){
                boolean orderDetailAdded = OrderDetailController.addOrderDetail(order.getList());
                if(orderDetailAdded){
                    boolean itemUpdated = ItemController.updateStock(order.getList());
                    if(itemUpdated){
                        connection.commit();
                        System.out.println("Updated Success!!");
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void addNewCustomerAction(ActionEvent actionEvent) {
        URL resource = this.getClass().getResource("/edu/icet/view/customer-form.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItemBtnAction(ActionEvent actionEvent) {
        URL resource = this.getClass().getResource("/edu/icet/view/item-form.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

