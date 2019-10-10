/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.daos;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import sg.guessthenumber.TestApplicationConfiguration;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;

/**
 *
 * @author blee0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles(profiles = "test")
public class GuessTheNumberDatabaseDaoTest {

    @Autowired
    GuessTheNumberDao toTest;

    public GuessTheNumberDatabaseDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //delete all
        toTest.deleteAllGamesAndRounds();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createNewGame method, of class GuessTheNumberDatabaseDao.
     */
    @Test
    public void CreateNewGameAndGetAllGamesGoldenPath() {

        try {
            int targetNumberOne = 9285;
            int targetNumberTwo = 7628;
            int targetNumberThree = 1245;

            int firstGameId = toTest.createNewGame(targetNumberOne);
            int secondGameId = toTest.createNewGame(targetNumberTwo);
            int thirdGameId = toTest.createNewGame(targetNumberThree);

            List<Game> allGames = toTest.getAllGames();

            assertEquals(3, allGames.size());

            Game firstInList = allGames.get(0);

            assertEquals(firstGameId, firstInList.getGameId());
            assertEquals(9285, firstInList.getTargetNumber());
            assertEquals(false, firstInList.getGameFinished());

            Game secondInList = allGames.get(1);

            assertEquals(secondGameId, secondInList.getGameId());
            assertEquals(7628, secondInList.getTargetNumber());
            assertEquals(false, secondInList.getGameFinished());

            Game addedGame = allGames.get(2);

            assertEquals(thirdGameId, addedGame.getGameId());
            assertEquals(1245, addedGame.getTargetNumber());
            assertEquals(false, addedGame.getGameFinished());

        } catch (GuessTheNumberDaoException ex) {
            fail();
        }

    }
    
   

    /**
     * Test of createNewRoundByGameId method, of class
     * GuessTheNumberDatabaseDao.
     */
    @Test
    public void CreateNewRoundByGameIdAndGetRoundsByGameIdGoldenPath() {

        try {
            int targetNumber = 9285;

            int newGameId = toTest.createNewGame(targetNumber);

            Round firstRound = new Round();
            firstRound.setGameId(newGameId);
            firstRound.setGuessNumber(1234);
            firstRound.setPartialMatchCount(0);
            firstRound.setExactMatchCount(1);
            firstRound.setTimeStamp("10/01/2019 10:25:34 PM");

            Round secondRound = new Round();
            secondRound.setGameId(newGameId);
            secondRound.setGuessNumber(6789);
            secondRound.setPartialMatchCount(1);
            secondRound.setExactMatchCount(1);
            secondRound.setTimeStamp("10/01/2019 10:26:34 PM");

            Round thirdRound = new Round();
            thirdRound.setGameId(newGameId);
            thirdRound.setGuessNumber(3467);
            thirdRound.setPartialMatchCount(0);
            thirdRound.setExactMatchCount(0);
            thirdRound.setTimeStamp("10/01/2019 10:27:34 PM");

            Round firstRoundReturned = toTest.createNewRoundByGameId(firstRound);
            Round secondRoundReturned = toTest.createNewRoundByGameId(secondRound);
            Round thirdRoundReturned = toTest.createNewRoundByGameId(thirdRound);

            List<Round> allRoundsFirstGame = toTest.getRoundsByGameId(newGameId);

            assertEquals(3, allRoundsFirstGame.size());

            Round firstToCheck = allRoundsFirstGame.get(0);
            //assertEquals(firstRoundReturned.getRoundId(), firstToCheck.getRoundId());
            assertEquals(newGameId, firstToCheck.getGameId());
            assertEquals(3467, firstToCheck.getGuessNumber());
            assertEquals(0, firstToCheck.getPartialMatchCount());
            assertEquals(0, firstToCheck.getExactMatchCount());
            assertEquals("10/01/2019 10:27:34 PM", firstToCheck.getTimeStamp());

            Round secondToCheck = allRoundsFirstGame.get(1);
            //assertEquals(secondRoundReturned.getRoundId(), secondToCheck.getRoundId());
            assertEquals(newGameId, secondToCheck.getGameId());
            assertEquals(6789, secondToCheck.getGuessNumber());
            assertEquals(1, secondToCheck.getPartialMatchCount());
            assertEquals(1, secondToCheck.getExactMatchCount());
            assertEquals("10/01/2019 10:26:34 PM", secondToCheck.getTimeStamp());

            Round lastToCheck = allRoundsFirstGame.get(2);
            //assertEquals(thirdRoundReturned.getRoundId(), lastToCheck.getRoundId());
            assertEquals(newGameId, lastToCheck.getGameId());
            assertEquals(1234, lastToCheck.getGuessNumber());
            assertEquals(0, lastToCheck.getPartialMatchCount());
            assertEquals(1, lastToCheck.getExactMatchCount());
            assertEquals("10/01/2019 10:25:34 PM", lastToCheck.getTimeStamp());

        } catch (GuessTheNumberDaoException | InvalidGameIdDaoException | InvalidTimeStampDaoException ex) {
            fail();
        }
    }

    @Test
    public void CreateNewRoundByGameIdNullRound() {
        try {

            int targetNumber = 9285;

            int newGameId = toTest.createNewGame(targetNumber);

            Round newRound = null;

            toTest.createNewRoundByGameId(newRound);
            fail();
        } catch (InvalidTimeStampDaoException ex) {
            fail();
        } catch (GuessTheNumberDaoException ex) {
        }
    }

    @Test
    public void CreateNewRoundByGameIdBlankTimeStamp() {

        try {
            int targetNumber = 9285;

            int newGameId = toTest.createNewGame(targetNumber);

            Round firstRound = new Round();
            firstRound.setGameId(newGameId);
            firstRound.setGuessNumber(1234);
            firstRound.setPartialMatchCount(0);
            firstRound.setExactMatchCount(1);
            firstRound.setTimeStamp("");

            Round firstRoundReturned = toTest.createNewRoundByGameId(firstRound);
            fail();
        } catch (GuessTheNumberDaoException ex) {
            fail();
        } catch (InvalidTimeStampDaoException ex) {
        }

    }


    /**
     * Test of editGame method, of class GuessTheNumberDatabaseDao.
     */
    @Test
    public void EditGameAndGetGameByIdGoldenPath() {

        try {
            int targetNumber = 9285;

            int addedGameId = toTest.createNewGame(targetNumber);

            Game edited = new Game();

            edited.setGameId(addedGameId);
            edited.setTargetNumber(targetNumber);
            edited.setGameFinished(true);

            toTest.editGame(addedGameId);

            Game toCheck = toTest.getGameById(addedGameId);
            assertEquals(addedGameId, toCheck.getGameId());
            assertEquals(9285, toCheck.getTargetNumber());
            assertEquals(true, toCheck.getGameFinished());
        } catch (GuessTheNumberDaoException | InvalidGameIdDaoException ex) {
            fail();
        }
    }

    @Test
    public void EditInvalidGameId() {

        try {
            int targetNumber = 9285;

            int newGameId = toTest.createNewGame(targetNumber);

            Round firstRound = new Round();
            firstRound.setGameId(newGameId);
            firstRound.setGuessNumber(1234);
            firstRound.setPartialMatchCount(0);
            firstRound.setExactMatchCount(1);
            firstRound.setTimeStamp("10/01/2019 10:25:34 PM");

            Round firstRoundReturned = toTest.createNewRoundByGameId(firstRound);

            int gameId = 233;

            toTest.editGame(gameId);
            fail();
        } catch (GuessTheNumberDaoException | InvalidTimeStampDaoException ex) {
            fail();
        } catch (InvalidGameIdDaoException ex) {
        }
    }

    /**
     * Test of getRoundsByGameId method, of class GuessTheNumberDatabaseDao.
     */
    //@Test
    public void GetRoundsByinvalidGameId() {
        try {

            int targetNumber = 9285;

            int newGameId = toTest.createNewGame(targetNumber);

            Round firstRound = new Round();
            firstRound.setGameId(newGameId);
            firstRound.setGuessNumber(1234);
            firstRound.setPartialMatchCount(0);
            firstRound.setExactMatchCount(1);
            firstRound.setTimeStamp("10/01/2019 10:25:34 PM");

            Round firstRoundReturned = toTest.createNewRoundByGameId(firstRound);

            int gameId = 789;

            toTest.getRoundsByGameId(gameId);
            fail();
        } catch (GuessTheNumberDaoException | InvalidTimeStampDaoException ex) {
            fail();
        } catch (InvalidGameIdDaoException ex) {
        }
    }

    /**
     * Test of getGameById method, of class GuessTheNumberDatabaseDao.
     */
    //@Test
    public void GetGameByInvlaidId() {
        try {
            int targetNumber = 9285;

            int newGameId = toTest.createNewGame(targetNumber);

            int gameId = 474;

            toTest.getGameById(gameId);
            fail();
        } catch (InvalidGameIdDaoException ex) {
        }
    }

}
