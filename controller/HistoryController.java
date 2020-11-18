/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import object.ImageSize;
import object.Stack;
import object.UserActions;

/**
 * FXML Controller class
 *
 * @author thecnomacVZLA
 */
public class HistoryController implements Initializable {
    
    MainController mainController;

    @FXML
    private ImageView imageRedo2;
    @FXML
    private Label filterRedo2;
    @FXML
    private Label sizeRedo2;
    @FXML
    private ImageView imageRedo1;
    @FXML
    private Label filterRedo1;
    @FXML
    private Label sizeRedo1;
    @FXML
    private ImageView imageSelected;
    @FXML
    private Label filterSelected;
    @FXML
    private Label sizeSelected;
    @FXML
    private ImageView imageUndo1;
    @FXML
    private Label filterUndo1;
    @FXML
    private Label sizeUndo1;
    @FXML
    private ImageView imageUndo2;
    @FXML
    private Label filterUndo2;
    @FXML
    private Label sizeUndo2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void visualizeHistory(MainController mainInstanceController) {
        mainController = mainInstanceController; 
        UserActions userActions  = mainController.userActions;
        Stack stackSelected = userActions.getStackPointer();
        WritableImage imgSelected =  stackSelected.getImageCurrent();
        String nameFilterSelected = stackSelected.getNameFilter();
        ImageSize dimSelected = stackSelected.getDimensions();
        
        imageSelected.setImage(imgSelected);
        filterSelected.setText("Filter applied: " + nameFilterSelected);
        sizeSelected.setText("Size: " + dimSelected.getHeight() + " x " + dimSelected.getWidth());
        
        renderUndo();
        renderRedo();
        
        
     }
     
     private void renderUndo() {
        UserActions userActions  = mainController.userActions;

        if(mainController.userActions.canDecreaseView(1)) {
            int pointer = userActions.getPointer();
            int capacity = userActions.getCapacity();
            int backward = pointer - 1 < 0 ? capacity - Math.abs(pointer - 1) : pointer - 1;
            Stack pointUndo1 = mainController.userActions.getPointer(backward);
            WritableImage imgUndo1 =  pointUndo1.getImageCurrent();
            String nameFilterUndo1 = pointUndo1.getNameFilter();
            ImageSize dimUndo1 = pointUndo1.getDimensions();

            imageUndo1.setImage(imgUndo1);
            filterUndo1.setText("Filter applied: " + nameFilterUndo1);
            sizeUndo1.setText("Size: " + dimUndo1.getHeight() + " x " + dimUndo1.getWidth());
        }
        
        if(mainController.userActions.canDecreaseView(2)) {
            int pointer = userActions.getPointer();
            int capacity = userActions.getCapacity();
            int backward = pointer - 2 < 0 ? capacity - Math.abs(pointer - 2) : pointer - 2;
            Stack pointUndo2 = mainController.userActions.getPointer(backward);
            WritableImage imgUndo2 =  pointUndo2.getImageCurrent();
            String nameFilterUndo2 = pointUndo2.getNameFilter();
            ImageSize dimUndo2 = pointUndo2.getDimensions();

            imageUndo2.setImage(imgUndo2);
            filterUndo2.setText("Filter applied: " + nameFilterUndo2);
            sizeUndo2.setText("Size: " + dimUndo2.getHeight() + " x " + dimUndo2.getWidth());
        } 
         
         
     }
     
     private void renderRedo() {
        UserActions userActions  = mainController.userActions;
        
        if(mainController.userActions.canIncreaseView(1)) {
            int pointer = userActions.getPointer();
            int capacity = userActions.getCapacity();
            int fordward = (pointer + 1) % capacity;;
            Stack pointRedo1 = mainController.userActions.getPointer(fordward);
            WritableImage imgRedo1 =  pointRedo1.getImageCurrent();
            String nameFilterRedo1 = pointRedo1.getNameFilter();
            ImageSize dimRedo1 = pointRedo1.getDimensions();

            imageRedo1.setImage(imgRedo1);
            filterRedo1.setText("Filter applied: " + nameFilterRedo1);
            sizeRedo1.setText("Size: " + dimRedo1.getHeight() + " x " + dimRedo1.getWidth());
        }
        
        if(mainController.userActions.canIncreaseView(2)) {
            int pointer = userActions.getPointer();
            int capacity = userActions.getCapacity();
            int fordward = (pointer + 2) % capacity;;            
            Stack pointRedo2= mainController.userActions.getPointer(fordward);
            WritableImage imgRedo2 =  pointRedo2.getImageCurrent();
            String nameFilterRedo2 = pointRedo2.getNameFilter();
            ImageSize dimRedo2 = pointRedo2.getDimensions();

            imageRedo1.setImage(imgRedo2);
            filterRedo1.setText("Filter applied: " + nameFilterRedo2);
            sizeRedo1.setText("Size: " + dimRedo2.getHeight() + " x " + dimRedo2.getWidth());
        }
        
        

         
     }
    
}
