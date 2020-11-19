/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Jose Pio y Giselt Parra
 */
public class StructuringElem {
    
    private int type;
    private int width;
    private int height;
    private int posX;
    private int posY;
    private Mat structuringElem;
    
    public StructuringElem() {
        structuringElem = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        
    }

    public Mat getStructuringElem() {
        return structuringElem;
    }
    
    public void setElem(int type, int width, int height, int posX, int posY) {
        int elementType;
        Point pivot = new Point(posX,posY);
        Size size = new Size(width, height);
        
        switch(type) {
            case 0:
                elementType = Imgproc.MORPH_RECT;
                structuringElem = Imgproc.getStructuringElement(elementType, size, pivot);
                break;
            case 1:
                elementType = Imgproc.MORPH_CROSS;
                structuringElem = Imgproc.getStructuringElement(elementType, size, pivot);
                break;
            case 2:
                elementType = Imgproc.MORPH_ELLIPSE;
                structuringElem = Imgproc.getStructuringElement(elementType, size, pivot);
                break;
        }
    }
        
    
}
