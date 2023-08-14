package edu.icet.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtPw;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    public void loginBtn(ActionEvent actionEvent) {
        try {
            if(txtPw.getText().equals("1234")){
                Parent root = FXMLLoader.load(getClass().getResource("../view/dash-board.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Invalid Password!").show();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
