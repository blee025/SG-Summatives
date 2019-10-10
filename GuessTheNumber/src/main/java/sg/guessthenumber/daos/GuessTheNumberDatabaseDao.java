/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;

/**
 *
 * @author blee0
 */
@Repository
@Profile({"production", "test"})
public class GuessTheNumberDatabaseDao implements GuessTheNumberDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public int createNewGame(int targetNumber) {

        String insert = "Insert into Games(TargetNumber) Values (?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setInt(1, targetNumber);

                return toReturn;
            }
        };

        template.update(psc, holder);
        int toReturn = holder.getKey().intValue();
        //toAdd.setGameId(generatedId);

        return toReturn;

    }

    @Override
    public Round createNewRoundByGameId(Round toAdd) throws GuessTheNumberDaoException, InvalidTimeStampDaoException {
        
        if (toAdd == null) {
            throw new GuessTheNumberDaoException("Round cannot be saved to Database.");
        }
   
        if (toAdd.getTimeStamp().isEmpty()) {
            throw new InvalidTimeStampDaoException("No timestamp found.");
        }

        String insert = "Insert into Rounds(GameId, GuessNumber, PartialMatchCount, ExactMatchCount, TimeStamp) Values (?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                
                //toReturn.setInt(1, toAdd.getRoundId());
                toReturn.setInt(1, toAdd.getGameId());
                toReturn.setInt(2, toAdd.getGuessNumber());
                toReturn.setInt(3, toAdd.getPartialMatchCount());
                toReturn.setInt(4, toAdd.getExactMatchCount());
                toReturn.setString(5, toAdd.getTimeStamp());

                return toReturn;
            }
        };
        
       template.update(psc, holder); 
       
       int generatedId = holder.getKey().intValue();
       toAdd.setRoundId(generatedId);
       
       return toAdd;
    }  
    
    @Override
    public void editGame(int gameId) throws GuessTheNumberDaoException, InvalidGameIdDaoException {
        
        String updateStatement = "Update Games G Set G.GameFinished = True Where G.Id = ?";
        
        int rowsAffected = template.update(
            updateStatement,
            gameId
        );
        
        if( rowsAffected == 0 ){
            throw new InvalidGameIdDaoException( "Could not edit order with id = " + gameId );
        }
        
        if( rowsAffected > 1 ){
            throw new GuessTheNumberDaoException( "ERROR: OrderId IS NOT UNIQUE FOR orders TABLE." );

        }
    
    }

    @Override
    public List<Round> getRoundsByGameId(int id) throws InvalidGameIdDaoException {
        List<Round> toReturn = new ArrayList<>();
        String query = "Select * From Rounds R Inner Join Games G ON R.GameId = G.Id Where G.Id = ? Order by TimeStamp DESC";
        //may need to order by asc or desc timestamp
        toReturn = template.query(query, new RoundMapper(), id);
        
        if (toReturn == null) {
            throw new InvalidGameIdDaoException("Cannot get list of rounds by gameId " + id);
        }

        return toReturn;
    }

    @Override
    public List<Game> getAllGames() throws GuessTheNumberDaoException {
        
        String query = "Select * From Games";       
        List<Game> toReturn = template.query(query, new GameMapper());
        
        if (toReturn == null) {
            throw new GuessTheNumberDaoException("Cannot get list of games.");
        }
        
        return toReturn;
    }

    @Override
    public Game getGameById(int id) throws InvalidGameIdDaoException {
        
        String query = "Select * From Games G Where G.Id = ?";
        Game toReturn = null;
        try {
            toReturn = template.queryForObject(query, new GameMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            //expecting query to return object, if no object, query returns EmptyResultDataAccessException
        }

        if (toReturn == null) {
            throw new InvalidGameIdDaoException("Cannot get game by gameId " + id);
        }

        return toReturn;
    }

    @Override
    public void deleteAllGamesAndRounds() {
        
        String deleteRelationships = "DELETE FROM Rounds Where Id > 0;";
        String deleteStatement = "DELETE FROM Games Where Id > 0;";
        template.update(deleteRelationships);
        template.update(deleteStatement);
        
    }


    private class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet results, int rowNum) throws SQLException {
            Game toReturn = new Game();
            toReturn.setGameId(results.getInt("Id"));
            toReturn.setTargetNumber(results.getInt("TargetNumber"));
            toReturn.setGameFinished(results.getBoolean("GameFinished"));
            return toReturn;
        }
    }

    private class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet results, int rowNum) throws SQLException {
            Round toReturn = new Round();
            toReturn.setRoundId(results.getInt("Id"));
            toReturn.setGameId(results.getInt("GameId"));
            toReturn.setGuessNumber(results.getInt("GuessNumber"));
            toReturn.setPartialMatchCount(results.getInt("PartialMatchCount"));
            toReturn.setExactMatchCount(results.getInt("ExactMatchCount"));
            toReturn.setTimeStamp(results.getString("TimeStamp"));
            return toReturn;
        }

    }

}


