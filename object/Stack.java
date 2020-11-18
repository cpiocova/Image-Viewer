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
    private String nameFilter;
    private ImageSize dimensions;
    
    public Stack(WritableImage imageCurrent, String nameFilter, ImageSize dimensions) {
        this.imageCurrent = imageCurrent;
        this.nameFilter = nameFilter;
        this.dimensions = dimensions;
    }

    public ImageSize getDimensions() {
        return dimensions;
    }

    public WritableImage getImageCurrent() {
           return imageCurrent;        
    }
    
    public String getNameFilter() {
           return nameFilter;        
    }
    
    
    
    
}
