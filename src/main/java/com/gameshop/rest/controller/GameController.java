package com.gameshop.rest.controller;
import com.gameshop.persistence.domain.Game;
import com.gameshop.rest.dto.GameDTO;
import com.gameshop.service.GameService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GameDTO> addGame(@RequestBody Game game){
        return new ResponseEntity<GameDTO>(this.service.addGame(game), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GameDTO> getGame(@PathVariable long id){
        return new ResponseEntity<GameDTO>(this.service.getGame(id), HttpStatus.FOUND);
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<GameDTO> getGameByTitle(@PathVariable String title){
        return new ResponseEntity<GameDTO>(this.service.getGameByTitle(title), HttpStatus.FOUND);
    }

    @GetMapping("/getByPrice/{price}")
    public ResponseEntity<List<GameDTO>> getGamesByPrice(@PathVariable float price){
        return new ResponseEntity<List<GameDTO>>(this.service.getGamesByPrice(price), HttpStatus.FOUND);
    }

    @GetMapping("/getByPriceGreaterThan/{price}")
    public ResponseEntity<List<GameDTO>> getGamesPriceGreaterThan(@PathVariable float price){
        return new ResponseEntity<List<GameDTO>>(this.service.getGamesByPriceGreaterThan(price), HttpStatus.FOUND);
    }

    @GetMapping("/getByPriceLessThan/{price}")
    public ResponseEntity<List<GameDTO>> getGamesLessThan(@PathVariable float price){
        return new ResponseEntity<List<GameDTO>>(this.service.getGamesByPriceLessThan(price), HttpStatus.FOUND);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GameDTO>> getAllGames(){
        return new ResponseEntity<List<GameDTO>>(this.service.getAllGames(), HttpStatus.FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable long id, @RequestBody Game game){
        return new ResponseEntity<GameDTO>(this.service.updateGame(id, game), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> removeGame(@PathVariable long id){
        return new ResponseEntity<Boolean>(this.service.removeGame(id), HttpStatus.ACCEPTED);
    }

}
