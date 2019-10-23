/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.guessthenumber.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import sg.guessthenumber.daos.GuessTheNumberDao;
import sg.guessthenumber.daos.GuessTheNumberDaoException;
import sg.guessthenumber.models.Game;
import sg.guessthenumber.models.Round;
import java.util.Date;
import sg.guessthenumber.daos.InvalidGameIdDaoException;
import sg.guessthenumber.daos.InvalidTimeStampDaoException;

/**
 *
 * @author blee0
 */
@Repository
public class GuessTheNumberServiceImpl implements GuessTheNumberService {

    GuessTheNumberDao dao;

    @Autowired
    public GuessTheNumberServiceImpl(GuessTheNumberDao dao) {
        this.dao = dao;
    }

    @Override
    public int createNewGame() throws GuessTheNumberServiceException {
        int toReturn = -1;

        int targetNumber = generateNewTargetNumber();

        toReturn = dao.createNewGame(targetNumber);

        if (toReturn == -1) {
            throw new GuessTheNumberServiceException("Unable to create new game.");
        }

        return toReturn;
    }

    @Override
    public Round createNewRoundByGameId(Round newRound) throws InvalidGameIdServiceException, GameCompletedException, GuessTheNumberServiceException, InvalidGuessServiceException {

        Round toReturn = new Round();

        Game requestedGame = new Game();
        try {
            requestedGame = dao.getGameById(newRound.getGameId());
        } catch (InvalidGameIdDaoException ex) {
            throw new InvalidGameIdServiceException("Requested GameId " + newRound.getGameId() + " could not be found.");
        }

        int requestedGameId = requestedGame.getGameId();

        if (requestedGame.getGameFinished() == true) {
            throw new GameCompletedException("The requested GameId " + requestedGameId + " has been completed.");
        }

        boolean duplicates = hasDuplicates(newRound.getGuessNumber());

        if (!duplicates) {

            int target = requestedGame.getTargetNumber();
            int guess = newRound.getGuessNumber();

            int exactNumbers = computeExact(target, guess);
            int partialNumbers = computePartial(target, guess);

            newRound.setPartialMatchCount(partialNumbers);
            newRound.setExactMatchCount(exactNumbers);

            String timeStamp = getTimeStamp();

            newRound.setTimeStamp(timeStamp);

            try {
                toReturn = dao.createNewRoundByGameId(newRound);
            } catch (GuessTheNumberDaoException | InvalidTimeStampDaoException ex) {
                throw new GuessTheNumberServiceException("Could not submit new Round.", ex);
            }

            if (newRound.getExactMatchCount() == 4) {
                try {
                    dao.editGame(requestedGameId);
                } catch (GuessTheNumberDaoException | InvalidGameIdDaoException ex) {
                    throw new GuessTheNumberServiceException("Could not edit order with id " + requestedGameId, ex);
                }
            }
        } else {
            throw new InvalidGuessServiceException("Guess is invalid.");
        }

        return toReturn;
    }

    @Override
    public List<Round> getRoundsbyGameId(int id) throws InvalidGameIdServiceException, GuessTheNumberServiceException {

        List<Round> toReturn = new ArrayList();

        boolean validId = checkValidId(id);

        if (validId == true) {
            try {
                toReturn = dao.getRoundsByGameId(id);
            } catch (InvalidGameIdDaoException ex) {
                throw new GuessTheNumberServiceException("Unable to get rounds by GameId: " + id, ex);
            }
        } else {
            throw new InvalidGameIdServiceException("Requested GameId " + id + " could not be found.");
        }

        return toReturn;

    }

    @Override
    public List<Game> getAllGames() throws GuessTheNumberServiceException {

        List<Game> toReturn;
        try {
            toReturn = dao.getAllGames();
        } catch (GuessTheNumberDaoException ex) {
            throw new GuessTheNumberServiceException("Unable to get all games.", ex);
        }

        for (Game toCheck : toReturn) {
            if (toCheck.getGameFinished() == false) {
                toCheck.setTargetNumber(0);
            }
        }

        return toReturn;

    }

    @Override
    public Game getGameById(int id) throws InvalidGameIdServiceException, GuessTheNumberServiceException {

        Game toReturn = new Game();

        boolean validId = checkValidId(id);

        if (validId == true) {
            try {
                toReturn = dao.getGameById(id);
            } catch (InvalidGameIdDaoException ex) {
                throw new GuessTheNumberServiceException("Unable to get GameId: " + id, ex);
            }
            if (toReturn.getGameFinished() == false) {
                toReturn.setTargetNumber(0);
            }
        } else {
            throw new InvalidGameIdServiceException("Requested GameId " + id + " could not be found.");
        }

        return toReturn;
    }

    private int generateNewTargetNumber() {
        Random rng = new Random();
        List<Integer> avail = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        int toReturn = 0;
        for (int i = 0; i < 4; i++) {
            toReturn *= 10;
            int digitIndex = rng.nextInt(avail.size());
            int digit = avail.get(digitIndex);
            toReturn += digit;
            avail.remove(digitIndex);
        }
        return toReturn;
    }

    private boolean hasDuplicates(int guessNumber) {
        boolean toReturn = false;
        boolean[] seenDigit = new boolean[]{false, false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 4; i++) {
            int onesPlace = guessNumber % 10;
            guessNumber /= 10;
            boolean seenBefore = seenDigit[onesPlace];
            if (seenBefore) {
                toReturn = true;
                break;
            } else {
                seenDigit[onesPlace] = true;
            }

        }
        return toReturn;

    }

    private int computeExact(int target, int guess) {
        int toReturn = 0;
        for (int i = 0; i < 4; i++) {
            int targetOnes = target % 10;
            int guessOnes = guess % 10;
            if (targetOnes == guessOnes) {
                toReturn++;
            }
            target /= 10;
            guess /= 10;
        }
        return toReturn;
    }

    private int computePartial(int target, int guess) {
        int toReturn = 0;
        String t = target + " ";
        String g = guess + " ";
        if (target < 1000) {
            t = "0" + t;
        }
        if (guess < 1000) {
            g = "0" + g;
        }
        for (int i = 0; i < 4; i++) {
            char gc = g.charAt(i);
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    char tc = t.charAt(j);
                    if (gc == tc) {
                        toReturn++;
                    }
                }
            }
        }
        return toReturn;
    }

    private boolean checkValidId(int id) throws GuessTheNumberServiceException {
        boolean toReturn = false;

        List<Game> allGames = getAllGames();

        for (Game toCheck : allGames) {
            if (id == toCheck.getGameId()) {
                toReturn = true;
            }
        }

        return toReturn;
    }

    private String getTimeStamp() {
        String toReturn = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date());

        return toReturn;

    }

}
