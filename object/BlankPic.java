/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Jose Pio y Giselt Parra
 */
public class BlankPic {  
    private Image imageOriginal;
    private Image imageModified;
       
    private ImageSize dimensions;
    private ImageSize originalDimensions;
    
    private Color [][] colorMatrix;
    private Color [][] scaleMatrix;
    private Color [][] originalMatrix;
    private Color [][] bufferNetpbm;

    private ArrayList uniqueColors;
    
    private String numberMagic;
    private String fileFormat;
        
    private int maxColor;
    private boolean grayScaleFlag;
    private boolean initGrayScale;
    
    public void BlankPic() {
        this.grayScaleFlag = false;
        this.initGrayScale = false;
    }

    public boolean isGrayScaleFlag() {
        return grayScaleFlag;
    }

    public void setGrayScaleFlag(boolean grayScaleFlag) {
        this.grayScaleFlag = grayScaleFlag;
    }

    public boolean isInitGrayScale() {
        return initGrayScale;
    }

    public void setInitGrayScale(boolean initGrayScale) {
        this.initGrayScale = initGrayScale;
    }
    
 
    
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

    public Image getImageModified() {
        return imageModified;
    }

    public void setImageModified(Image imageModified) {
        this.imageModified = imageModified;
    }    
    

    public ImageSize getDimensions() {
        return dimensions;
    }
   
    public void setDimensions(ImageSize dimensions) {
        this.dimensions = dimensions;
    }

    public ImageSize getOriginalDimensions() {
        return originalDimensions;
    }
   
    public void setOriginalDimensions(ImageSize originalDimensions) {
        this.originalDimensions = originalDimensions;
    }
    
    
    public Color[][] getColorMatrix() {
        return colorMatrix;
    }

    public void setColorMatrix(Color[][] colorMatrix) {
        this.colorMatrix = colorMatrix;
    }
    
    public Color[][] getScaleMatrix() {
        return scaleMatrix;
    }

    public void setScaleMatrix(Color[][] scaleMatrix) {
        this.scaleMatrix = scaleMatrix;
    }

    public Color[][] getOriginalMatrix() {
        return originalMatrix;
    }

    public void setOriginalMatrix(Color[][] originalMatrix) {
        this.originalMatrix = originalMatrix;
    }

    public ArrayList getUniqueColors() {
        return uniqueColors;
    }

    public void setUniqueColors(ArrayList uniqueColorsList) {
        this.uniqueColors = uniqueColorsList;
    }

    public int getMaxColor() {
        return maxColor;
    }

    public void setMaxColor(int maxColor) {
        this.maxColor = maxColor;
    }
    
    public double mapRangePgm(double number){
        double ent1 = 0;
        double ent2 = maxColor; 
        double ret1 = 0;
        double ret2 = 1;
        return (double) (((ret2 - ret1)/(ent2 - ent1)) * (number - ent2) + ret2);
    }
    
    public static int mappingRangeColor(int x){
        double ent1 = -16777216;
        double ent2 = -1; // En mis calculos ent1 es el negro
        double ret1 = 0;
        double ret2 = 255;
        return (int) (((ret2 - ret1)/(ent2 - ent1)) * (x - ent2) + ret2);
    }
    
}

