package com.gameshop.service;

import com.gameshop.exception.GameNotFoundException;
import com.gameshop.persistence.domain.Game;
import com.gameshop.persistence.repository.GameRepo;

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

    public Game getGameByTitle(String title){
        return this.repo.findByTitle(title).orElseThrow(GameNotFoundException::new);
    }
    public List<Game> getGamesByPrice(float price){
        return this.repo.findByPrice(price);
    }

    public List<Game> getGamesByPriceGreaterThan(float price){
        return this.repo.findByPriceGreaterThan(price);
    }
    
    public List<Game> getGamesByPriceLessThan(float price){
        return this.repo.findByPriceLessThan(price);
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