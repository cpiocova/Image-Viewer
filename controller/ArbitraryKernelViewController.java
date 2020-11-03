/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import object.Convolution;

/**
 * FXML Controller class
 *
 * @author Jose Pio
 */
public class ArbitraryKernelViewController implements Initializable {
    MainController mainController;

    /**
     * Initializes the controller class.
     */
    
    private double [][] matrixConvolution;
    private TextField[][] textFields;
    private int width;
    private int height;
    private boolean typingError;
    private boolean isApply;
    @FXML
    private AnchorPane gridWrapper;
    @FXML
    private Button saveButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.typingError = false;
        this.isApply = false;
    }
    
    public void setArbitraryMatrix(MainController mainInstanceController, int height, int width) {
        mainController = mainInstanceController; 
        this.width = width;
        this.height = height;
        int pivotY = (int) Math.round((double)this.width / 2);
        int pivotX = (int) Math.round((double)this.height / 2);
        textFields = new TextField[this.height][this.width]; 
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20); 
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER); 
        gridPane.setPadding(new Insets(50, 50, 50, 50)); 
        matrixConvolution = new double[height][width];
        for (int y = 0; y < this.width; y++) {
            for (int x = 0; x < this.height; x++) {
                TextField infoCoord = new TextField();
                infoCoord.setId("coord"+x+""+y);
                infoCoord.setPromptText("Enter coord: " + x + "" + y);                
                if(x == pivotX - 1 && y == pivotY - 1) {
                    infoCoord.setStyle("-fx-background-color: rgba(3,172,240,0.2);");
                }
               textFields[x][y] = infoCoord;
               gridPane.add(textFields[x][y], y, x);
            }
        }
        gridWrapper.getChildren().add(gridPane);
    }

    @FXML
    private void applyKernel(ActionEvent event) {
        for (int x = 0; x < this.height; x++) {
            for (int y = 0; y < this.width; y++) {
               String textCoord = textFields[x][y].getText();
               if(isNumeric(textCoord)) {
                double numberCoord = Double.parseDouble(textCoord);
                matrixConvolution[x][y] = numberCoord;
               } else {
                   this.typingError = true;
                   break;
               }
            }
        }
        if(this.typingError == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("All values ​​must be number");
            alert.showAndWait();
            this.typingError = false;
        } else {
            mainController.receiveParamsArbitraryKernel(matrixConvolution, width, height);
            this.isApply = true;
        }
    }
    
    public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}

    @FXML
    private void saveKernel(ActionEvent event) {
        if(this.isApply == true) {
            mainController.filterArbitraryContext();

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("You must apply before saving");
            alert.showAndWait();
        }
    }
    
}
