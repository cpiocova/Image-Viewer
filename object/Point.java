/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;


import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author Jose Pio
 */
public class Point {
    private int posX;
    private int posY;
    private int indexRow;
    private int indexColumn;
    
// El indice es para ver a cual posicion xy pertenece a la matriz de convolucion, para saber por cual coeficiente hay que multiplicarlo
   public Point(int posX, int posY, int indexRow, int indexColumn) {
       this.posX = posX;
       this.posY = posY;
       this.indexRow = indexRow;
       this.indexColumn = indexColumn;       
   }

    Point() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getIndexRow() {
        return indexRow;
    }

    public int getIndexColumn() {
        return indexColumn;
    }
   
   

}
