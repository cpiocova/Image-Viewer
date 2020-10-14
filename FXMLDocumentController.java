/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piopdi1;

import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @FXML
    private Button btnUndo;
    @FXML
    private Button btnRedo;
    
    private String numberMagic;



       
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    @FXML
    private void handleLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load image in BMP or Netpbm format");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Netpbm", "*.pbm", "*.pgm", "*.ppm"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );

        File imgPath = fileChooser.showOpenDialog(null);

        if (imgPath != null) {
            String fileName = imgPath.getName();           
            String fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());           
            switch(fileFormat) {
                case "bmp":                
                    uniqueColorsList = new ArrayList();
                    bmpLoader(imgPath);
                    break;
                case "pbm":
                case "pgm":
                case "ppm":
                    uniqueColorsList = new ArrayList();
                    netpbmLoader(imgPath);
                    break;
                default:
                    labelLoadMessage.setText("Incompatible Format.");
                    break;
            }
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
 
    
    private void  setPixelsColorsBMP() {
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
      
    private void setImageSize(int width, int height) {
        imageWidth = width;
        imageHeight = height;
        initPixelsColors();
        setDimensionsLabel();
    }
    
        
    private void setDimensionsLabel() {
        infoDimensionsImage.setText("Width: " + imageWidth + "px. Heigh: " + imageHeight + "px.");
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
            int text = (int) (brightnessValue * 100);
            valueBrightness.setText(" " + text + "%");           
        }

    }

    @FXML
    private void handleUndo(ActionEvent event) {
    }

    @FXML
    private void handleRedo(ActionEvent event) {
    }
    
    private void setDimensions(String line) {
        String widthString = "", heightString = "";
        boolean secondEntry = false;
        
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' '){
                secondEntry = true;
                continue;
            }
            if(!secondEntry){
                widthString = widthString + line.charAt(i);
            }else {
                heightString = heightString + line.charAt(i);
            }
        }
        int width = Integer.parseInt(widthString.trim());
        int height = Integer.parseInt(heightString.trim());
        
        setImageSize(width, height);

    }

    private void setNumberMagic(String line) {
        numberMagic = line;
    }

    private void bmpLoader(File path) {
        image = new Image("file:" + path.getAbsolutePath());
        setImageSize((int)image.getWidth(), (int)image.getHeight());
        pixelReader = image.getPixelReader();
        writableImage = new WritableImage(imageWidth, imageHeight);
        setPixelsColorsBMP();
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
        labelLoadMessage.setText("Loaded successfully!");
        setPixelsFormatLabel();
        setUniqueColor();
    }
    
    private void netpbmLoader(File path) {
        File pathAbs = path.getAbsoluteFile();
        Scanner scan;
        try {
            scan = new Scanner(pathAbs);           
            int lineNumber = 1;
            int row = 0;
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(!line.startsWith("#")) {
                    switch (lineNumber) {
                        case 1:
                            setNumberMagic(line);
                            lineNumber++;
                            break;
                        case 2:
                            setDimensions(line);
                            lineNumber++;
                            break;
                        default:
                            line = line.replaceAll("\\s+","");   
                            setPixelsColorsNetbpm(line, row);
                            row++;
                            lineNumber++;
                            break;
                    }
                }
            }
            renderImageNetbpm();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Fail Load");
        }
    }
    
    private void setPixelsColorsNetbpm(String line, int row) {       
        int i = 0;
        int x = i;
        while(i < line.length()) {
            double color = Character.getNumericValue(line.charAt(i));
            colorMatrix[x][row] = new Color(color, color, color, 1.0);
            i++;
            x++;
            if(x == imageWidth) {
                x = 0;
                row++;
            }
            
        }
    }
    
    private void renderImageNetbpm() {
        writableImage = new WritableImage(imageWidth, imageHeight);
        pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < imageHeight; y++) {
           for (int x = 0; x < imageWidth; x++) {
               Color pbm;
               if(colorMatrix[x][y].getRed() == 1){
                   pbm = new Color(0,0,0,1.0);
               } else {
                   pbm = new Color(1,1,1,1.0);
               }
               pixelWriter.setColor(x,y,pbm);
           }
        }
        imageView.setPreserveRatio(true);
        imageView.setImage(writableImage);           
    }


}
