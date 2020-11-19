/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;

/**
 *
 * @author Jose Pio y Giselt Parra
 */
public class DataColor implements Comparable<DataColor>{
    
    private int color;
    private int repetitions;

    public DataColor(int color) {
        this.color = color;
        this.repetitions = 1;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }   
    
    
    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && object instanceof DataColor)
        {
            isEqual = (this.color == ((DataColor) object).color);
        }

        return isEqual;
    }

    @Override
    public int compareTo(DataColor o) {
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
        return this.color;
    }
    

    
    
     
}
