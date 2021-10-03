package com.gameshop.services;

import com.gameshop.entities.Game;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;



@Service
public class GameService {
    private List<Game> games = new ArrayList<>();

    public Game addGame(Game game){
        // adds a new game
        this.games.add(game);
        // returns the last added game
        return this.games.get(this.games.size() - 1);
    }

    public Game getGame(int id){
        // returns the game with matching id
        return this.games.get(id);
    }

    public List<Game> getAllGames(){
        // returns the whole list
        return this.games;
    }

    public Game updateGame(int id, Game game){
        // removes the existing game with the same id
        this.games.remove(id);
        // adds the new game in its place
        this.games.add(id, game);
        // returns the updated game from the list
        return this.games.get(id);
    }

    public Game removeGame(int id){
        //removes the game from the list and returns it
        return this.games.remove(id);
    }
}
