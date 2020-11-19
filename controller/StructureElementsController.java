/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.ArbitraryKernelViewController.isNumeric;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import object.StructuringElem;
import org.opencv.core.Mat;

/**
 * FXML Controller class
 *
 * @author thecnomacVZLA
 */
public class StructureElementsController implements Initializable {
    private MainController mainController;
    private Mat kernel;
    private boolean isApply;
    
    @FXML
    private ToggleGroup elementTypeGroup;
    @FXML
    private Slider sliderToSizeX;
    @FXML
    private Slider sliderToSizeY;
    @FXML
    private TextField inputCoordX;
    @FXML
    private TextField inputCoordY;
    @FXML
    private Label labelSizeX;
    @FXML
    private Label labelSizeY;
    
    final ChangeListener<Number> sliderSizeX = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToSizeX.valueProperty().set(roundedValue);
        labelSizeX.setText(Integer.toString(roundedValue));

    };
    final ChangeListener<Number> sliderSizeY = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToSizeY.valueProperty().set(roundedValue);
        labelSizeY.setText(Integer.toString(roundedValue));

    };
    @FXML
    private Button setButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        sliderToSizeX.valueProperty().addListener(sliderSizeX);
        sliderToSizeY.valueProperty().addListener(sliderSizeY);
        isApply = true;

    }    
    
    public void setValues(MainController mainInstanceController) {
        mainController = mainInstanceController;        
    }

    
    


    @FXML
    private void setStructuringElement(ActionEvent event) {
        checkValues();
    }
    
    private void checkValues() {
        int type = elementTypeGroup.getToggles().indexOf(elementTypeGroup.getSelectedToggle());
        int height = (int) sliderToSizeX.getValue();
        int width = (int) sliderToSizeY.getValue();
        String inputX = inputCoordX.getText();
        String inputY = inputCoordY.getText();
        
        if(
            width + height > 2 && 
            inputX != null && 
            inputY != null && 
            !inputX.isEmpty() &&
            !inputY.isEmpty() &&
            isNumeric(inputX) &&
            isNumeric(inputY)
                
        ) {
            
            int pivotX = Integer.parseInt(inputX);
            int pivotY = Integer.parseInt(inputY);
            
            if(pivotX >= 0 && pivotX < width && pivotY >= 0 && pivotY < height) {
                StructuringElem elem = new StructuringElem();
                elem.setElem(type, width, height, pivotX, pivotY);
                kernel = elem.getStructuringElem();
                
            } else {
                this.isApply = false;
            }

            
        } else {
            this.isApply = false;
        }
        saveKernel();


    }
    
    
        private void saveKernel() {
        if(this.isApply == true) {
            mainController.receiveStructuringElem(kernel);

            Stage stage = (Stage) setButton.getScene().getWindow();
            stage.close();            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("The pivots or anchors must be a number greater than or equal to zero and less than the respective size");
            alert.showAndWait();
            this.isApply = true;
        }
    }

    
}
