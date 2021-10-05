package com.gameshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameshop.exception.GameNotFoundException;
import com.gameshop.persistence.domain.Game;
import com.gameshop.rest.dto.GameDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:game-schema.sql", "classpath:game-2.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class GameshopIntegrationTests {
	@Autowired
	private MockMvc mock;

	@Autowired
	private ModelMapper mapper;

	private GameDTO mapToDTO(Game game){
		return this.mapper.map(game, GameDTO.class);
	}
	
	@Autowired
	private ObjectMapper jsonifier;

	private final long testId = 1;
	private final Game testGame = new Game(testId, "League of Legends", 0.0f);
	private List<GameDTO> testList= new ArrayList<>();

	@BeforeEach
	public void setup(){
		//Adding it to the arrayList
		testList.add(mapToDTO(testGame));
	}

	@Test
	public void testAddGame() throws Exception{
		mock
		.perform(post("/add")
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(this.jsonifier.writeValueAsString(testGame)))
		.andExpect(status().isCreated())
		.andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(testGame))));
	}


	@Test
	public void testGetGame() throws Exception{
		mock
		.perform(get("/get/" + testId))
		.andExpect(status().isFound()) 
		.andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(testGame))));
	}

	@Test
	public void testGetGameByTitle() throws Exception{
		mock
		.perform(get("/getByTitle/" + testGame.getTitle()))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(mapToDTO(testGame))));
	}

	@Test
	public void testGetGameByPrice() throws Exception{
		mock
		.perform(get("/getByPrice/" + testGame.getPrice()))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(testList)));
	}

	@Test
	public void testGetGamesByPriceGreaterThan() throws Exception{
		mock
		.perform(get("/getByPriceGreaterThan/" + (testGame.getPrice() - 1)))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(testList)));
	}

	@Test
	public void testGetGamesByPriceLessThan() throws Exception{
		mock
		.perform(get("/getByPriceLessThan/" + (testGame.getPrice() + 1)))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(testList)));
	}

	@Test
	public void testGetAll() throws Exception{
		mock
		.perform(get("/getAll"))
		.andExpect(status().isFound())
		.andExpect(content().json(jsonifier.writeValueAsString(testList)));	
	}

	@Test
	public void testUpdateGame() throws Exception{
		//changing the price
		testGame.setPrice(1.1f);
		//updateGame
		mock
		.perform(put("/update/" + testId)
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonifier.writeValueAsString(testGame)))
		.andExpect(status().isAccepted())
		.andExpect(content().json(jsonifier.writeValueAsString(mapToDTO(testGame))));
	}

	@Test
	public void testDeleteGame() throws Exception{
		mock
		.perform(delete("/delete/" + testId))
		.andExpect(status().isAccepted())
		.andExpect(content().string("true"));
	}

	@Test
	public void testCustomExceptionIsThrown() throws Exception{
		mock.perform(get("/get/1000")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isNotFound())
		  .andExpect(result -> assertTrue(result.getResolvedException() instanceof GameNotFoundException))
		  .andExpect(result -> assertEquals("Game does not exist with that ID", result.getResolvedException().getMessage()));
	}

}
