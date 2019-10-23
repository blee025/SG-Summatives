/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sg.guessthenumber.daos.InvalidTimeStampDaoException;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;
import sg.guessthenumber.services.GameCompletedException;
import sg.guessthenumber.services.GuessTheNumberService;
import sg.guessthenumber.services.GuessTheNumberServiceException;
import sg.guessthenumber.services.InvalidGameIdServiceException;
import sg.guessthenumber.services.InvalidGuessServiceException;

/**
 *
 * @author blee0
 */
@RestController
@RequestMapping("/api")
public class Controller {
    
    @Autowired 
    GuessTheNumberService service;
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createNewGame() throws GuessTheNumberServiceException {
        int gameId = service.createNewGame();
        return gameId;
    }
    
    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round createNewRoundByGameId(@RequestBody Round newRound) throws InvalidGameIdServiceException, GuessTheNumberServiceException, InvalidGuessServiceException, GameCompletedException {
        Round toReturn = service.createNewRoundByGameId(newRound);
        return toReturn;
    }
        
    @GetMapping("/rounds/{id}")
    public List<Round> getAllRoundsByGameId(@PathVariable Integer id) throws InvalidGameIdServiceException, GuessTheNumberServiceException {
        
        List<Round> toReturn = service.getRoundsbyGameId(id);
        
        return toReturn;
    }
    
    @GetMapping("/game")
    public List<Game> getAllGames() throws GuessTheNumberServiceException {
        
        List<Game> toReturn = service.getAllGames();
        
        return toReturn;
    }
    
    @GetMapping("/game/{id}")
    public Game getGameById(@PathVariable Integer id) throws InvalidGameIdServiceException, GuessTheNumberServiceException {
        Game toReturn = service.getGameById(id);
     
        return toReturn;
    }
    
}
