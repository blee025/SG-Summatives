/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.healthyhearts;

import java.util.Scanner;

/**
 *
 * @author blee0
 */
public class HealthyHearts {
    public static void main(String[] args) {
        double age = 0;
        double heartRate = 0;
        double hrZoneBottom = 0;
        double hrZoneTop = 0;
        //int intHrZoneTop = 0;
        //int intHrZoneBottom = 0;
       
                
                
        Scanner inputReader = new Scanner(System.in);
        
        System.out.println("What is your age?");
        age = inputReader.nextInt();
        
        heartRate = 220 - age;
        hrZoneBottom = Math.round(heartRate * 0.5);
        hrZoneTop = Math.round(heartRate * 0.85);
       
        
        System.out.println("Your maximum heart rate should be " + (int)heartRate + " beats per minute.");
        System.out.println("Your target HR Zone is " + (int)hrZoneBottom + " - " + (int)hrZoneTop + " beats per minute.");
    }
}
