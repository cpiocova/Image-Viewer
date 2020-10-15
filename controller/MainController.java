/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import model.BlankPic;
import model.ImageSize;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;





/**
 *
 * @author Jose Pio
 */
public class MainController implements Initializable {
    
    private BlankPic pic;
    private ImageSize dimensions;

    @FXML
    private Button btnLoadImage;
    @FXML
    private ImageView imageView;
    
    private Image image;
    private Image imageLoaded;
    private WritableImage writableImage;

    
    private PixelReader pixelReader;
    private PixelWriter pixelWriter;
    
    private String fileFormat;
    
    private int imageWidth;
    private int imageHeight;
    private Color [][] colorMatrix;
    private Color [][] originalMatrix;
    @FXML
    private Button buttonToNegative;
    @FXML
    private Button buttonToBlackWhite;
    @FXML
    private Label labelLoadMessage;
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
    private TitledPane labelBrightness;
    
    private String numberMagic;
    @FXML
    private Button btnDefault;
    @FXML
    private TitledPane labelGrayscale;
    @FXML
    private Slider sliderToGrayscale;
    @FXML
    private TitledPane labelContrast;
    @FXML
    private Slider sliderToContrast;
   



       
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }
    
    private void updateColorMatrix() {
        
    }


    @FXML
    private void handleLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load image in BMP or Netpbm format");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Allowed formats (Bmp and Netpbm)", "*.bmp","*.pbm", "*.pgm", "*.ppm"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),               
                new FileChooser.ExtensionFilter("Netpbm", "*.pbm", "*.pgm", "*.ppm")
        );

        File imgPath = fileChooser.showOpenDialog(null);

        if (imgPath != null) {
            pic = new BlankPic();
            pic.setFileFormat(
                    imgPath.getName().substring(imgPath.getName().lastIndexOf(".") + 1,
                    imgPath.getName().length())
            );
            switch(pic.getFileFormat()) {
                case "bmp":                
                    uniqueColorsList = new ArrayList();
                    bmpLoader(imgPath);
                    break;
                case "pbm":
                case "pgm":
                case "ppm":
                    uniqueColorsList = new ArrayList();
                    netpbmLoader(imgPath, pic.getFileFormat());
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
                   colorMatrix[x][y] = negative;
               }
            }
           imageView.setImage(writableImage);
        }
    }
    
 
    
    @FXML
    private void convertToBlackWhite(ActionEvent event) {        
        if(image != null) {
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
                        colorMatrix[x][y] = blackwhite;

                   }
                }
               imageView.setImage(writableImage);           
        }
    }
 
    
    private void  setPixelsColorsBMP() {
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                colorMatrix[x][y] = pixelReader.getColor(x, y);
                originalMatrix[x][y] = colorMatrix[x][y];
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
        originalMatrix = new Color[imageWidth][imageHeight];
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
    private void handleGrayscale(MouseEvent event) {
            if(image != null) {
            restartBrightness();
            restartContrast();                  
            double gv = (double) sliderToGrayscale.getValue();
            pixelWriter = writableImage.getPixelWriter();
            double gred, ggreen, gblue;
                for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {
                       gred = colorMatrix[x][y].getRed();
                       ggreen = colorMatrix[x][y].getGreen();
                       gblue = colorMatrix[x][y].getBlue();
                       double average = (gred + ggreen + gblue)/3;
                       Color grayScale = new Color(
                               gred * (1 - gv) + average * gv,
                               ggreen * (1 - gv) + average * gv,
                               gblue * (1 - gv) + average * gv,
                               1.0);
                        pixelWriter.setColor(x,y,grayScale);
                   }
                }
            imageView.setImage(writableImage);
            int text = (int) (gv * 100);
            labelGrayscale.setText("Grayscale: " + text + "%");           
        }    
    }
    
    private double truncatePixel(double pixel) {
        if(pixel > 1) return pixel = 1;
        if(pixel < 0) return pixel = 0;
        return pixel;
    }

    @FXML
    private void handleBrightness(MouseEvent event) {
        if(image != null) {
            restartGrayscale();
            restartContrast();              
            double brightnessValue = (double) sliderToBrightness.getValue();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double r = truncatePixel(colorMatrix[x][y].getRed() + brightnessValue);
                    double g = truncatePixel(colorMatrix[x][y].getGreen() + brightnessValue);
                    double b = truncatePixel(colorMatrix[x][y].getBlue() + brightnessValue);
                    Color brightness = new Color(r,g,b,1.0);
                    pixelWriter.setColor(x,y,brightness);
                }
            }
            imageView.setImage(writableImage);
            int text = (int) (brightnessValue * 100);
            labelBrightness.setText("Brightness: " + text + "%");           
        }
    }
    
        @FXML
    private void handleContrast(MouseEvent event) {
        if(image != null) {
            restartGrayscale();
            restartBrightness();
            double contrastValue = (double) sliderToContrast.getValue();
            double factor = (1.0156 *(1 + contrastValue)) / (1 * (1.0156 - contrastValue));
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double r = truncatePixel(
                        factor * (colorMatrix[x][y].getRed() - 0.5) + 0.5
                    );
                    double g = truncatePixel(
                        factor * (colorMatrix[x][y].getGreen() - 0.5) + 0.5
                    );
                    double b = truncatePixel(
                        factor * (colorMatrix[x][y].getBlue() - 0.5) + 0.5
                    );
                    Color contrast = new Color(r,g,b,1.0);
                    pixelWriter.setColor(x,y,contrast);
                }
            }
            imageView.setImage(writableImage);
            int text = (int) (contrastValue * 100);
            labelContrast.setText("Contrast: " + text + "%");           
        }
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
        imageLoaded = new Image("file:" + path.getAbsolutePath());
        Image imageLoad = new Image("file:" + path.getAbsolutePath());
        pic.setImageDefault(imageLoad);
        pic.setImageChanging(imageLoad);
        image = imageLoaded;
        dimensions = new ImageSize((int)image.getWidth(), (int)image.getHeight());
//        pic.set
        setImageSize((int)image.getWidth(), (int)image.getHeight());
        pixelReader = image.getPixelReader();
        writableImage = new WritableImage(imageWidth, imageHeight);
        setPixelsColorsBMP();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(347);
        imageView.setFitHeight(532);        
        imageView.setImage(image);
        labelLoadMessage.setText("Loaded successfully!");
        setPixelsFormatLabel();
        setUniqueColor();
        
    }
    
    private void netpbmLoader(File path, String format) {
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
            if(color == 0) {
                color = 1;
            }else {
                color = 0;
            }
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
               if(colorMatrix[x][y].getRed() == 0){
                   pbm = new Color(0,0,0,1.0);
               } else {
                   pbm = new Color(1,1,1,1.0);
               }
               pixelWriter.setColor(x,y,pbm);
           }
        }
        imageLoaded = writableImage;
        image = imageLoaded;
        imageView.setPreserveRatio(true);
        imageView.setImage(writableImage);           
    }
    
    @FXML
        private void sliderContext() {
        pixelReader = writableImage.getPixelReader();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                colorMatrix[x][y] = pixelReader.getColor(x, y);
            }
        }
    }
    
    @FXML
    private void grayscaleContext(ActionEvent event) {
        sliderContext();
        sliderToGrayscale.setValue(0);
        labelGrayscale.setText("Grayscale: 0%");
    }

    @FXML
    private void brightnessContext(ActionEvent event) {
        sliderContext();
        sliderToBrightness.setValue(0);
        labelBrightness.setText("Brightness: 0%");        
    }

    @FXML
    private void contrastContext(ActionEvent event) {
        sliderContext();
        sliderToContrast.setValue(0);        
        labelContrast.setText("Contrast: 0%");        
    }

    

    @FXML
    private void convertToDefault(ActionEvent event) {
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                colorMatrix[x][y] = originalMatrix[x][y];
            }
        } 
        imageView.setImage(imageLoaded);
        restartUI();
    }

    private void restartUI() {
        restartGrayscale();
        restartBrightness();
        restartContrast();        
    }

    private void restartGrayscale() {
        sliderToGrayscale.setValue(0);
        labelGrayscale.setText("Grayscale: 0%");        
    }
    private void restartBrightness() {
        sliderToBrightness.setValue(0);
        labelBrightness.setText("Brightness: 0%");     
    }
    private void restartContrast() {
        sliderToContrast.setValue(0);        
        labelContrast.setText("Contrast: 0%");     
    }  

    @FXML
    private void generateHistogram(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load((getClass().getResource("/view/HistogramView.fxml")));
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    

  
}
