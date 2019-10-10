/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.services;

import java.util.List;
import sg.guessthenumber.daos.InvalidTimeStampDaoException;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;

/**
 *
 * @author blee0
 */
public interface GuessTheNumberService {

    public int createNewGame() throws GuessTheNumberServiceException;

    public Round createNewRoundByGameId(Round newRound) throws InvalidGameIdServiceException, GameCompletedException, GuessTheNumberServiceException, InvalidGuessServiceException;

    public Game getGameById(int id) throws InvalidGameIdServiceException, GuessTheNumberServiceException;

    public List<Round> getRoundsbyGameId(int id) throws InvalidGameIdServiceException, GuessTheNumberServiceException;

    public List<Game> getAllGames() throws GuessTheNumberServiceException;
    
}
