package com.gameshop.service;

import com.gameshop.exception.GameNotFoundException;
import com.gameshop.persistence.domain.Game;
import com.gameshop.persistence.repository.GameRepo;
import com.gameshop.rest.dto.GameDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class GameService {
    @Autowired
    private GameRepo repo;

    @Autowired
    private ModelMapper mapper;
    
    public GameDTO mapToDTO(Game game){
        return this.mapper.map(game, GameDTO.class);
    }

    public GameDTO addGame(Game game){
        return mapToDTO(this.repo.save(game));
    }

    public GameDTO getGame(long id){
        Game game = this.repo.findById(id).orElseThrow(GameNotFoundException::new);
        return mapToDTO(game);
    }

    public GameDTO getGameByTitle(String title){
        Optional<Game> existingOptional = this.repo.findByTitle(title);
        Game existing = existingOptional.get();
        return mapToDTO(existing);
    }
    public List<GameDTO> getGamesByPrice(float price){
        return this.repo.findByPrice(price).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<GameDTO> getGamesByPriceGreaterThan(float price){
        return this.repo.findByPriceGreaterThan(price).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    public List<GameDTO> getGamesByPriceLessThan(float price){
        return this.repo.findByPriceLessThan(price).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<GameDTO> getAllGames(){
        return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public GameDTO updateGame(long id, Game game){
        Optional<Game> existingOptional = this.repo.findById(id);
        Game existing = existingOptional.get();

        existing.setTitle(game.getTitle());
        existing.setPrice(game.getPrice());

        return mapToDTO(this.repo.save(existing));
    }

    public boolean removeGame(long id){
        this.repo.deleteById(id);
        boolean exists = this.repo.existsById(id);
        return !exists;
    }
}
