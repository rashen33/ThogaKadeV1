package edu.icet.controller;

import edu.icet.db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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

    public Pane rootPane;
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
    public AnchorPane anpRoot;
    public AnchorPane anpDashBoard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDashBoard();
    }

    public void loadDashBoard() {
        URL resource = this.getClass().getResource("/edu/icet/view/dash-board-root.fxml");
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

    public void customerButton(ActionEvent actionEvent) {
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

    public void itemButton(ActionEvent actionEvent) {
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

    public void orderButton(ActionEvent actionEvent) {
        URL resource = this.getClass().getResource("/edu/icet/view/order-form.fxml");
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

    public void dashBoardBtn(ActionEvent actionEvent) {
        URL resource = this.getClass().getResource("/edu/icet/view/dash-board-root.fxml");
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

    public void exitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}

