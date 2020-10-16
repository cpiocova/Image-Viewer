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
import javafx.scene.image.Image;
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
    
    private Image imagePic;
    
    private int imageWidth;
    private int imageHeight;
    private int viewWidth;
    private int viewHeight;
    
    private int maxRepeat;
    private int minRepeat;

    
    private PixelReader pixelReaderImage;
    private PixelWriter pixelHistogramWriter;
    
    private Color [][] colorMatrix;
    
    private ArrayList arrayNormalColor;    
    private DataColor dtc;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void allColors(BlankPic pic) {

        imageWidth = pic.getDimensions().getWidth();
        imageHeight = pic.getDimensions().getHeight();
        
        arrayNormalColor = new ArrayList();
        colorMatrix = pic.getColorMatrix();
        
        if(pic.getImageModified() instanceof Image) {
            imagePic = pic.getImageModified();
        } else {
            imagePic = (WritableImage) pic.getImageModified();
        }
        
        pixelReaderImage = imagePic.getPixelReader();
        
        traverseMatrixColors();
        int arr[] = calculateMaxMinRepeats();
        maxRepeat = arr[0];
        minRepeat = arr[1];
        
        viewWidth = 256;
        viewHeight = 200;
        
        writableHistogramImage = new WritableImage(viewWidth, viewHeight);       
        pixelHistogramWriter = writableHistogramImage.getPixelWriter();
        
        test();
        drawHistogram();
           
        
        imageView.setImage(writableHistogramImage);
        imageView.setFitWidth(viewWidth);
        imageView.setFitHeight(viewHeight);
        imageView.setPreserveRatio(true);

    }
    
    private void drawHistogram() {
        Color color = new Color(0,0,0,1.0);
        for (int counter = 0; counter < arrayNormalColor.size(); counter++) { 		      
           DataColor data = (DataColor) arrayNormalColor.get(counter);
           int x = data.getColor();
           int repeats = data.getRepetitions();
           int normalizedY = mappingRangeRepeats(repeats);
           for(int start = 1; start < normalizedY; start++){
               int y = viewHeight - start;
               pixelHistogramWriter.setColor(x,y,color);
           }
        }  

    }
    
    private void traverseMatrixColors() {
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int color = mappingRangeColor(pixelReaderImage.getArgb(x, y));
                checkUniqueColors(color);
            }
        }
    } 

    private int mappingRangeColor(int x){
        double ent1 = -16777216;
        double ent2 = -1; // En mis calculos ent1 es el negro
        double ret1 = 0;
        double ret2 = 255;
        return (int) (((ret2 - ret1)/(ent2 - ent1)) * (x - ent2) + ret2);
    }
    
    private int mappingRangeRepeats(int x){
        double ent1 = minRepeat;
        double ent2 = maxRepeat;
        double ret1 = 20;
        double ret2 = 180;
        if(maxRepeat == minRepeat) return 180; 
        return (int) (((ret2 - ret1)/(ent2 - ent1)) * (x - ent2) + ret2);
    }

    private int[] calculateMaxMinRepeats() {
        DataColor data = (DataColor) arrayNormalColor.get(0);
        int max = data.getRepetitions();
        int min = data.getRepetitions();
        for (int counter = 0; counter < arrayNormalColor.size(); counter++) { 		      
            data = (DataColor) arrayNormalColor.get(counter);
            int repeat = data.getRepetitions();
            if(repeat > max) {
                max = repeat;
            }
            if(repeat < min) {
                min = repeat;
            }
        }
        return new int[] {max, min};        
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

    private void  test() {
        for (int counter = 0; counter < arrayNormalColor.size(); counter++) { 		      
            DataColor data = (DataColor) arrayNormalColor.get(counter);
            System.out.println("--------------" + counter + "-----------------");            
            System.out.println("color: " + data.getColor());
            System.out.println("repetitions: " + data.getRepetitions());   
        }
    }

    
}
