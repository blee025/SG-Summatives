/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.daos;

import java.util.List;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;

/**
 *
 * @author blee0
 */
public interface GuessTheNumberDao {

    public int createNewGame(int targetNumber);

    public Round createNewRoundByGameId(Round newRound) throws GuessTheNumberDaoException, InvalidTimeStampDaoException;

    public Game getGameById(int id) throws InvalidGameIdDaoException;

    public List<Round> getRoundsByGameId(int id) throws InvalidGameIdDaoException;

    public List<Game> getAllGames() throws GuessTheNumberDaoException;

    public void editGame(int gameId) throws GuessTheNumberDaoException, InvalidGameIdDaoException;

    public void deleteAllGamesAndRounds();
    
}
