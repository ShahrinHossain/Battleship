package com.example.bships;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class Shot {
    @FXML
    Label attackedPlayer, statusLabel;
    @FXML
    private Label endLabel;
    @FXML
    private Label phaseLabel;

    @FXML
    Button shipShuffle;

    @FXML
    private GridPane gridPane1;

    private boolean hit = true;

    static int playerNo = 1;

    private int[][][] battleGround = new int[2][9][9];

    private int[] battleshipCells = {3,3};
    private int[] destroyerCells = {4,4};
    private int[] submarineCells = {3,3};
    
    private boolean over =  false;

    public void setBattleGround(int[][][] battleGround) {
        this.battleGround = battleGround;
    }

    public void endGame(){
        statusLabel.setText("OFFICER " + ((playerNo + 1) % 2 == 0 ? 1 : 2) + "\n" + " WINS!!");
        shipShuffle.setStyle("-fx-background-color: orange;");
        shipShuffle.setText("GAME ENDS");
        endLabel.setText("VICTORY !!");
        phaseLabel.setText("");
        attackedPlayer.setText("");
        over = true;
    }
    
    private void handleButtonClick(Button button, int playerNo) {
        if (hit) {
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);
            if (battleGround[playerNo][row][col] == 1) {
                battleshipCells[playerNo]--;
                statusLabel.setText("HIT! "+"\n"+"FIRE AGAIN..");
                button.setStyle("-fx-background-color: red;");
                battleGround[playerNo][row][col] = -1;
            }
            else if (battleGround[playerNo][row][col] == 2) {
                destroyerCells[playerNo]--;
                statusLabel.setText("HIT! "+"\n"+"FIRE AGAIN..");
                button.setStyle("-fx-background-color: red;");
                battleGround[playerNo][row][col] = -1;
            }
            else if (battleGround[playerNo][row][col] == 3) {
                submarineCells[playerNo]--;
                statusLabel.setText("HIT! "+"\n"+"FIRE AGAIN..");
                button.setStyle("-fx-background-color: red;");
                battleGround[playerNo][row][col] = -1;
            }
            else {
                hit = false;
                statusLabel.setText("MISSED!");
                button.setStyle("-fx-background-color: white;");
                battleGround[playerNo][row][col] = -2;
            }
        }
        if (battleshipCells[playerNo] == 0 && destroyerCells[playerNo] == 0 && submarineCells[playerNo] == 0)
        {
            shipShuffle.setStyle("-fx-background-color: black;");
            endGame();
        }
    }

    private void initializeGrid() {
        int[][] currentPlayerBattleground = battleGround[playerNo];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                button.setPrefSize(66, 77);
                button.setStyle("-fx-background-color:  #b0bec5;");
                if (currentPlayerBattleground[i][j] == -1) {
                    button.setStyle("-fx-background-color: red;");
                }
                else if (currentPlayerBattleground[i][j] == -2) {
                    button.setStyle("-fx-background-color: white;");
                }
                else {
                    button.setStyle("");
                }
                button.setOnAction(event -> handleButtonClick(button, playerNo));
                gridPane1.add(button, j, i);
            }
        }
    }


    public void initialize(){
        initializeGrid();
        hit = true;
        attackedPlayer.setText("TIME TO ATTACK PLAYER " + (playerNo+1));
    }

    public void changePlayer() throws IOException {
        if(!hit){
            playerNo = (playerNo + 1)%2;
            gridPane1.getChildren().clear();
            statusLabel.setText("");
            initialize();
        }
    }
    
}


