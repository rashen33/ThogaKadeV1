package edu.icet.controller;

import edu.icet.db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    private AnchorPane sideAncPane;

    @FXML
    private Button btnAddCust;

    @FXML
    private Button btnAddCust1;

    @FXML
    private Button btnAddCust11;

    @FXML
    private Button btnAddCust111;

    @FXML
    private AnchorPane mainAncorPane;

    @FXML
    private Label txtCustNo;

    @FXML
    private Label txtItemNo;

    @FXML
    private Label txtOrderNo;

    @FXML
    private Label txtGreeting;

    @FXML
    private Label txtTime;

    @FXML
    private Label txtDate;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadGreeting();
        loadDate();
        loadTime();
        setOrderID();
        setCustomerID();
        setItem();
    }

    private void loadGreeting() {
        LocalTime cTime = LocalTime.now();
        if(cTime.getHour() > 5 && cTime.getHour() < 12 ){
            txtGreeting.setText("Good Morning!");
        } else if (cTime.getHour() >= 12 && cTime.getHour() < 5) {
            txtGreeting.setText("Good Afternoon!");
        } else {
            txtGreeting.setText("Good Evening!");
        }
    }

    private void loadTime() {
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            txtTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(01))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void loadDate() {
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
    public void setOrderID(){
        String lastOrderId = getLastOrderId();
        lastOrderId = lastOrderId.split("[A-Z]")[1]; // D001==> 001
        lastOrderId = String.format("%02d", (Integer.parseInt(lastOrderId)));
        txtOrderNo.setText(lastOrderId);
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
    public String getLastCustomerId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1"); //gets the highest order id from the order list.
            return rst.next() ? rst.getString("id") : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCustomerID(){
        String lastCustomerId = getLastCustomerId();
        lastCustomerId = lastCustomerId.split("[A-Z]")[1]; // D001==> 001
        lastCustomerId = String.format("%02d", (Integer.parseInt(lastCustomerId)));
        txtCustNo.setText(lastCustomerId);
    }

    public String getLastItem() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1"); //gets the highest order id from the order list.
            return rst.next() ? rst.getString("code") : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setItem(){
        String item = getLastItem();
        item = item.split("[A-Z]")[1]; // D001==> 001
        item = String.format("%02d", (Integer.parseInt(item)));
        txtItemNo.setText(item);
    }

}

