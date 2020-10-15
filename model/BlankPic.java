/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Jose Pio
 */
public class BlankPic {  
    private Image imageOriginal;
       
    private ImageSize dimensions;
    
    private Color [][] colorMatrix;
    private Color [][] originalMatrix;
    
    private ArrayList uniqueColorsList;
    
    private String numberMagic;
    private String fileFormat;

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    
    public Image getImageOriginal() {
        return imageOriginal;
    }

    public void setImageOriginal(Image imageChanging) {
        this.imageOriginal = imageChanging;
    }
    

   public ImageSize getDimensions() {
       return dimensions;
   }
   
    public void setDimensions(ImageSize dimensions) {
        this.dimensions = dimensions;
    }

    public Color[][] getColorMatrix() {
        return colorMatrix;
    }

    public void setColorMatrix(Color[][] colorMatrix) {
        this.colorMatrix = colorMatrix;
    }

    public Color[][] getOriginalMatrix() {
        return originalMatrix;
    }

    public void setOriginalMatrix(Color[][] originalMatrix) {
        this.originalMatrix = originalMatrix;
    }

    public ArrayList getUniqueColorsList() {
        return uniqueColorsList;
    }

    public void setUniqueColorsList(ArrayList uniqueColorsList) {
        this.uniqueColorsList = uniqueColorsList;
    }
    
}

