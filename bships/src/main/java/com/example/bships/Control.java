package com.example.bships;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Control {
    @FXML
    void initialize(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("set.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void rules(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("rule.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goback(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
