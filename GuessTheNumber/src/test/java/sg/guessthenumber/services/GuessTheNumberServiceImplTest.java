/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.services;

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
import sg.guessthenumber.daos.GuessTheNumberInMemoDao;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;

/**
 *
 * @author blee0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles(profiles = "ServiceTest")
public class GuessTheNumberServiceImplTest {
    
    //@Autowired
    //GuessTheNumberService toTest;
    
    public GuessTheNumberServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createNewGame method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void CreateNewGameGoldenPath() {
      GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());  
        try {
            int newGameId = toTest.createNewGame();
            
            Game newGameCreated = toTest.getGameById(newGameId);

            List<Game> allGames = toTest.getAllGames();

            assertEquals(4, allGames.size());
            assertEquals(newGameId, allGames.get(3).getGameId());
            assertEquals(newGameCreated.getTargetNumber(), allGames.get(3).getTargetNumber());
            assertEquals(newGameCreated.getGameFinished(), allGames.get(3).getGameFinished());
            
        } catch (InvalidGameIdServiceException | GuessTheNumberServiceException ex) {
            fail();
        }
        
    }

    /**
     * Test of createNewRoundByGameId method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void CreateNewRoundByGameIdGoldenPath(){
      GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());  
        
        try {
            //actual: 9285
            Round firstGuess = new Round();
            firstGuess.setGameId(1);
            firstGuess.setGuessNumber(2368);
          
            Round addedFirstGuess = toTest.createNewRoundByGameId(firstGuess);
            
            Round secondGuess = new Round();
            secondGuess.setGameId(1);
            secondGuess.setGuessNumber(9582);
            
            Round addedSecondGuess = toTest.createNewRoundByGameId(secondGuess);
            
            
            Round thirdGuess = new Round();
            thirdGuess.setGameId(1);
            thirdGuess.setGuessNumber(859);
            
            Round addedThirdGuess = toTest.createNewRoundByGameId(thirdGuess);
            
            Round fourthGuess = new Round();
            fourthGuess.setGameId(1);
            fourthGuess.setGuessNumber(9285);
            
            Round addedFourthGuess = toTest.createNewRoundByGameId(fourthGuess);
            
            List<Round> allroundsGameOne = toTest.getRoundsbyGameId(1);
            
            assertEquals(6, allroundsGameOne.size());
            
            assertEquals(addedFirstGuess.getRoundId(), allroundsGameOne.get(3).getRoundId());
            assertEquals(addedFirstGuess.getGameId(), allroundsGameOne.get(3).getGameId());
            assertEquals(addedFirstGuess.getGuessNumber(), allroundsGameOne.get(3).getGuessNumber());
            assertEquals(2, allroundsGameOne.get(3).getPartialMatchCount());
            assertEquals(0, allroundsGameOne.get(3).getExactMatchCount());
            
            assertEquals(addedSecondGuess.getRoundId(), allroundsGameOne.get(2).getRoundId());
            assertEquals(addedSecondGuess.getGameId(), allroundsGameOne.get(2).getGameId());
            assertEquals(addedSecondGuess.getGuessNumber(), allroundsGameOne.get(2).getGuessNumber());
            assertEquals(2, allroundsGameOne.get(2).getPartialMatchCount());
            assertEquals(2, allroundsGameOne.get(2).getExactMatchCount());
            
            assertEquals(addedThirdGuess.getRoundId(), allroundsGameOne.get(1).getRoundId());
            assertEquals(addedThirdGuess.getGameId(), allroundsGameOne.get(1).getGameId());
            assertEquals(addedThirdGuess.getGuessNumber(), allroundsGameOne.get(1).getGuessNumber());
            assertEquals(3, allroundsGameOne.get(1).getPartialMatchCount());
            assertEquals(0, allroundsGameOne.get(1).getExactMatchCount());
            
            assertEquals(addedFourthGuess.getRoundId(), allroundsGameOne.get(0).getRoundId());
            assertEquals(addedFourthGuess.getGameId(), allroundsGameOne.get(0).getGameId());
            assertEquals(addedFourthGuess.getGuessNumber(), allroundsGameOne.get(0).getGuessNumber());
            assertEquals(0, allroundsGameOne.get(0).getPartialMatchCount());
            assertEquals(4, allroundsGameOne.get(0).getExactMatchCount());
            
            //impossible to test for timestamp
               
        } catch (InvalidGameIdServiceException | GameCompletedException | GuessTheNumberServiceException | InvalidGuessServiceException ex) {
            fail();
        }
        
    }

    @Test
    public void CreateNewRoundByGameIdBadId(){
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            Round firstGuess = new Round();
            firstGuess.setGameId(8);
            firstGuess.setGuessNumber(2368);
            
            Round toCheck = toTest.createNewRoundByGameId(firstGuess);
            fail();
        } catch (GameCompletedException | GuessTheNumberServiceException | InvalidGuessServiceException ex) {
            fail();
        } catch (InvalidGameIdServiceException ex) {           
        } 
    }
    
    @Test
    public void CreateNewRoundByGameIdBadGuess(){
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            Round firstGuess = new Round();
            firstGuess.setGameId(1);
            firstGuess.setGuessNumber(2234);
            
            toTest.createNewRoundByGameId(firstGuess);
            fail();
        } catch (InvalidGameIdServiceException | GameCompletedException | GuessTheNumberServiceException ex) {
           fail();
        } catch (InvalidGuessServiceException ex) {
        }  
    }
    
    
    @Test
    public void CreateNewRoundByGameIdCompletedGame(){
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            Round firstGuess = new Round();
            firstGuess.setGameId(2);
            firstGuess.setGuessNumber(2234);
            
            toTest.createNewRoundByGameId(firstGuess);
            fail();
        } catch (InvalidGameIdServiceException | GuessTheNumberServiceException | InvalidGuessServiceException ex) {
            fail();
        } catch (GameCompletedException ex) {
        }   
    }
    
    
    /**
     * Test of getRoundsbyGameId method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetRoundsbyGameIdGoldenPath() {
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            List<Round> roundsByGameId = toTest.getRoundsbyGameId(1);
            
            assertEquals(2, roundsByGameId.size());
            
            Round secondRoundToCheck = roundsByGameId.get(0);
            
            assertEquals(2, secondRoundToCheck.getRoundId());
            assertEquals(1, secondRoundToCheck.getGameId());
            assertEquals(3479, secondRoundToCheck.getGuessNumber());
            assertEquals(1, secondRoundToCheck.getPartialMatchCount());
            assertEquals(0, secondRoundToCheck.getExactMatchCount());
            assertEquals("10/01/2019 10:25:34 PM", secondRoundToCheck.getTimeStamp());
            
            Round firstRoundToCheck = roundsByGameId.get(1);
            
            assertEquals(1, firstRoundToCheck.getRoundId());
            assertEquals(1, firstRoundToCheck.getGameId());
            assertEquals(1234, firstRoundToCheck.getGuessNumber());
            assertEquals(0, firstRoundToCheck.getPartialMatchCount());
            assertEquals(1, firstRoundToCheck.getExactMatchCount());
            assertEquals("10/01/2019 10:20:34 PM", firstRoundToCheck.getTimeStamp());    
            
        } catch (InvalidGameIdServiceException | GuessTheNumberServiceException ex) {
            fail();
        }  
    }
    
    @Test
    public void testGetRoundsbyBadId() {
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            List<Round> roundsByGameId = toTest.getRoundsbyGameId(9);
            fail();
        } catch (GuessTheNumberServiceException ex) {
            fail();
        } catch (InvalidGameIdServiceException ex) {
            
        }
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetAllGamesGoldenPath() {
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            List<Game> allGames = toTest.getAllGames();
            
            assertEquals(3, allGames.size());
            
            Game firstGame = allGames.get(0);
            Game secondGame = allGames.get(1);
            Game thirdGame = allGames.get(2);
            
            assertEquals(1, firstGame.getGameId());
            assertEquals(0, firstGame.getTargetNumber());
            assertEquals(false, firstGame.getGameFinished());
            
            assertEquals(2, secondGame.getGameId());
            assertEquals(7628, secondGame.getTargetNumber());
            assertEquals(true, secondGame.getGameFinished());
            
            assertEquals(3, thirdGame.getGameId());
            assertEquals(0, thirdGame.getTargetNumber());
            assertEquals(false, thirdGame.getGameFinished());
        } catch (GuessTheNumberServiceException ex) {
            fail();
        }

    }

    /**
     * Test of getGameById method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetGameByIdGoldenPath() {
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            Game firstGame = toTest.getGameById(1);
            Game secondGame = toTest.getGameById(2);
            Game thirdGame = toTest.getGameById(3);
            
            assertEquals(1, firstGame.getGameId());
            assertEquals(0, firstGame.getTargetNumber());
            assertEquals(false, firstGame.getGameFinished());
            
            assertEquals(2, secondGame.getGameId());
            assertEquals(7628, secondGame.getTargetNumber());
            assertEquals(true, secondGame.getGameFinished());
            
            assertEquals(3, thirdGame.getGameId());
            assertEquals(0, thirdGame.getTargetNumber());
            assertEquals(false, thirdGame.getGameFinished());
        } catch (InvalidGameIdServiceException | GuessTheNumberServiceException ex) {
            fail();
        }     
    }
    
    @Test
    public void testGetGameByBadId() {
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            Game toCheck = toTest.getGameById(6);
            fail();
        } catch (GuessTheNumberServiceException ex) {
            fail();
        } catch (InvalidGameIdServiceException ex) {
            
        } 
    }
    
     @Test
    public void testgetRoundsbyBadGameId() {
        try {
            GuessTheNumberService toTest = new GuessTheNumberServiceImpl( new GuessTheNumberInMemoDao());
            
            List<Round> allRounds = toTest.getRoundsbyGameId(5);
            fail();
        } catch (GuessTheNumberServiceException ex) {
            fail();
        } catch (InvalidGameIdServiceException ex) {
            
        } 
    }
    
}
