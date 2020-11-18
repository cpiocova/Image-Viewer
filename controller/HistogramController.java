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
import java.util.Collections;
import object.Convolution;
import object.DataColorRGB;
import object.OpenCVUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


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

    private int[] histogramAll;
    private int[] histogramEQ;
    private int[] cdf;
    
    private double[] histogramEQRed;
    private double[] histogramEQGreen;
    private double[] histogramEQBlue;

    
    private double[] histogramRed;
    private double[] histogramBlue;
    private double[] histogramGreen;
    
    private int[] cdfRed;
    private int[] cdfGreen;
    private int[] cdfBlue;
    

    private int cdfMin;
    private int cdfMinRed;
    private int cdfMinGreen;
    private int cdfMinBlue;


    private PixelReader pixelReaderImage;
    private PixelWriter pixelHistogramWriter;

    private ArrayList arrayNormalColor;
    private ArrayList arrayAllColor;
    private ArrayList arrayRedColor;
    private ArrayList arrayGreenColor;
    private ArrayList arrayBlueColor;

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
        arrayRedColor = new ArrayList();
        arrayGreenColor = new ArrayList();
        arrayBlueColor = new ArrayList();
        

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
    
    private void traverseMatrixRGB() {
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color color = pixelReaderImage.getColor(x, y);
                int colorInt = pixelReaderImage.getArgb(x, y);
                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();
                                
                DataColorRGB.checkUniqueColors(r, arrayRedColor);
                DataColorRGB.checkUniqueColors(g, arrayGreenColor);
                DataColorRGB.checkUniqueColors(b, arrayBlueColor);
            }
        }
        
        Collections.sort(arrayRedColor);
        Collections.sort(arrayGreenColor);
        Collections.sort(arrayBlueColor);
        
        
        histogramRed = new double[arrayRedColor.size()];
        cdfRed = new int[arrayRedColor.size()];
        histogramEQRed = new double[arrayRedColor.size()];
        
        histogramBlue = new double[arrayBlueColor.size()];
        cdfBlue = new int[arrayBlueColor.size()];
        histogramEQBlue = new double[arrayBlueColor.size()];
        
        histogramGreen = new double[arrayGreenColor.size()];
        cdfGreen = new int[arrayGreenColor.size()];
        histogramEQGreen = new double[arrayGreenColor.size()];
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
        double ent2 = 1; // En mis calculos ent1 es el negro
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
        Collections.sort(arrayAllColor);
        histogramAll = new int[arrayAllColor.size()];
        cdf = new int[arrayAllColor.size()];
        histogramEQ = new int[arrayAllColor.size()];
    }
    
    private void findCdfMinAll() {
        int min = imageWidth * imageHeight;
        for (int i = 0; i < cdf.length; i++) {
            if (cdf[i] < min && cdf[i] > 0) {
                min = cdf[i];
            }
        }
        cdfMin = min;
    }
    
    private int findCdfMin(int[]cdfColor) {
        int min = imageWidth * imageHeight;
        for (int i = 0; i < cdfColor.length; i++) {
            if (cdfColor[i] < min && cdfColor[i] > 0) {
                min = cdfColor[i];
            }
        }
        return min;
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
    
    private void fillHistogram(ArrayList arrayColor, double[] histogram, int[] cdfColor ) {
        int accumulated = 0;
        for (int i = 0; i < arrayColor.size(); i++) {
            DataColorRGB data = (DataColorRGB) arrayColor.get(i);
            histogram[i] = data.getColor();

            accumulated = accumulated + data.getRepetitions();
            cdfColor[i] = accumulated;
        }
    }
    
        private void generateEQValues(int[] cdfColor, int cdfMinColor, double[] histogram) {
        for (int i = 0; i < cdfColor.length; i++) {
            double expression = (
                    (double) (cdfColor[i] - cdfMinColor) 
                    / (double) (imageWidth * imageHeight - cdfMinColor));
            histogram[i] = expression;
        }
    }
    

    @FXML
    private void equalizeHistogram(ActionEvent event) {
        traverseMatrixRGB();
        fillHistogram(arrayRedColor, histogramRed, cdfRed);
        fillHistogram(arrayGreenColor, histogramGreen, cdfGreen);
        fillHistogram(arrayBlueColor, histogramBlue, cdfBlue);
        cdfMinRed = findCdfMin(cdfRed);
        cdfMinGreen = findCdfMin(cdfGreen);
        cdfMinBlue = findCdfMin(cdfBlue);
        generateEQValues(cdfRed, cdfMinRed, histogramEQRed);
        generateEQValues(cdfGreen, cdfMinGreen, histogramEQGreen);
        generateEQValues(cdfBlue, cdfMinBlue, histogramEQBlue);
    
        WritableImage imageStrecth = new WritableImage(imageWidth, imageHeight); 
        PixelWriter writerStrecth = imageStrecth.getPixelWriter();
        
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int colorInt = pixelReaderImage.getArgb(x, y);
                Color colorRGB = pixelReaderImage.getColor(x, y);
                DataColorRGB dataRed = new DataColorRGB(colorRGB.getRed());
                DataColorRGB dataGreen = new DataColorRGB(colorRGB.getGreen());
                DataColorRGB dataBlue = new DataColorRGB(colorRGB.getBlue());
                
                int indexRed = arrayRedColor.indexOf(dataRed);
                int indexGreen = arrayGreenColor.indexOf(dataGreen);
                int indexBlue = arrayBlueColor.indexOf(dataBlue);
                
                double r = histogramEQRed[indexRed];
                double g = histogramEQGreen[indexGreen];
                double b = histogramEQBlue[indexBlue];
                
                Color colorStrecth = new Color(r,g,b,1.0);

                writerStrecth.setColor(x, y, colorStrecth);
            }
        }
                
        mainController.setEqualizedImage(imageStrecth);
        allColors(mainController, picCopy);

    }
    
    private WritableImage covertImageToWritableImage(Image image) {
        pixelReaderImage = image.getPixelReader();
        WritableImage imageEQ = new WritableImage(imageWidth, imageHeight);
        PixelWriter pW = imageEQ.getPixelWriter();
                      
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color color = pixelReaderImage.getColor(x,y);
                pW.setColor(x,y,color);
            }
        }
        return imageEQ;
    }

    @FXML
    private void equalizeHistogramOpenCV(ActionEvent event) {
        ArrayList<Mat> yCrCb = new ArrayList<Mat>();

        Mat bgr = new Mat();
        if (imagePic instanceof Image) {
            imagePic = covertImageToWritableImage(imagePic);
        }
        
        Mat src = OpenCVUtils.image2Mat((WritableImage) imagePic);
        Imgproc.cvtColor(src, bgr, Imgproc.COLOR_BGRA2BGR);
        Imgproc.cvtColor(bgr, bgr, Imgproc.COLOR_BGR2YCrCb);
        
        Core.split(bgr, yCrCb);
        Imgproc.equalizeHist(yCrCb.get(0), yCrCb.get(0));
	Core.merge(yCrCb, bgr);
	Imgproc.cvtColor(bgr, bgr, Imgproc.COLOR_YCrCb2BGR);      
//        

        imagePic = OpenCVUtils.mat2WritableImage(bgr);
        mainController.setEqualizedImage((WritableImage) imagePic);
        allColors(mainController, picCopy);


    }
    

    
    

}
