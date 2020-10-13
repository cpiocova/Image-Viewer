/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piopdi1;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.stage.FileChooser;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;





/**
 *
 * @author Jose Pio
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btnLoadImage;
    @FXML
    private ImageView imageView;
    
    private Image image;
    private WritableImage writableImage;

    
    private PixelReader pixelReader;
    private PixelWriter pixelWriter;
    
    private int imageWidth;
    private int imageHeight;
    private Color [][] colorMatrix;
    @FXML
    private ToggleButton buttonToNegative;
    @FXML
    private ToggleButton buttonToGrayScale;
    @FXML
    private ToggleButton buttonToBlackWhite;
    @FXML
    private Label labelLoadMessage;
    private Label infoImage;
    @FXML
    private Label infoDimensionsImage;
    @FXML
    private Label infoFormatPixelImage;
    
    private ArrayList uniqueColorsList;
    @FXML
    private Label infoUniqueColorsImage;
    @FXML
    private Slider sliderToBrightness;
    @FXML
    private Label valueBrightness;



       
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    @FXML
    private void handleLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load image in BMP or Netpbm format");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("Netpbm", "*.pbm", "*.pgm", "*.ppm")
        );

        File imgPath = fileChooser.showOpenDialog(null);

        if (imgPath != null) {
            uniqueColorsList = new ArrayList();
            image = new Image("file:" + imgPath.getAbsolutePath());
            setImageSize(image);
            pixelReader = image.getPixelReader();
            writableImage = new WritableImage(imageWidth, imageHeight);
            initPixelsColors();
            setPixelsColors();
            imageView.setPreserveRatio(true);
            imageView.setImage(image);
            labelLoadMessage.setText("Loaded successfully!");
            setPixelsFormatLabel();
            setDimensionsLabel();
            setUniqueColor();
        }
    }
    
    @FXML
    private void convertToNegative(ActionEvent event) {
        if(image != null) {
            if(buttonToNegative.isSelected()) {
                pixelWriter = writableImage.getPixelWriter();
                for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {                
                       Color negative = new Color(
                               1 - colorMatrix[x][y].getRed(), 
                               1 - colorMatrix[x][y].getGreen(),
                               1 - colorMatrix[x][y].getBlue(),
                               1.0
                       );
                       pixelWriter.setColor(x,y,negative);
                   }
                }
               imageView.setImage(writableImage);           
            }else {
                imageView.setImage(image);           
            }
        }
    }
    
    
    
    @FXML
    private void convertToGrayScale(ActionEvent event) {
        if(image != null) {
            if(buttonToGrayScale.isSelected()) {
                pixelWriter = writableImage.getPixelWriter();
                for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {
                       double average = (
                               colorMatrix[x][y].getRed() +
                               colorMatrix[x][y].getGreen() +
                               colorMatrix[x][y].getBlue()
                               )/3;
                       Color grayScale = new Color(average, average, average, 1.0);
                       pixelWriter.setColor(x,y,grayScale);
                   }
                }
               imageView.setImage(writableImage);           
            }else {
                imageView.setImage(image);           
            }
        }
    }
    
    
    @FXML
    private void convertToBlackWhite(ActionEvent event) {        
        if(image != null) {
            if(buttonToBlackWhite.isSelected()) {
                pixelWriter = writableImage.getPixelWriter();
                for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {
                       double average = (
                               colorMatrix[x][y].getRed() +
                               colorMatrix[x][y].getGreen() +
                               colorMatrix[x][y].getBlue()
                               )/3;
                       Color blackwhite;
                        if(average > 0.5) {
                            blackwhite = new Color(1, 1, 1, 1.0);

                        } else {
                            blackwhite = new Color(0, 0, 0, 1.0);
                        }
                        
                       pixelWriter.setColor(x,y,blackwhite);
                   }
                }
               imageView.setImage(writableImage);           
            }else {
                imageView.setImage(image);           
            }
        }
    }
 
    
    private void  setPixelsColors() {
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                colorMatrix[x][y] = pixelReader.getColor(x, y);
                checkUniqueColors(colorMatrix[x][y]);
            }
        }
    }
    
    private void  checkUniqueColors(Color colorPixel) {
        if(!uniqueColorsList.contains(colorPixel)){
            uniqueColorsList.add(colorPixel);
        }
    }
        
    private void  initPixelsColors() {
        colorMatrix = new Color[imageWidth][imageHeight];
    } 
    
    private void  setImageSize(Image image) {
        imageWidth = (int)image.getWidth();
        imageHeight = (int)image.getHeight();
    }
    
    
        
    private void setDimensionsLabel() {
        infoDimensionsImage.setText("Width: " + imageWidth + " pixels. Heigh: " + imageHeight + " pixels.");
    }
    
    private void  setPixelsFormatLabel() {
        PixelFormat.Type type = pixelReader.getPixelFormat​().getType();
        switch(type) {
            case INT_ARGB_PRE:
            case INT_ARGB:
            case BYTE_BGRA_PRE:
            case BYTE_BGRA:
                infoFormatPixelImage.setText("Bits per pixel: 32 bits per pixel.");
                break;
            case BYTE_INDEXED:
                infoFormatPixelImage.setText("The pixel colors are referenced by byte indices stored in the pixel array.");
                break;
            case BYTE_RGB:
                infoFormatPixelImage.setText("Bits per pixel: 24 bits per pixel.");                
        }
    }
 
    private void setUniqueColor() {
        infoUniqueColorsImage.setText("The image contains " + uniqueColorsList.size() + " unique colors.");
    }

    @FXML
    private void handleBrightness(MouseEvent event) {
        if(image != null) {
            double brightnessValue = (double) sliderToBrightness.getValue();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double r = colorMatrix[x][y].getRed() + brightnessValue;
                    double g = colorMatrix[x][y].getGreen() + brightnessValue;
                    double b = colorMatrix[x][y].getBlue() + brightnessValue;

                    if(r > 1)
                        r = 1;
                    if(g > 1)
                        g = 1;
                    if(b > 1)
                        b = 1;
                        
                        
                    
                    Color brightness = new Color(r,g,b,1.0);
                    pixelWriter.setColor(x,y,brightness);
                }
            }
            imageView.setImage(writableImage);
            int text = (int) (brightnessValue * 250);
            valueBrightness.setText(" " + text );           
        }

    }

    
}
