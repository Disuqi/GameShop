package com.gameshop.controller;
import com.gameshop.entities.Game;
import com.gameshop.services.GameService;

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
    public Game addGame(@RequestBody Game game){
        return this.service.addGame(game);
    }

    @GetMapping("/get/{id}")
    public Game getGame(@PathVariable long id){
        return this.service.getGame(id);
    }

    @GetMapping("/getAll")
    public List<Game> getAllGames(){
        return this.service.getAllGames();
    }

    @PutMapping("/update/{id}")
    public Game updateGame(@PathVariable long id, @RequestBody Game game){
        return this.service.updateGame(id, game);
    }

    @DeleteMapping("/delete/{id}")
    public boolean removeGame(@PathVariable long id){
        return this.service.removeGame(id);
    }

}
