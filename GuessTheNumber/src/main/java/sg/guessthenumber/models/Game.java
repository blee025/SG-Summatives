/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.models;

/**
 *
 * @author blee0
 */
public class Game {
    
    private int gameId;
    private int targetNumber; 
    private boolean gameFinished;

    
    /**
     * @return the targetNumber
     */
    public int getTargetNumber() {
        return targetNumber;
    }

    /**
     * @param targetNumber the targetNumber to set
     */
    public void setTargetNumber(int targetNumber) {
        this.targetNumber = targetNumber;
    }

    /**
     * @param gameFinished the gameFinished to set
     */
    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    /**
     * @return the gameId
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the gameFinished
     */
    public boolean getGameFinished() {
        return gameFinished;
    }

    
}
