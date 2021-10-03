package com.gameshop.rest.controller;
import com.gameshop.persistence.domain.Game;
import com.gameshop.rest.dto.GameDTO;
import com.gameshop.service.GameService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    private GameService service;

    @PostMapping("/add")
    public GameDTO addGame(@RequestBody Game game){
        return this.service.addGame(game);
    }

    @GetMapping("/get/{id}")
    public GameDTO getGame(@PathVariable long id){
        return this.service.getGame(id);
    }

    @GetMapping("/getByTitle/{title}")
    public GameDTO getGameByTitle(@PathVariable String title){
        return this.service.getGameByTitle(title);
    }

    @GetMapping("/getByPrice/{price}")
    public List<GameDTO> getGamesByPrice(@PathVariable float price){
        return this.service.getGamesByPrice(price);
    }

    @GetMapping("/getByPriceGreaterThan/{price}")
    public List<GameDTO> getGamesPriceHigherThan(@PathVariable float price){
        return this.service.getGamesByPriceGreaterThan(price);
    }

    @GetMapping("/getByPriceLessThan/{price}")
    public List<GameDTO> getGamesLowerThan(@PathVariable float price){
        return this.service.getGamesByPriceLessThan(price);
    }

    @GetMapping("/getAll")
    public List<GameDTO> getAllGames(){
        return this.service.getAllGames();
    }

    @PutMapping("/update/{id}")
    public GameDTO updateGame(@PathVariable long id, @RequestBody Game game){
        return this.service.updateGame(id, game);
    }

    @DeleteMapping("/delete/{id}")
    public boolean removeGame(@PathVariable long id){
        return this.service.removeGame(id);
    }

}
