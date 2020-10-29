/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author Jose Pio
 */
public class Pascal {
    
    
    public static int pascalValue(int i, int j) {
        if (j == 0) {
            return 1;
        } else if (j == i) {
            return 1;
        } else {
            return pascalValue(i - 1, j - 1) + pascalValue(i - 1, j);
        }
    } 
    
    
    public static int[] generateVector(int linePascal) {
        int arr[] = new int[linePascal];
        for (int i = 0; i < linePascal; i++) {
            if (i == linePascal-1) {
                for (int j = 0; j <= i; j++) {
                    arr[j] = pascalValue(i, j);
//                    System.out.print(pascalValue(i, j) + " ");
                }
            }
        }
        return arr;
    }

   
}
