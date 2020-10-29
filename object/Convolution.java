/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;


import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
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
    private Color[][] colorMatrix;
    private Image imageModified;


   public Convolution(int width, int height, int imageWidth, int imageHeight, String filterName, BlankPic pic) {
       this.width = width;
       this.height = height;
       this.matrixConvolution = new double[height][width];
       this.pivotY = (int) Math.round((double)this.width / 2);
       this.pivotX = (int) Math.round((double)this.height / 2);
       this.countElements = 0;
       this.imageWidth = imageWidth;
       this.imageHeight = imageHeight;
       this.filterName = filterName;
       this.coordinates = new ArrayList();
       this.colorMatrix = pic.getColorMatrix();
       this.imageModified = pic.getImageModified();
   }
   
   public Color setMatrixConvolution() {
       Color colorRet = new Color(1,1,1,1);
       switch(filterName) {
           case "average":
               fillAverage();
               colorRet = processLineal();
               break;
           case "median":
               colorRet = processNoLineal();
               break;
           case "gaussian":
               fillGaussian();
               normalizeGaussianMatrix();
               colorRet = processLineal();
               break;          
           case "laplacian":
               fillLaplacian();
               colorRet = processLineal();
               break;
           case "gausslap":
               fillGaussian();
               normalizeGaussianMatrix();
               colorRet = processLineal();
               break;
           case "sobel":
               fillSobel();
               colorRet = processLineal();
               break;
       }
    return colorRet;
   }
   
   private Color processNoLineal() {
       
    ArrayList messyColors = new ArrayList();
    PixelReader pixelReader = imageModified.getPixelReader();
    for(int i = 0; i < coordinates.size(); i++) {
        Point info = (Point) coordinates.get(i);
        int pX = info.getPosX();
        int pY = info.getPosY();
        messyColors.add(pixelReader.getArgb(pX, pY));
    }
    
    Collections.sort(messyColors);

    int medianColor = (int) messyColors.get(countElements/2);
    double r = (medianColor & 0xFF0000) >> 16;
    double g = (medianColor & 0xFF00) >> 8;
    double b = (medianColor & 0xFF);

    return new Color(r/255,g/255,b/255, 1.0);
   }
   
   private Color processLineal() {
        double red = 0;
        double green = 0;
        double blue = 0;
        
        for(int i = 0; i < coordinates.size(); i++) {
            Point info = (Point) coordinates.get(i);
            int pX = info.getPosX();
            int pY = info.getPosY();
            int iX = info.getIndexRow();
            int iY = info.getIndexColumn();
                       
            double coefficient = matrixConvolution[iX][iY];
            
            red = red + (colorMatrix[pX][pY].getRed() * coefficient);
            green = green + (colorMatrix[pX][pY].getGreen() * coefficient);
            blue = blue + (colorMatrix[pX][pY].getBlue() * coefficient);                      
        }
       
        if(red > 1) red = 1;
        if(green > 1) green = 1;
        if(blue > 1) blue = 1;
        
        if(red < 0) red = 0;
        if(green < 0) green = 0;
        if(blue < 0) blue = 0;


        return new Color(red, green, blue, 1.0);
   }
  
    
   private void fillAverage() {
    for (int x = 0; x < height; x++) {
        for (int y = 0; y < width; y++) {
            matrixConvolution[x][y] = (1.0 / (double) countElements);
        }
    }
   }
   
   private void fillSobel() {
        int [] comunVectorRow = Pascal.generateVector(width);  
        int [] comunVectorColumn = Pascal.generateVector(height);

        if(height == 1 ){ // Seria un Sobel Derivando en X, actuara el pivotY
            for (int y = 0; y < width; y++) {
                if(y == pivotY - 1) {
                    matrixConvolution[0][y] = 0;
                } else if(y > pivotX - 1){
                    matrixConvolution[0][y] = comunVectorRow[y];
                } else {
                    matrixConvolution[0][y] = -comunVectorRow[y];
                }
            }
        }else if(width == 1) { // Seria un sobel Derivando en Y,  actuara el pivotX
            for (int x = 0; x < height; x++) {
                if(x == pivotX - 1) {
                    matrixConvolution[x][0] = 0;
                } else if(x > pivotX - 1){
                    matrixConvolution[x][0] = -comunVectorColumn[x];
                } else {
                    matrixConvolution[x][0] = comunVectorColumn[x];
                }
            }
        } else {
           fillSobelY(comunVectorRow);
        }
   }
   
    private void fillSobelY(int [] comunVectorRow) {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if(x == pivotX - 1) {
                    matrixConvolution[x][y] = 0;
                }else if(x > pivotX - 1){
                    matrixConvolution[x][y] = (double) -comunVectorRow[y] / (double) x;
                }else { // x < pivotX - 1
                    matrixConvolution[x][y] = (double) comunVectorRow[y] / (double) (height - x - 1);
                }   
            }
        }
    }

    private void fillSobelX(int [] comunVectorColumn) {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if(y == pivotX - 1) {
                    matrixConvolution[x][y] = 0;
                }else if(y > pivotX - 1){
                    matrixConvolution[x][y] = (double) comunVectorColumn[x] / (double) (width - y);
                }else { // y < pivotX - 1
                    matrixConvolution[x][y] = (double) -comunVectorColumn[x] / (double) (y+1);
                }   
            }
        }
    }    
    
   
   private void TestMatrixConvol() {
    for (int x = 0; x < height; x++) {
       for (int y = 0; y < width; y++) {
           System.out.println(matrixConvolution[x][y] + " ");
       }
   }    
   }
   
    private void fillGaussian() {
        int [] pascalX = new int[height]; // height === axisX
        int [] pascalY = new int[width]; // width === axisY
                                                      
        pascalX = Pascal.generateVector(height); 
        pascalY =  Pascal.generateVector(width);
         
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                matrixConvolution[x][y] = (pascalX[x] * pascalY[y]);
            }
        }
    }
    
    private void fillLaplacian() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                matrixConvolution[x][y] = -1;
            }
        }
        matrixConvolution[pivotX - 1][pivotY - 1] = countElements - 1;
    }
    
    
   
   private double setArraySum() {
       double arraySum = 0;
       for(int i = 0; i < coordinates.size(); i++) {
        Point info = (Point) coordinates.get(i);
        int iX = info.getIndexRow();
        int iY = info.getIndexColumn();   
        arraySum = arraySum + matrixConvolution[iX][iY];           
       }
       return arraySum;
   }
    
    private void normalizeGaussianMatrix() {
        double elevation;
        if(countElements == width * height) {
            elevation = Math.pow(2, width + height - 2);
        } else {
            elevation = setArraySum();
        }
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                matrixConvolution[x][y] = matrixConvolution[x][y] / elevation;
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
                if(south < imageWidth - 1) {
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
           if(east < imageHeight - 1) {
                coordinates.add(new Point(coordX, east, indexRow, pivotY + i));
               countElements++;
           }
       }       

   }

}
