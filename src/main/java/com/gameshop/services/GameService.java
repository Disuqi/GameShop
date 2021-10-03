package com.gameshop.services;

import com.gameshop.entities.Game;
import com.gameshop.repositories.GameRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class GameService {
    @Autowired
    private GameRepo repo;

    public Game addGame(Game game){
        return this.repo.save(game);
    }

    public Game getGame(long id){
        Optional<Game> existingOptional = this.repo.findById(id);
        Game existing = existingOptional.get();
        return existing;
    }

    public List<Game> getAllGames(){
        return this.repo.findAll();
    }

    public Game updateGame(long id, Game game){
        Optional<Game> existingOptional = this.repo.findById(id);
        Game existing = existingOptional.get();

        existing.setTitle(game.getTitle());
        existing.setPrice(game.getPrice());

        return this.repo.save(existing);
    }

    public boolean removeGame(long id){
        this.repo.deleteById(id);
        boolean exists = this.repo.existsById(id);
        return !exists;
    }
}
