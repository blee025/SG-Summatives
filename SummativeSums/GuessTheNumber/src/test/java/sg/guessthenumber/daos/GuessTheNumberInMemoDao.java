/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.daos;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;

/**
 *
 * @author blee0
 */
@Repository
@Profile("ServiceTest")
public class GuessTheNumberInMemoDao implements GuessTheNumberDao {

    List<Game> allGames = new ArrayList<>();
    List<Round> allRounds = new ArrayList<>();

    public GuessTheNumberInMemoDao() {

        Game firstGame = new Game();
        firstGame.setGameId(1);
        firstGame.setTargetNumber(9285);
        firstGame.setGameFinished(false);

        Game secondGame = new Game();
        secondGame.setGameId(2);
        secondGame.setTargetNumber(7628);
        secondGame.setGameFinished(true);

        Game thirdGame = new Game();
        thirdGame.setGameId(3);
        thirdGame.setTargetNumber(1245);
        thirdGame.setGameFinished(false);

        allGames.add(firstGame);
        allGames.add(secondGame);
        allGames.add(thirdGame);

        Round roundOneGameOne = new Round();
        roundOneGameOne.setRoundId(1);
        roundOneGameOne.setGameId(1);
        roundOneGameOne.setGuessNumber(1234);
        roundOneGameOne.setPartialMatchCount(0);
        roundOneGameOne.setExactMatchCount(1);
        roundOneGameOne.setTimeStamp("10/01/2019 10:20:34 PM");

        Round roundTwoGameOne = new Round();
        roundTwoGameOne.setRoundId(2);
        roundTwoGameOne.setGameId(1);
        roundTwoGameOne.setGuessNumber(3479);
        roundTwoGameOne.setPartialMatchCount(1);
        roundTwoGameOne.setExactMatchCount(0);
        roundTwoGameOne.setTimeStamp("10/01/2019 10:25:34 PM");

        Round roundOneGameTwo = new Round();
        roundOneGameTwo.setRoundId(3);
        roundOneGameTwo.setGameId(2);
        roundOneGameTwo.setGuessNumber(5649);
        roundOneGameTwo.setPartialMatchCount(0);
        roundOneGameTwo.setExactMatchCount(1);
        roundOneGameTwo.setTimeStamp("10/01/2019 10:27:34 PM");

        Round roundTwoGameTwo = new Round();
        roundTwoGameTwo.setRoundId(4);
        roundTwoGameTwo.setGameId(2);
        roundTwoGameTwo.setGuessNumber(3172);
        roundTwoGameTwo.setPartialMatchCount(2);
        roundTwoGameTwo.setExactMatchCount(0);
        roundTwoGameTwo.setTimeStamp("10/01/2019 10:28:34 PM");

        Round roundOneGameThree = new Round();
        roundOneGameThree.setRoundId(5);
        roundOneGameThree.setGameId(3);
        roundOneGameThree.setGuessNumber(7256);
        roundOneGameThree.setPartialMatchCount(1);
        roundOneGameThree.setExactMatchCount(1);
        roundOneGameThree.setTimeStamp("10/01/2019 10:30:34 PM");

        Round roundTwoGameThree = new Round();
        roundTwoGameThree.setRoundId(6);
        roundTwoGameThree.setGameId(3);
        roundTwoGameThree.setGuessNumber(9625);
        roundTwoGameThree.setPartialMatchCount(1);
        roundTwoGameThree.setExactMatchCount(1);
        roundTwoGameThree.setTimeStamp("10/01/2019 10:033:34 PM");

        allRounds.add(roundOneGameOne);
        allRounds.add(roundTwoGameOne);

        allRounds.add(roundOneGameTwo);
        allRounds.add(roundTwoGameTwo);

        allRounds.add(roundOneGameThree);
        allRounds.add(roundTwoGameThree);

    }

    @Override
    public int createNewGame(int targetNumber) {
        Game newGame = new Game();
        newGame.setTargetNumber(targetNumber);
        newGame.setGameFinished(false);

        int largestId = 0;
        for (Game toCheck : allGames) {
            if (toCheck.getGameId() > largestId) {
                largestId = toCheck.getGameId();
            }
        }

        int newGameId = largestId + 1;

        newGame.setGameId(newGameId);

        allGames.add(newGame);

        return newGameId;

    }

    @Override
    public Round createNewRoundByGameId(Round newRound) {
       
        int maxId = 0;
        for (Round toCheck : allRounds) {
            if (toCheck.getRoundId() > maxId) {
                maxId = toCheck.getRoundId();
            }
        }
        
        newRound.setRoundId(maxId + 1);
        
        allRounds.add(newRound);
            
        return newRound;

    }

    @Override
    public Game getGameById(int id) throws InvalidGameIdDaoException {
        Game toReturn = null;

        for (Game toCheck : allGames) {
            if (toCheck.getGameId() == id) {
                toReturn = toCheck;
            }    
        }
        
        if (toReturn == null){
            throw new InvalidGameIdDaoException("Invalid Id!");
        }
        
        return toReturn;
    }

    @Override
    public List<Round> getRoundsByGameId(int id) {
        
        List<Round> allRoundsByGameId = new ArrayList();
        
        for (int i = 0; i < allRounds.size(); i++) {
            
            Round toCheck = allRounds.get(allRounds.size() - 1 - i);

            if (toCheck.getGameId() == id) {
                allRoundsByGameId.add(toCheck);
            }
        }
        
        return allRoundsByGameId; 
                
                
        
    }

    @Override
    public List<Game> getAllGames() {
        return allGames;
    }

    @Override
    public void editGame(int gameId) throws GuessTheNumberDaoException {
        
        for (Game toCheck : allGames) {
            if (toCheck.getGameId() == gameId){
                toCheck.setGameFinished(true);
            }
        }   
    }

    @Override
    public void deleteAllGamesAndRounds() {
    }

}
