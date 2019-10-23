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
public class Round {
    
    private int roundId;
    private int gameId;
    private int guessNumber;
    private int partialMatchCount;
    private int exactMatchCount;
    private String timeStamp;


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
     * @return the guessNumber
     */
    public int getGuessNumber() {
        return guessNumber;
    }

    /**
     * @param guessNumber the guessNumber to set
     */
    public void setGuessNumber(int guessNumber) {
        this.guessNumber = guessNumber;
    }

    /**
     * @return the partialMatchCount
     */
    public int getPartialMatchCount() {
        return partialMatchCount;
    }

    /**
     * @param partialMatchCount the partialMatchCount to set
     */
    public void setPartialMatchCount(int partialMatchCount) {
        this.partialMatchCount = partialMatchCount;
    }

    /**
     * @return the exactMatchCount
     */
    public int getExactMatchCount() {
        return exactMatchCount;
    }

    /**
     * @param exactMatchCount the exactMatchCount to set
     */
    public void setExactMatchCount(int exactMatchCount) {
        this.exactMatchCount = exactMatchCount;
    }

    /**
     * @return the roundId
     */
    public int getRoundId() {
        return roundId;
    }

    /**
     * @param roundId the roundId to set
     */
    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    /**
     * @return the timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
           
    
}
