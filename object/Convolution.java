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
    private Color [][] matrixConvolution;
    private int pivotX;
    private int pivotY;
    private int imageHeight;
    private int imageWidth;
    private int countElements;
    private ArrayList coordMatrix;

   public Convolution(int width, int height, int imageWidth, int imageHeight) {
       this.width = width;
       this.height = height;
       this.matrixConvolution = new Color[width][height];
       this.pivotY = (int) Math.round((double)this.width / 2);
       this.pivotX = (int) Math.round((double)this.height / 2);
       this.countElements = 0;
       this.imageWidth = imageWidth;
       this.imageHeight = imageHeight;
   }
   
   public void setMatrixConvolution(Color[][] colorMatrix) {
       
   }
   
    public void printPivot() {
       System.out.println(pivotX + " ,    y:  " + pivotY);
   }
    
   public void searchNS(int coordX, int coordY) {
        if(height > 1) {
            for(int i = 0; i < pivotX; i++){
                int north = coordX - i;
                if(north >= 0) {
                    searchEO(north, coordY);
                }
            }
            
            for(int i = 0; i < height - pivotX; i++){
                int south = coordX + i + 1;
                if(south <= imageHeight - 1) {
                    searchEO(coordX, coordY);               

                }
            }             
            
        }   else {
                searchEO(coordX, coordY);               
            }
        System.out.println(countElements);
    }

   
   public void searchEO(int coordX, int coordY) {
       for(int i = 0; i < pivotY; i++){
           int west = coordY - i;
           if(west >= 0) {
               countElements++;   
           }
       }
       for(int i = 0; i < width - pivotY; i++){
           int east = coordY + i + 1;
           if(east <= imageWidth - 1) {
               countElements++;
           }
       }       

   }

}
