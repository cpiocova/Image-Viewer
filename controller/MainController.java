/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import object.BlankPic;
import object.ImageSize;


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
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import object.Convolution;
import object.Pascal;





/**
 *
 * @author Jose Pio
 */
public class MainController implements Initializable {
    
    MainController mainInstanceController;
    
    private BlankPic pic;
    
    private Image image;
    private WritableImage writableImage;
    private WritableImage writableNetpbm;
    private WritableImage zoomWritable;

    
    private PixelReader pixelReader;
    private PixelReader zoomReader;
    
    private PixelWriter pixelWriter;
    private PixelWriter pixelWriterNetpbm;
    private PixelWriter zoomWriter;

    
    private int imageWidth;
    private int imageHeight;
    private int imageWidthOriginal;
    private int imageHeightOriginal;
    
    private int imageX;
    private int imageY;

    
    private Color [][] bufferNetpbm;
    
    private int vw;
    private int vh;

      
    @FXML
    private ImageView imageView;
    
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
    @FXML
    private Button btnLoadImage;
    @FXML
    private TitledPane labelThresholding;
    @FXML
    private Slider sliderToThresholding;
    @FXML
    private Label labelAverageX;
    @FXML
    private Label labelAverageY;
    @FXML
    private Slider sliderToAverageX;
    @FXML
    private Slider sliderToAverageY;
    @FXML
    private Slider sliderToMedianX;
    @FXML
    private Slider sliderToMedianY;
    @FXML
    private Label labelMedianX;
    @FXML
    private Label labelMedianY;
    @FXML
    private Slider sliderToGaussianX;
    @FXML
    private Slider sliderToGaussianY;
    @FXML
    private Label labelGaussianX;
    @FXML
    private Label labelGaussianY;
    @FXML
    private Slider sliderToLaplacianY;
    @FXML
    private Label labelLaplacianX;
    @FXML
    private Label labelLaplacianY;
    @FXML
    private Slider sliderToLaplacianX;
    @FXML
    private Slider sliderToSobelY;
    @FXML
    private Label labelSobelX;
    @FXML
    private Label labelSobelY;
    @FXML
    private Slider sliderToSobelX;
    @FXML
    private Slider sliderToPrewittY;
    @FXML
    private Label labelPrewittX;
    @FXML
    private Label labelPrewittY;
    @FXML
    private Slider sliderToPrewittX;
    @FXML
    private Slider sliderToZoom;
    @FXML
    private Label labelZoom;
    @FXML
    private ToggleGroup zoomMethod;
    
   
    
    final ChangeListener<Number> sliderGaussianX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToGaussianX.valueProperty().set(roundedValue);
        labelGaussianX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderGaussianY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToGaussianY.valueProperty().set(roundedValue);
        labelGaussianY.setText(Integer.toString(roundedValue));
    };

    final ChangeListener<Number> sliderLaplacianX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToLaplacianX.valueProperty().set(roundedValue);
        labelLaplacianX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderLaplacianY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToLaplacianY.valueProperty().set(roundedValue);
        labelLaplacianY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderSobelX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToSobelX.valueProperty().set(roundedValue);
        labelSobelX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderSobelY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToSobelY.valueProperty().set(roundedValue);
        labelSobelY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderPrewittX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToPrewittX.valueProperty().set(roundedValue);
        labelPrewittX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderPrewittY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToPrewittY.valueProperty().set(roundedValue);
        labelPrewittY.setText(Integer.toString(roundedValue));
    };   
    
    final ChangeListener<Number> sliderZoom = (obs, old, val) -> {
        final double roundedValue = Math.round(val.doubleValue() * 10) / 10.0;
        sliderToZoom.valueProperty().set(roundedValue);
        labelZoom.setText(Double.toString(roundedValue) + "x");
    };
    @FXML
    private RadioButton zoomNeighbor;
    @FXML
    private RadioButton zoomInterpolation;
    @FXML
    private ToggleGroup sobelDerivate;
    @FXML
    private ToggleGroup prewittDerivate;
    @FXML
    private CheckBox overflowZoom;
    



       
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainInstanceController = this;
        
        sliderToGaussianX.valueProperty().addListener(sliderGaussianX);
        sliderToGaussianY.valueProperty().addListener(sliderGaussianY);

        sliderToLaplacianX.valueProperty().addListener(sliderLaplacianX);
        sliderToLaplacianY.valueProperty().addListener(sliderLaplacianY);
        
        sliderToSobelX.valueProperty().addListener(sliderSobelX);
        sliderToSobelY.valueProperty().addListener(sliderSobelY);
        
        sliderToPrewittX.valueProperty().addListener(sliderPrewittX);
        sliderToPrewittY.valueProperty().addListener(sliderPrewittY);
        
        sliderToZoom.valueProperty().addListener(sliderZoom);

    }
    
    
    
    private void setImageLoaded(Image image) {
        this.image = image;
    }
    
    private void setViewport() {
        if(imageWidth > 500 || imageHeight > 500) {
            vw = 500;
            vh = 500;
        } else {
        vw = imageWidth;
        vh = imageHeight;  
        }
    }
     
    private void configurationInit() {
        imageView.setImage(pic.getImageOriginal());
        setViewport();
        configurationImageView();        
        labelLoadMessage.setText("Loaded successfully!");
        displayPixelsFormatLabel();
        displayUniqueColor();
        displayDimensionsLabel();
    }
    
    private void configurationImageView() {
        imageView.setPreserveRatio(true);
//        imageView.setFitWidth(vw);
//        imageView.setFitHeight(vh);   
//        imageView.setFitWidth(300);
//        imageView.setFitHeight(300);   
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
            uniqueColorsList = new ArrayList();
            switch(pic.getFileFormat()) {
                case "bmp":                
                    bmpLoader(imgPath);
                    break;
                case "pbm":
                    pbmLoader(imgPath);
                    break;
                case "ppm":
                case "pgm":
                    pgmLoader(imgPath);
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
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            Color [][] current = pic.getColorMatrix();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
               for (int x = 0; x < imageWidth; x++) {                
                   Color negative = new Color(
                           1 - current[x][y].getRed(), 
                           1 - current[x][y].getGreen(),
                           1 - current[x][y].getBlue(),
                           1.0
                   );
                   pixelWriter.setColor(x,y,negative);
                   current[x][y] = negative;
               }
            }
            pic.setColorMatrix(current);
            pic.setImageModified(writableImage);
           imageView.setImage(writableImage);
           configurationImageView();
        }
    }
    
 
    
    @FXML
    private void convertToBlackWhite(ActionEvent event) {        
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            Color [][] current = pic.getColorMatrix();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
               for (int x = 0; x < imageWidth; x++) {
                   double average = (
                           current[x][y].getRed() +
                           current[x][y].getGreen() +
                           current[x][y].getBlue()
                           )/3;
                   Color blackwhite;
                    if(average > 0.5) {
                        blackwhite = new Color(1, 1, 1, 1.0);
                    } else {
                        blackwhite = new Color(0, 0, 0, 1.0);
                    }

                    pixelWriter.setColor(x,y,blackwhite);
                    current[x][y] = blackwhite;
               }
            }
            pic.setColorMatrix(current);
            pic.setImageModified(writableImage);                
            imageView.setImage(writableImage);
            configurationImageView();
        }
    }
 
    
    private void  setColorPixelsBmp() {
        Color [][] current = new Color[imageWidth][imageHeight];
        Color [][] original = new Color[imageWidth][imageHeight];
        
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                original[x][y] = current[x][y] = pixelReader.getColor(x, y);
                addColorsUnique(pixelReader.getArgb(x, y));
            }
        }
        pic.setUniqueColors(uniqueColorsList);        
        pic.setColorMatrix(current);
        pic.setOriginalMatrix(original);
    }
    
    private void  addColorsUnique(int colorPixel) {
        if(!uniqueColorsList.contains(colorPixel)){
            uniqueColorsList.add(colorPixel);
        } 
    }
        
    private void setImageSize(int width, int height) {
        imageWidth = width;
        imageHeight = height;
    }
    
        
    private void displayDimensionsLabel() {
        infoDimensionsImage.setText("Width: " + imageWidth + "px. Heigh: " + imageHeight + "px.");
    }
    
    private void  displayPixelsFormatLabel() {
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
 
    private void displayUniqueColor() {
        infoUniqueColorsImage.setText(
                "The image contains " + uniqueColorsList.size() + " unique colors."
        );
    }
    
    @FXML
    private void handleGrayscale(MouseEvent event) {
            if(image != null) {
            restartBrightness();
            restartContrast();
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            Color [][] current = pic.getColorMatrix();
            double gv = (double) sliderToGrayscale.getValue();
            pixelWriter = writableImage.getPixelWriter();
            double gred, ggreen, gblue;
                for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {
                       gred = current[x][y].getRed();
                       ggreen = current[x][y].getGreen();
                       gblue = current[x][y].getBlue();
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
            configurationImageView();
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
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            Color [][] current = pic.getColorMatrix();            
            double brightnessValue = (double) sliderToBrightness.getValue();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double r = truncatePixel(current[x][y].getRed() + brightnessValue);
                    double g = truncatePixel(current[x][y].getGreen() + brightnessValue);
                    double b = truncatePixel(current[x][y].getBlue() + brightnessValue);
                    Color brightness = new Color(r,g,b,1.0);
                    pixelWriter.setColor(x,y,brightness);
                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
            int text = (int) (brightnessValue * 100);
            labelBrightness.setText("Brightness: " + text + "%");           
        }
    }
    
        @FXML
    private void handleContrast(MouseEvent event) {
        if(image != null) {
            restartGrayscale();
            restartBrightness();
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();      
            Color [][] current = pic.getColorMatrix();                        
            double contrastValue = (double) sliderToContrast.getValue();
            double factor = (1.0156 *(1 + contrastValue)) / (1 * (1.0156 - contrastValue));
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double r = truncatePixel(
                        factor * (current[x][y].getRed() - 0.5) + 0.5
                    );
                    double g = truncatePixel(
                        factor * (current[x][y].getGreen() - 0.5) + 0.5
                    );
                    double b = truncatePixel(
                        factor * (current[x][y].getBlue() - 0.5) + 0.5
                    );
                    Color contrast = new Color(r,g,b,1.0);
                    pixelWriter.setColor(x,y,contrast);
                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
            int text = (int) (contrastValue * 100);
            labelContrast.setText("Contrast: " + text + "%");           
        }
    }

    
    
        @FXML
    private void handleThresholding(MouseEvent event) {
        if(image != null) {
            restartGrayscale();
            restartBrightness();
            restartContrast();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            Color [][] current = pic.getColorMatrix();                        
            double thresholdingValue = (double) sliderToThresholding.getValue();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double average = (
                           current[x][y].getRed() +
                           current[x][y].getGreen() +
                           current[x][y].getBlue()
                           )/3;
                   Color thresholding;
                    if(average > thresholdingValue) {
                        thresholding = new Color(1, 1, 1, 1.0);
                    }else{
                        thresholding = new Color(0, 0, 0, 1.0);
                    }
  
                    pixelWriter.setColor(x,y,thresholding);
                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
            int text = (int) (thresholdingValue * 255);
            labelThresholding.setText("Thresholding: " + text);           
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
        
        pic.setDimensions(new ImageSize(width, height));
        pic.setOriginalDimensions(new ImageSize(width, height));
        setImageSize(width, height);

    }

    private void setNumberMagic(String line) {
        numberMagic = line;
    }

    private void bmpLoader(File path) {
        Image imageLoaded = new Image("file:" + path.getAbsolutePath());
        pic.setImageOriginal(imageLoaded);
        pic.setImageModified(imageLoaded);
        
        setImageLoaded(pic.getImageOriginal());
        
        pic.setDimensions(new ImageSize((int)image.getWidth(), (int)image.getHeight()));
        pic.setOriginalDimensions(new ImageSize((int)image.getWidth(), (int)image.getHeight()));

        
        int width = pic.getDimensions().getWidth();
        int height = pic.getDimensions().getHeight();
        
        setImageSize(width, height);
        
        pixelReader = pic.getImageOriginal().getPixelReader();
        writableImage = new WritableImage(width, height);
        
        setColorPixelsBmp();
        configurationInit();
        
    }
    
    private void pgmLoader(File path) {
        imageX = 0; imageY = 0;
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
                            bufferNetpbm = new Color[imageWidth][imageHeight];
                            lineNumber++;
                            break;
                        case 3:
                            int maxColor = Integer.parseInt(line.trim());
                            pic.setMaxColor(maxColor);
                            lineNumber++;
                            break;
                        default:
                            if("pgm".equals(pic.getFileFormat())) {
                                buildMatrixPgm(line, row);
                            }else{
                                buildMatrixPpm(line, row);
                            }
                            row++;
                            lineNumber++;
                            break;
                    }
                }
            }
            renderImagePgm();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Fail Load");
        }
    }
    
    private void pbmLoader(File path) {
        imageX = 0; imageY = 0;
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
                            bufferNetpbm = new Color[imageWidth][imageHeight];
                            lineNumber++;
                            break;
                        default:
                            line = line.replaceAll("\\s+","");   
                            buildMatrixPbm(line);
                            row++;
                            lineNumber++;
                            break;
                    }
                }
            }
            renderImagePbm();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Fail Load");
        }
    }
    
    private void buildMatrixPbm(String line) {
        int i = 0;
        while(i < line.length()) {
            if(line.charAt(i) == '#') {
                break;
            } else {
                double color = Character.getNumericValue(line.charAt(i));
                if(color == 0) {
                    color = 1;
                }else {
                    color = 0;
                }
                bufferNetpbm[imageX][imageY] = new Color(color, color, color, 1.0);
                i++;
                imageX++;
                if(imageX == imageWidth) {
                    imageX = 0;
                    imageY++;
                }             
            }

        }
    }
    
        private void buildMatrixPgm(String line, int row) {       
        int i = 0;
        int x = 0;
        String numberString = "";
        while(i < line.length()) {
            if(line.charAt(i) == '#') {
                break;
            } else {
                if(line.charAt(i) == ' ' && !"".equals(numberString) && !" ".equals(numberString)){
                    double number = Double.parseDouble(numberString);
                    double color = pic.mapRangePgm(number);
                    bufferNetpbm[x][row] = new Color(color,color,color,1.0);
                    x++;
                    i++;
                    numberString = "";
                } else {
                    numberString = numberString + line.charAt(i);
                    i++;
                    if(i == line.length()) {
                        double number = Double.parseDouble(numberString);
                        double color = pic.mapRangePgm(number);
                        bufferNetpbm[x][row] = new Color(color,color,color,1.0);   
                    }
                }                
            }

        }
    }
        
    private void buildMatrixPpm(String line, int row) {
        int i = 0;
        int x = 0;
//        int lineX = row % imageWidth;
//        int lineY = row / imageWidth;
        double color1 = 0; double color2 = 0; double color3 = 0;
        String numberString = "";
        while(i < line.length()) {
            if(line.charAt(i) == '#') {
                break;
            } else {
                if(line.charAt(i) == ' ' && !"".equals(numberString) && !" ".equals(numberString)){
                    double number = Double.parseDouble(numberString);                
                    switch (x) {
                        case 0:
                            color1 = pic.mapRangePgm(number);
                            x++;
                            break;
                        case 1:
                            color2 = pic.mapRangePgm(number);
                            x++;
                            break;
                        case 2:
                            color3 = pic.mapRangePgm(number);
                            if(imageY < imageHeight && imageX < imageWidth) {
                                bufferNetpbm[imageX][imageY] = new Color(color1,color2,color3,1.0);
                                x = 0;
                            }
                            imageX++;
                            if(imageX == imageWidth) {
                                imageX = 0;
                                imageY++;
                            }
                            break;
                        default:
                            break;
                    }
                    i++;
                    numberString = "";
                } else {
                    numberString = numberString + line.charAt(i);
                    i++;
                    if(i == line.length()) {
                        double number = Double.parseDouble(numberString);
                        color3 = pic.mapRangePgm(number);
                        if(imageY < imageHeight && imageX < imageWidth) {
                            bufferNetpbm[imageX][imageY] = new Color(color1,color2,color3,1.0);
                            x = 0;
                        }
                         imageX++;
                            if(imageX == imageWidth) {
                                imageX = 0;
                                imageY++;
                            }
                    }
                }                
            }

        }
    }
    
    private void setColorPixelsNetbpm() {
        Color [][] current = new Color[imageWidth][imageHeight];
        Color [][] original = new Color[imageWidth][imageHeight];
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                original[x][y] = current[x][y] = bufferNetpbm[x][y];
            }
        }
        pic.setColorMatrix(current);
        pic.setOriginalMatrix(original);    
    }
    
    private void renderImagePbm() {
        int width = pic.getDimensions().getWidth();
        int height = pic.getDimensions().getHeight();
        writableImage = new WritableImage(width, height);
        writableNetpbm = new WritableImage(width, height);
        
        pixelWriter = writableImage.getPixelWriter();
        pixelWriterNetpbm = writableNetpbm.getPixelWriter();

        setColorPixelsNetbpm();
        
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < height; y++) {
           for (int x = 0; x < width; x++) {
               Color pbm;
               if(current[x][y].getRed() == 0){
                   pbm = new Color(0,0,0,1.0);
               } else {
                   pbm = new Color(1,1,1,1.0);
               }
               pixelWriter.setColor(x,y,pbm);
               pixelWriterNetpbm.setColor(x,y,pbm);
           }
        }
                
        pixelReader = writableImage.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                addColorsUnique(pixelReader.getArgb(x, y));
            }
        }
        
        pic.setImageOriginal(writableNetpbm);
        pic.setImageModified(writableNetpbm);
        pic.setUniqueColors(uniqueColorsList);
        setImageLoaded(writableNetpbm);
        configurationInit();       
    }
    
    private void renderImagePgm() {
        writableImage = new WritableImage(imageWidth, imageHeight);
        writableNetpbm = new WritableImage(imageWidth, imageHeight);
        
        pixelWriter = writableImage.getPixelWriter();
        pixelWriterNetpbm = writableNetpbm.getPixelWriter();

        setColorPixelsNetbpm();
        
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < imageHeight; y++) {
           for (int x = 0; x < imageWidth; x++) {
                Color pgm = current[x][y];
                pixelWriter.setColor(x,y,pgm);
                pixelWriterNetpbm.setColor(x,y,pgm);
           }
        }
                
        pixelReader = writableImage.getPixelReader();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                addColorsUnique(pixelReader.getArgb(x, y));
            }
        }
        
        pic.setImageOriginal(writableNetpbm);
        pic.setImageModified(writableNetpbm);
        pic.setUniqueColors(uniqueColorsList);
        setImageLoaded(writableNetpbm);
        configurationInit();       
    }
    
    @FXML
    private void sliderContext() {
        if(image != null) {
            Color [][] current = pic.getColorMatrix();
            pixelReader = writableImage.getPixelReader();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    current[x][y] = pixelReader.getColor(x, y);
                }
            }
            pic.setImageModified(writableImage);
            pic.setColorMatrix(current);        
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
    private void thresholdingContext(ActionEvent event) {
        sliderContext();
        sliderToThresholding.setValue(0.5);        
        labelThresholding.setText("Thresholding: 127");        
    }
    @FXML
    private void filterAverageContext(ActionEvent event) {
        sliderContext();
        labelAverageX.setText("1");
        labelAverageY.setText("1");
        sliderToAverageX.setValue(1);
        sliderToAverageY.setValue(1);
    }
    
    @FXML
    private void filterMedianContext(ActionEvent event) {
        sliderContext();
        labelMedianX.setText("1");
        labelMedianY.setText("1");        
        sliderToMedianX.setValue(1);
        sliderToMedianY.setValue(1);        
    }
    
    @FXML
    private void filterGaussianContext(ActionEvent event) {
        sliderContext();
        labelGaussianX.setText("1");
        labelGaussianY.setText("1");         
        sliderToGaussianX.setValue(1);
        sliderToGaussianY.setValue(1);            
    }

    @FXML
    private void filterLaplacianContext(ActionEvent event) {
        sliderContext();
        labelLaplacianX.setText("1");
        labelLaplacianY.setText("1");          
        sliderToLaplacianX.setValue(1);
        sliderToLaplacianY.setValue(1);        
    }
    
    @FXML
    private void filterSobelContext(ActionEvent event) {
        sliderContext();
        labelSobelX.setText("1");
        labelSobelY.setText("1");   
        sliderToSobelX.setValue(1);
        sliderToSobelY.setValue(1);             
    }
    
    @FXML
    private void filterPrewittContext(ActionEvent event) {
        sliderContext();
        labelPrewittX.setText("1");
        labelPrewittY.setText("1");           
        sliderToPrewittX.setValue(1);
        sliderToPrewittY.setValue(1);   
    }
    
//    @FXML
//    private void zoomContext(ActionEvent event) {
//        scaleContext();
//        sliderToZoom.setValue(1);
//    }
//    
//    private void scaleContext() {
//        if(image != null) {
//            Color [][] current = pic.getColorMatrix();
//            pixelReader = writableImage.getPixelReader();
//            for (int y = 0; y < imageHeight; y++) {
//                for (int x = 0; x < imageWidth; x++) {
//                    current[x][y] = pixelReader.getColor(x, y);
//                }
//            }
//            pic.setImageModified(writableImage);
//            pic.setColorMatrix(current);        
//        }
//    }

    
   

    @FXML
    private void convertToDefault(ActionEvent event) {
        if(image != null) {
            int height = pic.getOriginalDimensions().getHeight();
            int width = pic.getOriginalDimensions().getWidth();
            writableImage = new WritableImage(width, height);
            Color [][] current = new Color[width][height];
            Color [][] original = pic.getOriginalMatrix();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    current[x][y] = original[x][y];
                }
            }
            setImageSize(width, height);
            pic.setDimensions(new ImageSize(width, height));
            pic.setColorMatrix(current);
            pic.setImageModified(pic.getImageOriginal());
            imageView.setImage(pic.getImageOriginal());
            configurationImageView();
            restartUI();    
        }

    }

    private void restartUI() {
        restartGrayscale();
        restartBrightness();
        restartContrast();
        restartThresholding();
        restartAverage();
        restartMedian();
        restartGaussian();
        restartLaplacian();
        restartSobel();
        restartPrewitt();
        restartZoom();
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
    private void restartThresholding() {
        sliderToThresholding.setValue(0.5);        
        labelThresholding.setText("Thresholding: 127");     
    }   
    private void restartAverage() {
        labelAverageX.setText("1");
        labelAverageY.setText("1");
        sliderToAverageX.setValue(1);
        sliderToAverageY.setValue(1);        
    }
    private void restartMedian() {
        labelMedianX.setText("1");
        labelMedianY.setText("1");
        sliderToMedianX.setValue(1);
        sliderToMedianY.setValue(1);        
    }
    private void restartGaussian() {
        labelGaussianX.setText("1");
        labelGaussianY.setText("1");
        sliderToGaussianX.setValue(1);
        sliderToGaussianY.setValue(1);        
    }
    private void restartLaplacian() {
        labelLaplacianX.setText("1");
        labelLaplacianY.setText("1");        
        sliderToLaplacianX.setValue(1);
        sliderToLaplacianY.setValue(1);        
    }      
    
    private void restartSobel() {
        labelSobelX.setText("1");
        labelSobelY.setText("1");            
        sliderToSobelX.setValue(1);
        sliderToSobelY.setValue(1);        
    } 
    
    private void restartPrewitt() {
        labelPrewittX.setText("1");
        labelPrewittY.setText("1");           
        sliderToPrewittX.setValue(1);
        sliderToPrewittY.setValue(1);        
    }
    
    private void restartZoom() {
        sliderToZoom.setValue(1);
    }      
 

    @FXML
    private void generateHistogram(ActionEvent event) {
        if(image != null) {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/HistogramView.fxml")));
                Parent root =  loader.load();
                
                HistogramController histogramInstanceController = (HistogramController)loader.getController();
                
                histogramInstanceController.allColors(pic);
               
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

    @FXML
    private void handleRotate(ActionEvent event) {
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            
            int width = imageHeight;
            int height = imageWidth;
            Color [][] rotate = new Color[imageHeight][imageWidth];
            writableImage = new WritableImage(imageHeight, imageWidth);
            pixelWriter = writableImage.getPixelWriter();
            String direction = ((Button)event.getSource()).getText();
            if(">".equals(direction)) {
                rotate = rotatePositive(width, height, rotate);
            }else {
               rotate = rotateNegative(width, height, rotate);
            }

            setImageSize(width, height);
            pic.setDimensions(new ImageSize(width, height));
            pic.setColorMatrix(rotate);
            pic.setImageModified(writableImage);
            imageView.setImage(writableImage);
            configurationImageView();
                
        }
    
    }

 
    private Color[][] rotateNegative(int width, int height, Color[][] rotate) {
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < width; y++) {
          for (int x = 0; x < height; x++) {                
              rotate[y][x] = current[imageWidth - 1 - x][y];
              pixelWriter.setColor(y,x,rotate[y][x]);
          }
       }
        return rotate;
    }

    private Color[][] rotatePositive(int width, int height, Color[][] rotate) {
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < width; y++) {
          for (int x = 0; x < height; x++) {                
              rotate[y][x] = current[x][imageHeight - 1 - y];
              pixelWriter.setColor(y,x,rotate[y][x]);
          }
       }
        return rotate;
    }



    @FXML
    private void handleAverage(MouseEvent event) {
        int axisX = (int) sliderToAverageX.getValue();
        int axisY = (int) sliderToAverageY.getValue();
        labelAverageX.setText("" + axisX);
        labelAverageY.setText("" + axisY);    
        if(image != null && (axisX + axisY >= 2)) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "average", pic);
                    mc.searchNS(x,y);
                    Color averageColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,averageColor);
                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
        }   
    }

    @FXML
    private void handleMedian(MouseEvent event) {
        int axisX = (int) sliderToMedianX.getValue();
        int axisY = (int) sliderToMedianY.getValue();
        labelMedianX.setText("" + axisX);
        labelMedianY.setText("" + axisY);    
        if(image != null && (axisX + axisY >= 2)) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "median", pic);
                    mc.searchNS(x,y);
                    Color medianColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,medianColor);
                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
        }  
        
    }

    @FXML
    private void handleGaussian(MouseEvent event) {
        int axisX = (int) sliderToGaussianX.getValue();
        int axisY = (int) sliderToGaussianY.getValue();
        if(axisX % 2 == 1 && axisY % 2 == 1){
           labelGaussianX.setText("" + axisX);
           labelGaussianY.setText("" + axisY);    
           if(image != null) {
                restartBrightness();
                restartContrast();
                restartGrayscale();            
                restartThresholding();
                restartAverage();
                restartMedian();
                restartLaplacian();
                restartSobel();
                restartPrewitt();
                restartZoom();               
               pixelWriter = writableImage.getPixelWriter();              
               for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {
                       Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "gaussian", pic);
                       mc.searchNS(x,y);
                       Color gaussianColor = mc.setMatrixConvolution();  
                       pixelWriter.setColor(x,y,gaussianColor);
                   }
               }
               imageView.setImage(writableImage);
               configurationImageView();
           }            
        } 
    }

    @FXML
    private void handleLaplacian(MouseEvent event) {
        int axisX = (int) sliderToLaplacianX.getValue();
        int axisY = (int) sliderToLaplacianY.getValue();
        labelLaplacianX.setText("" + axisX);
        labelLaplacianY.setText("" + axisY);    
        if(image != null && (axisY + axisX > 2)) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            pixelWriter = writableImage.getPixelWriter();              
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "laplacian", pic);
                    mc.searchNS(x,y);
                    Color laplacianColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,laplacianColor);
                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
        }        
    }

    @FXML
    private void handleSobel() {
        int sobelIndex = sobelDerivate.getToggles().indexOf(sobelDerivate.getSelectedToggle());
        int axisX = (int) sliderToSobelX.getValue();
        int axisY = (int) sliderToSobelY.getValue();
        labelSobelX.setText("" + axisX);
        labelSobelY.setText("" + axisY);    
        if(image != null && axisY + axisX > 2) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartPrewitt();
            restartZoom();
            pixelWriter = writableImage.getPixelWriter();
            int aX = (int) Math.round(axisX / 2.0 + 0.5);
            int aY = (int) Math.round(axisY / 2.0 + 0.5);
            for (int y = aY; y < imageHeight - aY; y++) {
                for (int x = aX; x < imageWidth - aX; x++) {
                    if(sobelIndex == 0){ // Derivate in X
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "sobelX", pic);
                        mc.searchNS(x,y);
                        Color sobelColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,sobelColor);                    
                    } else { //Derivate in Y
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "sobelY", pic);
                        mc.searchNS(x,y);
                        Color sobelColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,sobelColor);                      
                    }

                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
        }
    }

    @FXML
    private void handlePrewitt() {
        int prewittIndex = prewittDerivate.getToggles().indexOf(prewittDerivate.getSelectedToggle());
        int axisX = (int) sliderToPrewittX.getValue();
        int axisY = (int) sliderToPrewittY.getValue();
        labelPrewittX.setText("" + axisX);
        labelPrewittY.setText("" + axisY);    
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartZoom();
            pixelWriter = writableImage.getPixelWriter();
            int aX = (int) Math.round(axisX / 2.0 + 0.5);
            int aY = (int) Math.round(axisY / 2.0 + 0.5);
            for (int y = aY; y < imageHeight - aY; y++) {
                for (int x = aX; x < imageWidth - aX; x++) {
                    if(prewittIndex == 0) { //Derivate in X
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "prewittX", pic);
                        mc.searchNS(x,y);
                        Color prewittColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,prewittColor);                        
                    } else {
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "prewittY", pic);
                        mc.searchNS(x,y);
                        Color prewittColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,prewittColor);
                    }
                }
            }
            imageView.setImage(writableImage);
            configurationImageView();
        }        
        
    }

    @FXML
    private void handleRoberts(ActionEvent event) {
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartZoom();
            Color [][] current = pic.getColorMatrix();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(2, 2, imageWidth, imageHeight, "roberts", pic);
                    mc.searchRoberts(x,y);
                    Color robertsColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,robertsColor);
                    current[x][y] = robertsColor;
                }
            }
            pic.setColorMatrix(current);
            pic.setImageModified(writableImage);
            imageView.setImage(writableImage);
            configurationImageView();
        }          
        
    }

    @FXML
    private void handleZoom() {
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            int zoomButton = zoomMethod.getToggles().indexOf(zoomMethod.getSelectedToggle());
            // 0  Neighbor   -    1  Interpolation
            double zoomValue = sliderToZoom.getValue();

            if(zoomButton == 0) {
                zoomNeighbor(zoomValue);
            } else {
                zoomInterpolation(zoomValue);
            }  
        }
    }
    
    private int[] getZoomDimension(double zoomValue) {
        int [] newDimen = new int[2];
        boolean overflow = overflowZoom.isSelected();
        if(overflow || zoomValue < 1) {
            newDimen[0] = (int) Math.round(imageWidth * zoomValue);
            newDimen[1] = (int) Math.round(imageHeight * zoomValue);
        } else{
            newDimen[0] = imageWidth;
            newDimen[1] = imageHeight;  
        }
        return newDimen;
    } 
    
    private void zoomNeighbor(double zoomValue) {
        int [] zoomDimensions = getZoomDimension(zoomValue);
        int width = zoomDimensions[0];
        int height = zoomDimensions[1];
        Color [][] current = pic.getColorMatrix();
        zoomWritable = new WritableImage(width, height);
        zoomWriter = zoomWritable.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(x < width - zoomValue - 1 && y < height - zoomValue - 1){
                    int zX = (int) (x/zoomValue);
                    int zY = (int) (y/zoomValue);
                    Color zoomColor = current[zX][zY];
                    zoomWriter.setColor(x,y,zoomColor);
                }
            }
        }
        imageView.setImage(zoomWritable);
        configurationImageView();      
            
    }
        
    private void zoomInterpolation(double zoomValue) {
//        System.out.println("Zoom Interpolation " + zoomValue);
        int [] zoomDimensions = getZoomDimension(zoomValue);
        int width = zoomDimensions[0];
        int height = zoomDimensions[1];
        Color [][] current = pic.getColorMatrix();
        zoomWritable = new WritableImage(width, height);
        zoomWriter = zoomWritable.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
//                    if(x < width - zoomValue) {
//                       // Interpolar solo vertical  
//                    }else if(y < height - zoomValue) {
//                       // Interpolar solo horizontal 
//                    }
                if(x < width - zoomValue - 1  && y < height - zoomValue - 1){
                    int i = (int) Math.floor((double)x/zoomValue);
                    int d = i + 1;
                    int s = (int) Math.floor((double)y/zoomValue);
                    int r = s + 1;
                    double a = ((double) x / zoomValue) - i;
                    double b = ((double) y / zoomValue) - s;

                    Color bilinear1 = bilinearCoefficient(i,s, 1-a, 1-b, current);
                    Color bilinear2 = bilinearCoefficient(d,s, a, 1-b, current);
                    Color bilinear3 = bilinearCoefficient(i,r, 1-a, b, current);
                    Color bilinear4 = bilinearCoefficient(d,r, a, b, current);

                    Color zoomColor = addColors(bilinear1,bilinear2,bilinear3,bilinear4);
                    zoomWriter.setColor(x,y,zoomColor);
                }
            }
        }
        imageView.setImage(zoomWritable);
        configurationImageView();  
    }
    
    private Color bilinearCoefficient(int x, int y, double a, double b, Color[][] current) {
        double red = current[x][y].getRed();
        double green = current[x][y].getGreen();
        double blue = current[x][y].getBlue();
        
        red = a*b*red;
        green = a*b*green;
        blue = a*b*blue;
        
        if(red > 1) red = 1;
        if(green > 1) green = 1;
        if(blue > 1) blue = 1;
        
        if(red < 0) red = 0;
        if(green < 0) green = 0;
        if(blue < 0) blue = 0;
        
        return new Color(red, green, blue, 1.0);
    }
      
    private Color addColors(Color a, Color b, Color c, Color d) {
        double aRed = a.getRed();
        double aGreen = a.getGreen();
        double aBlue = a.getBlue();
        
        double bRed = b.getRed();
        double bGreen = b.getGreen();
        double bBlue = b.getBlue();
        
        double cRed = c.getRed();
        double cGreen = c.getGreen();
        double cBlue = c.getBlue();
        
        double dRed = d.getRed();
        double dGreen = d.getGreen();
        double dBlue = d.getBlue();
        
        double red = aRed + bRed + cRed + dRed;
        double green = aGreen + bGreen + cGreen + dGreen;
        double blue = aBlue + bBlue + cBlue + dBlue;
        
        if(red > 1) red = 1;
        if(green > 1) green = 1;
        if(blue > 1) blue = 1;
        
        if(red < 0) red = 0;
        if(green < 0) green = 0;
        if(blue < 0) blue = 0;
        
        return new Color(red,green,blue,1.0);
    }


  
}
