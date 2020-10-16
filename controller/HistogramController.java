/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import model.BlankPic;

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
    private WritableImage writableImage;
    
    private PixelReader pixelReader;
    private PixelWriter pixelWriter;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void allColors(ArrayList uniqueColors) {
        int width = 256;
        int height = 200;
//        int count[256]
        writableImage = new WritableImage(width, height);       
        pixelWriter = writableImage.getPixelWriter();
        Color color = (Color) uniqueColors.get(0);
        int r = (int)(color.getRed() * 255);
        
//        int g = (int)(color.getGreen() * 255);
//        int b = (int)(color.getBlue() * 255);
        
        System.out.println(r);
//        for (int y = 0; y < height; y++) {
//           for (int x = 0; x < width; x++) {
//
//               pixelWriter.setColor(x,y,pbm);
// 
//           }
//        }
//        
        
        imageView.setImage(writableImage);
    }

   
    
}
