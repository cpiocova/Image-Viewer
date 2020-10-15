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
    private WritableImage writableImage;
    private PixelReader pixelReader;
    private PixelWriter pixelWriter;
    
    private Image imageChanging;
    private Image imageDefault;
    
    private int imageWidth;
    private int imageHeight;
    
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

    public Image getImageDefault() {
        return imageDefault;
    }


    public void setImageDefault(Image imageDefault) {
        this.imageDefault = imageDefault;
    }    
    
    
    public Image getImageChanging() {
        return imageChanging;
    }

    public void setImageChanging(Image imageChanging) {
        this.imageChanging = imageChanging;
    }
    

   public ImageSize getDimensions() {
       return dimensions;
   }
   
    public void setImageSize(ImageSize dimensions) {
        this.dimensions = dimensions;
    }   
   
    

    
    

}

