package com.example.bships;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Label playerLabel;
    @FXML
    private Label commentLabel;
    @FXML
    private Label selectedLabel;


    @FXML
    private RadioButton radioBattleship, radioDestroyer, radioSubmarine;
    @FXML
    private Button changePlayerButton;

    @FXML
    private GridPane gridPane;

    int curr_player = 1;
    private int[][][] playerShips = new int [2][9][9];
    private int[][] battleGround = new int[9][9];

    private boolean battleshipSelected = true;
    private boolean destroyerSelected = false;
    private boolean submarineSelected = false;

    private int battleshipClick = 0;
    private int destroyerClick = 0;
    private int submarineClick = 0;

    private int destroyerCount = 0;
    private int submarineCount = 0;

    private void handleButtonClick(Button button) {
        int rowIndex = GridPane.getRowIndex(button);
        int colIndex = GridPane.getColumnIndex(button);
        if(battleGround[rowIndex][colIndex] == 0) {
            if (battleshipSelected) {
                if (battleshipClick == 0) {
                    commentLabel.setText("");
                    button.setStyle("-fx-background-color: #0097a7;");
                    battleshipClick++;
                    battleGround[rowIndex][colIndex] = 1;
                }
                else if (battleshipClick == 1) {
                    if ((rowIndex > 0 && battleGround[rowIndex - 1][colIndex] == 1) || (rowIndex < 8 && battleGround[rowIndex + 1][colIndex] == 1) || (colIndex > 0 && battleGround[rowIndex][colIndex - 1] == 1) || (colIndex < 8 && battleGround[rowIndex][colIndex + 1] == 1)) {
                        commentLabel.setText("");
                        button.setStyle("-fx-background-color: #0097a7;");
                        battleshipClick++;
                        battleGround[rowIndex][colIndex] = 1;
                    }
                    else commentLabel.setText("NOT ALLOWED!");
                }
                else if (battleshipClick == 2) {
                    if (colIndex >= 2 && ((battleGround[rowIndex][colIndex - 1] == 1 && battleGround[rowIndex][colIndex - 2] == 1)) ||
                            (colIndex <= 6 && (battleGround[rowIndex][colIndex + 1] == 1 && battleGround[rowIndex][colIndex + 2] == 1))) {
                        commentLabel.setText("");
                        button.setStyle("-fx-background-color: #0097a7;");
                        battleGround[rowIndex][colIndex] = 1;
                        battleshipClick++;
                    }
                    else if (rowIndex >= 2 && ((battleGround[rowIndex - 1][colIndex] == 1 && battleGround[rowIndex - 2][colIndex] == 1)) ||
                            (rowIndex <= 6 && (battleGround[rowIndex + 1][colIndex] == 1 && battleGround[rowIndex + 2][colIndex] == 1))) {
                        commentLabel.setText("");
                        button.setStyle("-fx-background-color: #0097a7;");
                        battleGround[rowIndex][colIndex] = 1;
                        battleshipClick++;
                    }
                    else commentLabel.setText("NOT ALLOWED!");
                }
            }
            else if (destroyerSelected) {
                if(destroyerCount < 2) {
                    if (destroyerClick == 0) {
                        commentLabel.setText("");
                        button.setStyle("-fx-background-color: #ec407a;");
                        destroyerClick++;
                        battleGround[rowIndex][colIndex] = 2;
                    }
                    else if (destroyerClick == 1) {
                        if ((rowIndex > 0 && battleGround[rowIndex - 1][colIndex] == 2) || (rowIndex < 8 && battleGround[rowIndex + 1][colIndex] == 2) || (colIndex > 0 && battleGround[rowIndex][colIndex - 1] == 2) || (colIndex < 8 && battleGround[rowIndex][colIndex + 1] == 2)) {
                            commentLabel.setText("");
                            button.setStyle("-fx-background-color: #ec407a;");
                            destroyerCount++;
                            destroyerClick = 0;
                            battleGround[rowIndex][colIndex] = 2;
                        }
                        else commentLabel.setText("NOT ALLOWED!");
                    }
                }
            }
            else if (submarineSelected) {
                if(submarineClick < 3) {
                    button.setStyle("-fx-background-color: #b39ddb;");
                    submarineClick++;
                    battleGround[rowIndex][colIndex] = 3;
                }
            }
        }
    }

    private void initializeGrid(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                Button button = new Button();
                button.setPrefSize(66, 77);
                button.setStyle("-fx-background-color:  #b0bec5;");
                int row = i;
                int column = j;
                button.setOnAction(event -> handleButtonClick(button));
                gridPane.add(button, j, i);
            }
        }
    }

    public void initialize(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) battleGround[i][j] = 0;
        }
        battleshipClick = 0;
        destroyerClick = 0;
        destroyerCount = 0;
        submarineClick = 0;
        initializeGrid();


    }

    private int[][] pastedArray(int[][] array) {
        int[][] newArray = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].clone();
        }
        return newArray;
    }
    @FXML
    void changePlayer(ActionEvent e) {
        if(battleshipClick >= 3 && destroyerCount >= 2 && submarineClick >= 3){
            if(curr_player == 1){
                curr_player++;
                playerShips[0] = pastedArray(battleGround);
                playerLabel.setText("Officer 2");
                changePlayerButton.setText("Attack!");
                initialize();
            }
            else{
                playerShips[1] = pastedArray(battleGround);
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("shot.fxml"));
                    Parent root = loader.load();

                    Shot controller = loader.getController();
                    controller.setBattleGround(playerShips);

                    Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                catch(IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }




    @FXML
    void selectBattleship() {
        battleshipSelected = true;
        destroyerSelected = false;
        submarineSelected = false;
        selectedLabel.setText("PLACE BATTLESHIP (1X - 3 TILES)");
    }
    @FXML
    void selectDestroyer() {
        battleshipSelected = false;
        destroyerSelected = true;
        submarineSelected = false;
        selectedLabel.setText("PLACE DESTROYERS (2X - 2 TILES)");
    }
    @FXML
    void selectSubmarine() {
        battleshipSelected = false;
        destroyerSelected = false;
        submarineSelected = true;
        selectedLabel.setText("PLACE SUBMARINES (3X - 1 TILE)");
    }

}