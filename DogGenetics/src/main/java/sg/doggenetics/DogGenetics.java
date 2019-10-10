/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.doggenetics;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author blee0
 */
public class DogGenetics {
    public static void main(String[] args) {
        
        Scanner scn = new Scanner(System.in);
        
        Random rng = new Random();  
        
        System.out.print("What is your dog's name? ");
        String dogName = scn.nextLine();
        
        System.out.println("Well then, I have this highly reliable report on " + dogName + "'s prestigious background right here.\n");
    
        // A% + B% + C% + D% + E% = 100% 
        // E% = 100 - A% - B% - C% - D%
        //Randomize Traits A-E with numbers, calculate sum, calculate percentages. 
        
        int traitRandomA = randomNum();
        int traitRandomB = randomNum();
        int traitRandomC = randomNum();
        int traitRandomD = randomNum();
        int traitRandomE = randomNum();
       
        int traitRandomSum = traitRandomA + traitRandomB + traitRandomC + traitRandomD + traitRandomE;
    
        int traitRandomAPercent = percentage(traitRandomA, traitRandomSum);
        int traitRandomBPercent = percentage(traitRandomB, traitRandomSum);
        int traitRandomCPercent = percentage(traitRandomC, traitRandomSum);
        int traitRandomDPercent = percentage(traitRandomD, traitRandomSum);
        
        int traitRandomEPercent = 100 - (traitRandomAPercent +  traitRandomBPercent +  traitRandomCPercent +  traitRandomDPercent); 
        
        
        System.out.println(dogName + " is: \n");
        
        //System.out.println("\n  ");
        
        System.out.println(traitRandomAPercent + "% St. Bernard");
        System.out.println(traitRandomBPercent + "% Chihuahua");
        System.out.println(traitRandomCPercent + "% Dramatic RedNosed Asian Pug");
        System.out.println(traitRandomDPercent + "% Common Cur");
        System.out.println(traitRandomEPercent + "% King Doberman\n");
        
        //System.out.println(" \n  ");
        
        System.out.println("Wow, that's QUITE the dog!");

    }
    
    private static int randomNum() {
        Random rng = new Random();
        int randomNumber = rng.nextInt(1000);
        return randomNumber;
    }
    
    private static int percentage(int traitRandom, int traitRandomSum) {
        
        int traitPercent = ( (traitRandom * 100) / traitRandomSum);
        return traitPercent;
    }
}
