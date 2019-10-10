/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.summativesums;

/**
 *
 * @author blee0
 */
public class SummativeSums {
    public static void main(String[] args) {
        
        //int [][] mainArray = new int[][];
        
        int [][] mainArray = {
            {1, 90, -33, -55, 67, -16, 28, -55, 15},   
            {999, -60, -77, 14, 160, 301},
            {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, -99},
        };        
        
        for(int i =0; i < mainArray.length; i++) {
            int arraySum = 0;
            for (int j = 0; j < mainArray[i].length; j++) {
                arraySum += mainArray[i][j]; 
            }
            System.out.println("# " + (i + 1) + " Array Sum: " + arraySum);           
        }
        
        int [] mainArrayTest = {
            1, 90, -33, -55, 67, -16, 28, -55, 15 
        };
        System.out.println(sums(mainArrayTest));
    }
    
    //If I wanted to use method for single array instead, call this method! 
    public static int sums(int [] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        } return sum;
    }
}
        
       
        //System.out.println(arraySum);
    
        
       
        //call in method to run sum
        //Print out each array index 
    
//    static void adder(int [][] toAdd) {
//        int arraysum = 0;
//        
//        //for(int i =0; i < toAdd.length; i++) {
//        for (int j = 0; j < mainArray[i].length; j++) {
//            arraySum += mainArray[i][j]; 
//            System.out.println(mainArray[i][j]);
//        }
    
    
            
   

