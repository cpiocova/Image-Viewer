/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import static java.lang.Double.doubleToLongBits;
import java.util.ArrayList;

/**
 *
 * @author Jose Pio y Giselt Parra
 */
public class DataColorRGB implements Comparable<DataColorRGB>{
    
    private double color;
    private int repetitions;
    private int colorInt;

    public DataColorRGB(double color) {
        this.color = color;
        this.repetitions = 1;
    }

    public double getColor() {
        return color;
    }

    public void setColor(double color) {
        this.color = color;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
    
    public static void checkUniqueColors(double colorPixel, ArrayList arrCol) {
        DataColorRGB data = new DataColorRGB(colorPixel);
        if (!arrCol.contains(data)) {
            arrCol.add(data);
        } else {
            int index = arrCol.indexOf(data);
            DataColorRGB newData = (DataColorRGB) arrCol.get(index);
            int repeat = newData.getRepetitions() + 1;
            newData.setRepetitions(repeat);
            arrCol.set(index, newData);
        }
    }
    
    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && object instanceof DataColorRGB)
        {
            isEqual = (this.color == ((DataColorRGB) object).color);
        }

        return isEqual;
    }

    @Override
    public int compareTo(DataColorRGB o) {
        if (this.color < o.getColor()) {
            return -1;
        }
        if (this.color > o.getColor()) {
            return 1;
        }
        return 0;
    }
    
    
    @Override
    public int hashCode() {
        return ((int) Math.round(this.color * 255));
    }
    

    
    
     
}
