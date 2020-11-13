/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import com.sun.javafx.util.Utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javax.imageio.ImageIO;
import org.opencv.core.Core;

import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.TermCriteria;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Jose Pio
 */
public class OpenCVUtils {
    
    public static Image mat2Image(Mat frame) { 
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
    
    public static WritableImage mat2WritableImage(Mat mat){
      MatOfByte matOfByte = new MatOfByte();
      Imgcodecs.imencode(".jpg", mat, matOfByte);

      byte[] byteArray = matOfByte.toArray();
      InputStream in = new ByteArrayInputStream(byteArray);
      BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(OpenCVUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
      
      WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);
      return writableImage;
   }
    

    
    
    public static Mat image2Mat(WritableImage image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];

        PixelReader reader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, buffer);
        return mat;
    }
    
    
    public static Mat image2Mat2( Image image) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        return bufferedImage2Mat( bImage);
    }
    
    
    public static Mat bufferedImage2Mat(BufferedImage in) {
        Mat out;
        byte[] data;
        int r, g, b;
        int height = in.getHeight();
        int width = in.getWidth();
        if(in.getType() == BufferedImage.TYPE_INT_RGB || in.getType() == BufferedImage.TYPE_INT_ARGB){
            out = new Mat(height, width, CvType.CV_8UC3);
            data = new byte[height * width * (int)out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
            for(int i = 0; i < dataBuff.length; i++){
                data[i*3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i*3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
            }
        }else{
            out = new Mat(height, width, CvType.CV_8UC1);
            data = new byte[height * width * (int)out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
            for(int i = 0; i < dataBuff.length; i++) {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); 
            }
        }
        out.put(0, 0, data);
        return out;
     } 
    
    
    public static Mat kmeans(Mat entry, int k) {
        
        Imgproc.cvtColor(entry, entry, Imgproc.COLOR_BGRA2BGR);
        
        int n = entry.cols() * entry.rows();

        Mat samples = entry.reshape(1, n);

        Mat samples32f = new Mat();

        samples.convertTo(samples32f, CvType.CV_32F, 1.0 / 255.0);

        Mat labels = new Mat();

        TermCriteria criteria = new TermCriteria(TermCriteria.COUNT + TermCriteria.EPS, 100, 0.5);

        Mat centers = new Mat();

        Core.kmeans(samples32f, k, labels, criteria, 1, Core.KMEANS_PP_CENTERS, centers);
        
        centers.convertTo(centers, CvType.CV_8UC1, 255.0);
        centers.reshape(3);



        Mat dst = entry.clone();
//        Mat dst = new Mat();

        int rows = 0;

        for(int y = 0; y < entry.rows(); y++) {

            for(int x = 0; x < entry.cols(); x++) {

                int label = (int)labels.get(rows,0)[0];

                int r = (int)centers.get(label, 2)[0];

                int g = (int)centers.get(label, 1)[0];

                int b = (int)centers.get(label, 0)[0];

                dst.put(y, x, b,g,r);
                rows++;
            }
        }

        return dst;

    }
    
    
}
