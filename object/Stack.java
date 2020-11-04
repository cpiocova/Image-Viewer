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
//        this.isSlider = isSlider;
//        this.positionSlider = positionSlider;
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

    public double getPositionSlider() {
        return positionSlider;
    }

    public void setPositionSlider(double positionSlider) {
        this.positionSlider = positionSlider;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public boolean isIsSlider() {
        return isSlider;
    }

    public void setIsSlider(boolean isSlider) {
        this.isSlider = isSlider;
    }


    
    
    
}
