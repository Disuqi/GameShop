package com.gameshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gameshop.persistence.domain.Game;
import com.gameshop.persistence.repository.GameRepo;
import com.gameshop.rest.dto.GameDTO;
import com.gameshop.service.GameService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GameServiceUnitTest {
    @Autowired
    private GameService service;
    
    @MockBean
    private GameRepo repo;

    @MockBean
    private ModelMapper mapper;


    private Game testGame;
    private GameDTO testGameDTO;
    private String title;
    private float price;
    private long id;
    private Optional<Game> testGameOpt;
    private List<Game> testList;
    private List<GameDTO> testListDTO;
    private boolean needed;

    @BeforeEach
    public void setup(){
        testGame = new Game(1, "Dark souls 3", 30.0f);
        testGameDTO = new GameDTO(1, "Dark souls 3", 30.0f);
        title = testGame.getTitle();
        price = testGame.getPrice();
        id = testGame.getId();
        testGameOpt = Optional.of(testGame);
        testList = new ArrayList<>();
        testListDTO = new ArrayList<>();
        needed = true;
        testList.add(testGame);
        testListDTO.add(testGameDTO);
        Mockito.when(mapper.map(testGame, GameDTO.class)).thenReturn(testGameDTO);
    }

    @AfterEach
    public void verifyMapper(){
        if(needed){
            Mockito.verify(mapper, Mockito.times(1)).map(testGame, GameDTO.class);
        }
        needed = true;
    }

    @Test
    public void testAddGame(){
        Mockito.when(repo.save(testGame)).thenReturn(testGame);

        Assertions.assertThat(service.addGame(testGame)).isEqualTo(testGameDTO);

        //is this.mapper necessary or good practice?
        Mockito.verify(repo, Mockito.times(1)).save(testGame);
    }

    @Test
    public void testGetGame(){
        Mockito.when(repo.findById(id)).thenReturn(testGameOpt);

        Assertions.assertThat(service.getGame(id)).isEqualTo(testGameDTO);

        //is this.mapper necessary or good practice?
        Mockito.verify(repo, Mockito.times(1)).findById(id);
    }

    @Test
    public void testGetGameByTitle(){
        Mockito.when(repo.findByTitle(title)).thenReturn(testGameOpt);

        Assertions.assertThat(service.getGameByTitle(title)).isEqualTo(testGameDTO);

        //is this.repo necessary or good practice?
        Mockito.verify(repo, Mockito.times(1)).findByTitle(title);
    }

    @Test
    public void testGetGamesByPrice(){
        Mockito.when(repo.findByPrice(price)).thenReturn(testList);
        
        Assertions.assertThat(service.getGamesByPrice(price)).isEqualTo(testListDTO);
        
        Mockito.verify(repo, Mockito.times(1)).findByPrice(price);
    }

    @Test
    public void testGetGamesByPriceGreaterThan(){
        price--;
        Mockito.when(repo.findByPriceGreaterThan(price)).thenReturn(testList);
        
        Assertions.assertThat(service.getGamesByPriceGreaterThan(price)).isEqualTo(testListDTO);
        
        Mockito.verify(repo, Mockito.times(1)).findByPriceGreaterThan(price);
    }

    @Test
    public void testGetGamesByPriceLessThan(){
        price++;
        Mockito.when(repo.findByPriceLessThan(price)).thenReturn(testList);
        
        Assertions.assertThat(service.getGamesByPriceLessThan(price)).isEqualTo(testListDTO);
        
        Mockito.verify(repo, Mockito.times(1)).findByPriceLessThan(price);
    }

    @Test
    public void testGetAll(){
        Mockito.when(repo.findAll()).thenReturn(testList);

        Assertions.assertThat(service.getAllGames()).isEqualTo(testListDTO);

        Mockito.verify(repo, Mockito.times(1)).findAll();
    }

    @Test
    public void testUpdateGame(){
        Mockito.when(repo.findById(id)).thenReturn(testGameOpt);
        Mockito.when(repo.save(testGame)).thenReturn(testGame);

        
        Assertions.assertThat(service.updateGame(id, testGame)).isEqualTo(testGameDTO);

        Mockito.verify(repo, Mockito.times(1)).findById(id);
        Mockito.verify(repo, Mockito.times(1)).save(testGame);

    }

    @Test
    public void testRemoveGame(){
        needed = false;
        Mockito.when(repo.existsById(id)).thenReturn(false);

        Assertions.assertThat(service.removeGame(id)).isEqualTo(true);

        Mockito.verify(repo, Mockito.times(1)).existsById(id);
    }

}
