/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import object.BlankPic;
import object.DataColor;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Jose Pio
 */
public class HistogramController implements Initializable {

    @FXML
    private ImageView imageView;
    private WritableImage writableHistogramImage;
    private WritableImage imageApp;
    
    private int imageWidth;
    private int imageHeight;

    
    private PixelReader pixelReaderImage;
    private PixelWriter pixelHistogramWriter;
    
    private Color [][] colorMatrix;
    
    private ArrayList arrayNormalColor;    
    private DataColor dtc;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private void initMain() {
  
    }
    
    public void allColors(BlankPic pic) {

        imageWidth = pic.getDimensions().getWidth();
        imageHeight = pic.getDimensions().getHeight();
        
        arrayNormalColor = new ArrayList();
        colorMatrix = pic.getColorMatrix();
        imageApp = (WritableImage) pic.getImageModified();
        pixelReaderImage = imageApp.getPixelReader();
        
        traverseMatrixColors();
        
        int width = 256;
        int height = 200;
        
        writableHistogramImage = new WritableImage(width, height);       
        pixelHistogramWriter = writableHistogramImage.getPixelWriter();
        
      
        for (int counter = 0; counter < arrayNormalColor.size(); counter++) { 		      
            DataColor data = (DataColor) arrayNormalColor.get(counter);
            System.out.println("--------------" + counter + "-----------------");            
            System.out.println("color: " + data.getColor());
            System.out.println("repetitions: " + data.getRepetitions());   
        }
      
        
        imageView.setImage(writableHistogramImage);
    }

    private int mappingRange(int x){
        double ent1 = -16777216;
        double ent2 = -1;
        double ret1 = 0;
        double ret2 = 255;
        return (int) (((ret2 - ret1)/(ent2 - ent1)) * (x - ent2) + ret2);
    }
   
    private void  checkUniqueColors(int colorPixel) {
        DataColor data = new DataColor(colorPixel);
        if(!arrayNormalColor.contains(data)){
            arrayNormalColor.add(data);
        } else {
            int index = arrayNormalColor.indexOf(data);
            DataColor newData = (DataColor) arrayNormalColor.get(index);
            int repeat = newData.getRepetitions() + 1;
            newData.setRepetitions(repeat);
            arrayNormalColor.set(index, newData);
        }
    }
    
    private void traverseMatrixColors() {
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int color = mappingRange(pixelReaderImage.getArgb(x, y));
                checkUniqueColors(color);
            }
        }
    }
    
}

