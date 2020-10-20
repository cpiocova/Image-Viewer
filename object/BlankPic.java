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
 * @author Jose Pio
 */
public class BlankPic {  
    private Image imageOriginal;
    private Image imageModified;
       
    private ImageSize dimensions;
    private ImageSize originalDimensions;
    
    private Color [][] colorMatrix;
    private Color [][] originalMatrix;
    private Color [][] bufferNetpbm;

    private ArrayList uniqueColors;
    
    private String numberMagic;
    private String fileFormat;
    
    private int imageXWidth;
    private int imageYHeight;
    
    private int maxColor;
    
    public BlankPic() {
        this.imageXWidth = 0;
        this.imageYHeight = 0;
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

    public String getNumberMagic() {
        return numberMagic;
    }

    public void setNumberMagic(String numberMagic) {
        this.numberMagic = numberMagic;
    }
    
    private void scanNumberMagic(String line){
        int i = 0;
        String nm = "";
        while(i < line.length()) {
            if(line.charAt(i) == '#') {
                break;
            } else {
                if(line.charAt(i) == ' '){
                    i++;
                } else {
                    nm = nm + line.charAt(i);
                    i++;
                }
            }    
        }
        setNumberMagic(nm);   
    }
    
    private void scanDimensions(String line) {
        int []dim = new int[2];
        Scanner lines = new Scanner(line);
        for(int i=0; i < 2; i ++ ){
            if (lines.hasNextInt()) { 
                dim[i] = lines.nextInt();
            }    
        }
        setDimensions(new ImageSize(dim[0], dim[1]));
        setOriginalDimensions(new ImageSize(dim[0], dim[1]));
    }
    
    public double mapRangePgm(double number){
        double ent1 = 0;
        double ent2 = maxColor; 
        double ret1 = 0;
        double ret2 = 1;
        return (double) (((ret2 - ret1)/(ent2 - ent1)) * (number - ent2) + ret2);
    }
    
    public void pbmLoader(File path) {
        File pathAbs = path.getAbsoluteFile();
        Scanner scan;
        try {
            scan = new Scanner(pathAbs);           
            int lineNumber = 1;
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(!line.startsWith("#")) {
                    switch (lineNumber) {
                        case 1:
                            scanNumberMagic(line);
                            lineNumber++;
                            break;
                        case 2:
                            scanDimensions(line);
                            bufferNetpbm = new Color[originalDimensions.getWidth()][originalDimensions.getHeight()];
                            lineNumber++;
                            break;
                        default:
                            buildMatrixPbm(line);
                            lineNumber++;
                            break;
                    }
                }
            }
//            renderImageNetbpm();
            scan.close();
            setColorPixelsPbm();
        } catch (FileNotFoundException ex) {
            System.out.println("Fail Load");
        }
        System.out.println("Fail Load");
    }
    
    private void buildMatrixPbm(String line){
        Scanner numbers = new Scanner(line);
        while(numbers.hasNextInt()){
            double color = numbers.nextInt();
            if(color == 0) {
                color = 1;
            }else {
                color = 0;
            }
            bufferNetpbm[imageXWidth][imageYHeight] = new Color(color,color,color,1.0);
            imageXWidth++;
            if(imageXWidth == dimensions.getWidth()){
                imageXWidth = 0;
                imageYHeight++;
            }
        }
        numbers.close();
    }
    
     
    
    private void setColorPixelsPbm() {
        Color [][] current = new Color[dimensions.getWidth()][dimensions.getHeight()];
        Color [][] original = new Color[dimensions.getWidth()][dimensions.getHeight()];
        for (int y = 0; y < dimensions.getHeight(); y++) {
            for (int x = 0; x < dimensions.getWidth(); x++) {
                original[x][y] = current[x][y] = bufferNetpbm[x][y];
            }
        }
        setColorMatrix(current);
        setOriginalMatrix(original);    
    }
}

