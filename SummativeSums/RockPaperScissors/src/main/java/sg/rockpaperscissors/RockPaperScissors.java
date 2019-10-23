/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.rockpaperscissors;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author blee0
 */
public class RockPaperScissors {
    public static void main(String[] args) {
        mainMenu();
    }
    
    private static void mainMenu() {
        int userChoice = 0;
        
       //User a while loop to control game. Don't use do while loop to ask user to to play game. Waste of memory. 
       //while(userChoice != 2) {
        
        System.out.println(" Shall we play a game of Rock, Paper and Scissors? ");
        System.out.println(" 1. Game on! Let's play Rock, Paper, Scissors!  ");
        System.out.println(" 2. No thanks. ");

        userChoice = getUserChoice(1, 2);


            switch (userChoice) {
            case 1:
                playGame();
                break;
            case 2:
                break;
            default:
                System.out.println("Error: Please enter number options 1 or 2.");
                break;
            } 
            
            
     //while (userChoice != 1 || userChoice != 2); Trying to force user to enter int vs string, DID NOT WORK
      
    }
           
    private static int getUserChoice() {
        return new Scanner(System.in).nextInt();
    }
    
    private static int getUserChoice(int minimum, int maximum) {
       int userChoice = maximum + 1;
       
        while (userChoice < minimum || userChoice > maximum) {
            userChoice = getUserChoice();
            if (userChoice < minimum){
                System.out.print("Please enter a larger number within range " + minimum + "-" + maximum + ": ");
            } else if (userChoice > maximum) {
                System.out.print("Please enter a smaller number within range " + minimum + "-" + maximum + ": ");
            }     
       }
       return userChoice;
    }
    
    private static void playGame() {
        Scanner scn = new Scanner(System.in);
        
        System.out.println("How many rounds do you want to play? (1-10)");
        int numberRounds = getUserChoice(1,10);
        
        int gameCount = 0;
        int userWinnings = 0;
        int pcWinnings = 0;
        int gameTies = 0;
        //1 = rock 
        //2 = paper 
        //3 = scissors      
        
        while (gameCount != numberRounds) {
           
            Random rng = new Random();
            
            
            int computerSelection = rng.nextInt(3) + 1;
           
            
            //System.out.println(" \n  ");
            System.out.println("What is your choice? 1 = Rock, 2 = Paper, 3 = Scissors ");
            int userGameSelection = getUserChoice(1,3);
            
            if (userGameSelection == 1 && computerSelection == 1) {
                System.out.println("Computer selected rock as well, it's a draw!\n");
                gameTies++;
                gameCount++;
            }
            
            else if (userGameSelection == 1 && computerSelection == 2) {  
                System.out.println("Computer selected paper, computer wins!\n");
                pcWinnings++;
                gameCount++;
            }
            
            else if (userGameSelection == 1 && computerSelection == 3) {  
                
                System.out.println("You defeated scissors, you win!\n");
                userWinnings++;
                gameCount++;
            }
            
            else if (userGameSelection == 2 && computerSelection == 1) {
                System.out.println("You defeated rock, you win!\n");
                userWinnings++;
                gameCount++;
            }
            
            else if (userGameSelection == 2 && computerSelection == 2) {
                System.out.println("Computer selected paper as well, it's a draw!\n");
                gameTies++;
                gameCount++;
            }
            
            else if (userGameSelection == 2 && computerSelection == 3) {
                System.out.println("Computer selected scissors, computer wins!\n");
                pcWinnings++;
                gameCount++;
            }
            
            else if (userGameSelection == 3 && computerSelection == 1) {
                System.out.println("Computer selected rock, computer wins!\n");
                pcWinnings++;
                gameCount++;
            }
            
            else if (userGameSelection == 3 && computerSelection == 2) {
                System.out.println("You defeated paper, you win!\n");
                userWinnings++;
                gameCount++;
            }
            
            else if (userGameSelection == 3 && computerSelection == 3) {
                System.out.println("Computer selected scissors as well, it's a draw!\n");
                gameTies++;
                gameCount++;
            }
            else {
                System.out.println(" \n ");
            }   
            
            //if choices are the same, game is a tie, add counter
            
        }
        
        //System.out.println(" \n  ");
        
        System.out.println("You won: " + userWinnings + " time(s)!");
        System.out.println("Computer won " + pcWinnings + " time(s)!");
        System.out.println(gameTies + " is the number of game(s) tied.\n");
        
    
        if (userWinnings > pcWinnings) {
            System.out.println("Nice, you won more games than computer! You win overall!\n ");   
        } else if (userWinnings < pcWinnings) {
            System.out.println("Looks like computer won more games than you, better luck next time.\n");
        } else {
            System.out.println("There are no winners here. Looks like it's a draw.\n");
        }
        
        //System.out.println(" \n  ");
        
        String userLastChoice;
        
        //no recommended to use do while loop 
        do {
            System.out.println("Would you like to play again? (y/n) ");
       
            userLastChoice = scn.nextLine();
            
            if (userLastChoice.equals("y")) {
                playGame();
            } else if (userLastChoice.equals("n")){
                System.out.println("Thanks for playing!");
                System.exit(0);
            } else {
                System.out.println("Please enter (y)es or (n)o.");
            }  
        } while (!(userLastChoice.equals("y")) || !(userLastChoice.equals("n")));
        
        
    }  
}
