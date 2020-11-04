/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import javafx.scene.image.WritableImage;

/**
 *
 * @author Jose Pio
 */
public class Stack {
    private WritableImage imageCurrent;
    private int pointerCurrent;
    private double positionSlider;
    private String nameFilter;
    private boolean isSlider;
    
    
    public Stack(WritableImage imageCurrent, String nameFilter) {
        this.imageCurrent = imageCurrent;
        this.nameFilter = nameFilter;
    }

    public WritableImage getImageCurrent() {
           return imageCurrent;        
    }
    
    public boolean getOrientationRotate() {
        if(nameFilter == "rotatePositive") {
            return true;
        }
        return false;
    }
    
}
