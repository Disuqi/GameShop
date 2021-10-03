package com.gameshop.controller;
import com.gameshop.entities.Game;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    private List<Game> games = new ArrayList<>();

    @PostMapping("/add")
    public boolean addGame(@RequestBody Game game){
        return this.games.add(game);
    }

    @GetMapping("/get/{id}")
    public Game getGame(@PathVariable int id){
        return this.games.get(id);
    }

    @GetMapping("/getAll")
    public List<Game> getAllGames(){
        return games;
    }

    @PutMapping("/update/{id}")
    public Game updateGame(@PathVariable int id, @RequestBody Game game){
        this.games.remove(id);
        this.games.add(id, game);
        return this.games.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public Game removeGame(@PathVariable int id){
        return this.games.remove(id);
    }

}
