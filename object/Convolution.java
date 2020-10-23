/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;


import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author Jose Pio
 */
public class Convolution {
    private int width;
    private int height;
    private double [][] matrixConvolution;
    private int pivotX;
    private int pivotY;
    private int imageHeight;
    private int imageWidth;
    private int countElements;
    private ArrayList coordinates;
    private String filterName;

   public Convolution(int width, int height, int imageWidth, int imageHeight, String filterName, BlankPic pic) {
       this.width = width;
       this.height = height;
       this.matrixConvolution = new double[width][height];
       this.pivotY = (int) Math.round((double)this.width / 2);
       this.pivotX = (int) Math.round((double)this.height / 2);
       this.countElements = 0;
       this.imageWidth = imageWidth;
       this.imageHeight = imageHeight;
       this.filterName = filterName;
       this.coordinates = new ArrayList();
   }
   
   public Color setMatrixConvolution(Color [][] colorMatrix) {
       Color colorRet = new Color(1,1,1,1);
       switch(filterName) {
           case "average":
               fillAverage();
               colorRet = processItems(colorMatrix);
               countElements = 0;
       }
    return colorRet;
   }
   
   private Color processItems(Color[][] colorMatrix) {
        double red = 0;
       double green = 0;
       double blue = 0;

  
        for(int i = 0; i < coordinates.size(); i++) {
            Point info = (Point) coordinates.get(i);
            int pX = info.getPosX();
            int pY = info.getPosY();
            int iX = info.getIndexRow();
            int iY = info.getIndexColumn();

            red = red + (colorMatrix[pX][pY].getRed() / countElements);
            green = green + (colorMatrix[pX][pY].getGreen() / countElements);
            blue = blue + (colorMatrix[pX][pY].getBlue() /countElements);      
        }
       
        if(red > 1) red = 1;
        if(green > 1) green = 1;
        if(blue > 1) blue = 1;


        return new Color(red, green, blue, 1.0);
   }
   
    
   private void fillAverage() {
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
         matrixConvolution[x][y] = (1 / countElements);
        }
    }
   }
    
   public void searchNS(int coordX, int coordY) {
        if(height > 1) {
            for(int i = 0; i < pivotX; i++){
                int north = coordX - i;
                if(north >= 0) {
                    searchEO(north, coordY, pivotX - i - 1);
                }
            }
            
            for(int i = 0; i < height - pivotX; i++){
                int south = coordX + i + 1;
                if(south < imageHeight - 1) {
                    searchEO(south, coordY, pivotX + i);
                }
            }             
            
        }else {
            searchEO(coordX, coordY, 0);
        }
    }

   
   public void searchEO(int coordX, int coordY, int indexRow) {
       for(int i = 0; i < pivotY; i++){
           int west = coordY - i;
           if(west >= 0) {
               coordinates.add(new Point(coordX, west, indexRow, pivotY - i - 1));
               countElements++;
           }
       }
       for(int i = 0; i < width - pivotY; i++){
           int east = coordY + i + 1;
           if(east < imageWidth - 1) {
                coordinates.add(new Point(coordX, east, indexRow, pivotY + i));
               countElements++;
           }
       }       

   }

}
