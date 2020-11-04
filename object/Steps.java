/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Jose Pio
 */
public class Steps {
    private Color [][] colorMatrix;
    private WritableImage imageCurrent;
    private int pointerCurrent;
    private double positionSlider;
    
    
    public Steps(WritableImage imageCurrent) {
        this.imageCurrent = imageCurrent;
    }

    public double getPositionSlider() {
        return positionSlider;
    }

    public void setPositionSlider(double positionSlider) {
        this.positionSlider = positionSlider;
    }
    
    
    
    public Color[][] getColorMatrix() {
        return colorMatrix;
    }

    public void setColorMatrix(Color[][] colorMatrix) {
        this.colorMatrix = colorMatrix;
    }

    public WritableImage getImageCurrent() {
        return imageCurrent;
    }

    public void setImageCurrent(WritableImage imageCurrent) {
        this.imageCurrent = imageCurrent;
    }

    public int getPointerCurrent() {
        return pointerCurrent;
    }

    public void setPointerCurrent(int pointerCurrent) {
        this.pointerCurrent = pointerCurrent;
    }
    
    
    
}
