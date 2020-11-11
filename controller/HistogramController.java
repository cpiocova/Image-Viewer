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
import javafx.event.ActionEvent;
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

    MainController mainController;

    @FXML
    private ImageView imageView;
    private WritableImage writableHistogramImage;

    private Image imagePic;
    private BlankPic picCopy;

    private int imageWidth;
    private int imageHeight;
    private int viewWidth;
    private int viewHeight;

    private int maxRepeat;
    private int minRepeat;

    private int[] histogramNormal;
    private int[] histogramAll;
    private int[] histogramEQ;
    private int[] cdf;

    private int cdfMin;

    //Borrar si no uso ---
    private int repeatColorDarker;
    private int repeatColorLighter;
    private int colorDarker;
    private int colorLighter;
    // Borrar si no uso ---

    private PixelReader pixelReaderImage;
    private PixelWriter pixelHistogramWriter;

    private ArrayList arrayNormalColor;
    private ArrayList arrayAllColor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void allColors(MainController mainInstanceController, BlankPic pic) {

        mainController = mainInstanceController;
        picCopy = pic;

        imageWidth = pic.getDimensions().getWidth();
        imageHeight = pic.getDimensions().getHeight();
        
        arrayNormalColor = new ArrayList();

        if (pic.getImageModified() instanceof Image) {
            imagePic = pic.getImageModified();
        } else {
            imagePic = (WritableImage) pic.getImageModified();
        }

        pixelReaderImage = imagePic.getPixelReader();

        traverseMatrixNormalColors();
        int arr[] = calculateMaxMinRepeats();
        maxRepeat = arr[0];
        minRepeat = arr[1];

        viewWidth = 256;
        viewHeight = 200;

        writableHistogramImage = new WritableImage(viewWidth, viewHeight);
        pixelHistogramWriter = writableHistogramImage.getPixelWriter();

        drawHistogram();

        imageView.setImage(writableHistogramImage);
        imageView.setFitWidth(viewWidth);
        imageView.setFitHeight(viewHeight);
        imageView.setPreserveRatio(true);

    }

    private void testArrayHistogram() {
        for (int i = 0; i < histogramAll.length; i++) {
            System.out.println("El valor de " + i + " es: " + histogramAll[i]);
        }
    }

    private void drawHistogram() {
        Color color = new Color(0, 0, 0, 1.0);
        for (int counter = 0; counter < arrayNormalColor.size(); counter++) {
            DataColor data = (DataColor) arrayNormalColor.get(counter);
            int x = data.getColor();
            int repeats = data.getRepetitions();
            int normalizedY = mappingRangeRepeats(repeats);
            for (int start = 1; start < normalizedY; start++) {
                int y = viewHeight - start;
                pixelHistogramWriter.setColor(x, y, color);
            }
        }

    }

    private void traverseMatrixNormalColors() {
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int color = mappingRangeColor(pixelReaderImage.getArgb(x, y));
                checkUniqueNormalColors(color, arrayNormalColor);
            }
        }
    }
    
    private void checkUniqueNormalColors(int colorPixel, ArrayList arrCol) {
        DataColor data = new DataColor(colorPixel);
        if (!arrCol.contains(data)) {
            arrCol.add(data);
        } else {
            int index = arrCol.indexOf(data);
            DataColor newData = (DataColor) arrCol.get(index);
            int repeat = newData.getRepetitions() + 1;
            newData.setRepetitions(repeat);
            arrCol.set(index, newData);

        }
    }

    private int mappingRangeColor(int x) {
        double ent1 = -16777216;
        double ent2 = -1; // En mis calculos ent1 es el negro
        double ret1 = 0;
        double ret2 = 255;
        return (int) (((ret2 - ret1) / (ent2 - ent1)) * (x - ent2) + ret2);
    }
    
    private int mappingRangeVar(int x) {
        double ent1 = -16777216;
        double ent2 = -1; // En mis calculos ent1 es el negro
        double ret1 = 0;
        double ret2 = arrayAllColor.size() - 1;
        return (int) (((ret2 - ret1) / (ent2 - ent1)) * (x - ent2) + ret2);
    }

    private int mappingRangeColorInvert(double x) {
        double ent1 = 0;
        double ent2 = 255; // En mis calculos ent1 es el negro
        double ret1 = -16777216;
        double ret2 = -1;
        return (int) (((ret2 - ret1) / (ent2 - ent1)) * (x - ent2) + ret2);
    }

    private double calculateDifference(double difference) {
        double number;
        if (difference >= 199) {
            number = 5;
        } else if (difference >= 160) {
            number = 30;
        } else if (difference >= 120) {
            number = 60;
        } else if (difference >= 80) {
            number = 90;
        } else if (difference >= 40) {
            number = 120;
        } else if (difference >= 20) {
            number = 150;
        } else if (difference >= 10) {
            number = 180;
        } else {
            number = 199;
        }

        return number;
    }

    private int mappingRangeRepeats(int x) {
        double ent1 = minRepeat;
        double ent2 = maxRepeat;
        double difference = calculateDifference(ent2 - ent1);
        double ret1 = difference;
        double ret2 = 200;
        double mod = maxRepeat - minRepeat;
        if (mod == 0) {
            mod = 1;
        }
        return (int) (((ret2 - ret1) / mod) * (x - ent2) + ret2);
    }

    private int[] calculateMaxMinRepeats() {
        DataColor data = (DataColor) arrayNormalColor.get(0);
        int max = data.getRepetitions();
        int min = data.getRepetitions();
        for (int counter = 0; counter < arrayNormalColor.size(); counter++) {
            DataColor ndata = (DataColor) arrayNormalColor.get(counter);
            int repeat = ndata.getRepetitions();
            if (repeat > max) {
                max = repeat;
            }
            if (repeat < min) {
                min = repeat;
            }
        }
        return new int[]{max, min};
    }

    private void findDarker() {
        for (int i = 0; i < histogramAll.length; i++) {
            if (histogramAll[i] >= 1) {
                colorDarker = i;
                repeatColorDarker = histogramAll[i];
                break;
            }
        }
    }

    private void findLighter() {
        for (int i = histogramAll.length - 1; i >= 0; i--) {
            if (histogramAll[i] >= 1) {
                colorLighter = i;
                repeatColorLighter = histogramAll[i];
                break;
            }
        }
    }



    private void generateEQValues() {
        for (int i = 0; i < cdf.length; i++) {
            double expression = (
                    (double) (cdf[i] - cdfMin) 
                    / (double) (imageWidth * imageHeight - cdfMin)
                    ) *  -16777216;
            histogramEQ[i] = (int) (expression);
//            System.out.println(histogramEQ[i]);

        }
    }

    private Color intToColor(int value) {
        double r = (value & 0xFF0000) >> 16;
        double g = (value & 0xFF00) >> 8;
        double b = (value & 0xFF);
        return new Color(r / 255, g / 255, b / 255, 1.0);
    }
    
    private void checkUniqueColors(int colorPixel, ArrayList arrCol) {
        DataColor data = new DataColor(colorPixel);
        if (!arrCol.contains(data)) {
            arrCol.add(data);
        } else {
            int index = arrCol.indexOf(data);
            DataColor newData = (DataColor) arrCol.get(index);
            int repeat = newData.getRepetitions() + 1;
            newData.setRepetitions(repeat);
            arrCol.set(index, newData);

        }
    }
    
    private void initMatrixEQ() {
        arrayAllColor = new ArrayList();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int color = pixelReaderImage.getArgb(x, y);
                checkUniqueColors(color, arrayAllColor);
            }
        }
        
        histogramAll = new int[arrayAllColor.size()];
        cdf = new int[arrayAllColor.size()];
        histogramEQ = new int[arrayAllColor.size()];
    }
    
    private void findCdfMin() {
        int min = imageWidth * imageHeight;
        for (int i = 0; i < cdf.length; i++) {
            if (cdf[i] < min && cdf[i] > 0) {
                min = cdf[i];
            }
        }
        cdfMin = min;
    }
    
    private void fillHistogramAll() {
        int accumulated = 0;
        for (int i = 0; i < arrayAllColor.size(); i++) {
            DataColor data = (DataColor) arrayAllColor.get(i);
            histogramAll[i] = data.getColor();
            
            accumulated = accumulated + data.getRepetitions();
            cdf[i] = accumulated;
//            System.out.println(cdf[i]);
        }
    }
    

    @FXML
    private void equalizeHistogram(ActionEvent event) {
        initMatrixEQ();
        fillHistogramAll();
        findCdfMin();
        generateEQValues();
    
        WritableImage imageStrecth = new WritableImage(imageWidth, imageHeight); 
        PixelWriter writerStrecth = imageStrecth.getPixelWriter();
        
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int color = pixelReaderImage.getArgb(x, y);
                DataColor data = new DataColor(color);
                int index = arrayAllColor.indexOf(data);
                int valueStrecth = histogramEQ[index];
//                Color colorStrecth = intToColor(valueStrecth);
                writerStrecth.setArgb(x, y, valueStrecth);
            }
        }
                
        mainController.setEqualizedImage(imageStrecth);
        allColors(mainController, picCopy);

    }
    
    

}
