/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author Jose Pio y Giselt Parra
 */
public class MedianCut {
    
    public static int partition(Color arr[], int begin, int end, char channel) {
        int i = (begin-1);
        
        if (channel == 'R'){
            double pivot = arr[end].getRed();
            for (int j = begin; j < end; j++) {
                if (arr[j].getRed() <= pivot) {
                    i++;
                    Color swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        else if (channel == 'G'){
            double pivot = arr[end].getGreen();
            for (int j = begin; j < end; j++) {
                if (arr[j].getGreen() <= pivot) {
                    i++;
                    Color swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        else if (channel == 'B'){
            double pivot = arr[end].getBlue();
            for (int j = begin; j < end; j++) {
                if (arr[j].getBlue() <= pivot) {
                    i++;
                    Color swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        
        Color swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
        return i+1;
    }
    
    public static void quickSort(Color arr[], int begin, int end,  char channel) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, channel);

            quickSort(arr, begin, partitionIndex-1, channel);
            quickSort(arr, partitionIndex+1, end, channel);
        }
    }
    
    public static HashMap <Color,Color> create_Palette(Color ma[], int depth, String option, int k) { 
        int middle = (int) (ma.length)/2;
        
        if (depth == k || Math.pow(2,k) >= ma.length){
            double av_red = ma[middle].getRed();
            double av_green = ma[middle].getGreen();
            double av_blue  = ma[middle].getBlue();                

            if (option == "Media"){
                av_red = 0;
                av_green = 0;
                av_blue = 0;
                for(int i=0; i<ma.length; i++){
                    av_red += ma[i].getRed();
                    av_green += ma[i].getGreen();
                    av_blue += ma[i].getBlue();
                }
                av_red = av_red/ma.length;
                av_green = av_green/ma.length;
                av_blue = av_blue/ma.length;
            }
            //Asignar diccionario
            HashMap <Color,Color> palette_color = new HashMap<Color,Color>();
             for(int i=0; i<ma.length; i++){
                Color currentColor = new Color(ma[i].getRed(),ma[i].getGreen(),ma[i].getBlue(), 1.0); 
                Color newColor = new Color(av_red,av_green,av_blue, 1.0);               
                palette_color.put(currentColor,newColor); 
            }   
            return palette_color;
        }
        
        //1st mitad
        
        HashMap <Color,Color>h1 = create_Palette(Arrays.copyOfRange(ma, 0, middle),depth+1,option,k);
        //2nd mitad
        HashMap <Color,Color> h2 = create_Palette(Arrays.copyOfRange(ma, middle, ma.length),depth+1,option,k);
  
        h2.forEach((Color key, Color value) -> h1.merge(key, value, (v1, v2) -> v1));
        return h1;
    }
    
    public static HashMap <Color,Color> create_Palette_Mediana(Color ma[], int depth, String option, int k) { 
        int middle = (int) (ma.length)/2;
        
        if (depth == k ){
            double av_red = ma[middle].getRed();
            double av_green = ma[middle].getGreen();
            double av_blue  = ma[middle].getBlue();                

            //Asignar diccionario
            HashMap <Color,Color> palette_color = new HashMap<Color,Color>();
             for(int i=0; i<ma.length; i++){
                Color currentColor = new Color(ma[i].getRed(),ma[i].getGreen(),ma[i].getBlue(), 1.0); 
                Color newColor = new Color(av_red,av_green,av_blue, 1.0);               
                palette_color.put(currentColor,newColor); 
            }   
            return palette_color;
        }
        
        //1st mitad
        
        HashMap <Color,Color>h1 = create_Palette(Arrays.copyOfRange(ma, 0, middle),depth+1,option,k);
        //2nd mitad
        HashMap <Color,Color> h2 = create_Palette(Arrays.copyOfRange(ma, middle, ma.length),depth+1,option,k);
  
        h2.forEach((Color key, Color value) -> h1.merge(key, value, (v1, v2) -> v1));
        return h1;
    }

    
}

