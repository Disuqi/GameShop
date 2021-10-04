package com.gameshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameshop.persistence.domain.Game;
import com.gameshop.rest.dto.GameDTO;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:game-schema.sql", "classpath:game-schema.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
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

	private final long test_id = 1;
	private final Game test_game = new Game(test_id, "League of Legends", 0.0f);
	private List<GameDTO> test_list= new ArrayList<>();

	@Test
	public void testEverything() throws Exception{
		//addGame
		mock
		.perform(post("/add")
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonifier.writeValueAsString(test_game)))
		.andExpect(status().isCreated())
		.andExpect(content().json(jsonifier.writeValueAsString(mapToDTO(test_game))));
		//adding it to the list
		this.test_list.add(mapToDTO(test_game));
		//getGame
		mock
		.perform(get("/get/" + test_id))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(mapToDTO(test_game))));
		//getByTitle
		mock
		.perform(get("/getByTitle/" + test_game.getTitle()))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(mapToDTO(test_game))));
		//getByPrice
		mock
		.perform(get("/getByPrice/" + test_game.getPrice()))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(test_list)));
		//getByPriceGreaterThan
		mock
		.perform(get("/getByPriceGreaterThan/" + (test_game.getPrice() - 1)))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(test_list)));
		//getByPriceLessThan
		mock
		.perform(get("/getByPriceLessThan/" + (test_game.getPrice() + 1)))
		.andExpect(status().isFound()) 
		.andExpect(content().json(jsonifier.writeValueAsString(test_list)));
		//getAll
		mock
		.perform(get("/getAll"))
		.andExpect(status().isFound())
		.andExpect(content().json(jsonifier.writeValueAsString(test_list)));	
		//changing the price
		test_game.setPrice(1.1f);
		//updateGame
		mock
		.perform(put("/update/" + test_id)
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonifier.writeValueAsString(test_game)))
		.andExpect(status().isAccepted())
		.andExpect(content().json(jsonifier.writeValueAsString(mapToDTO(test_game))));
		//deleteGame
		mock
		.perform(delete("/delete/" + test_id))
		.andExpect(status().isAccepted())
		.andExpect(content().string("true"));
	}
	
	// @Test
	// public void testAddGame() throws Exception{
	// 	//works perfectly
	// 	this.mock
	// 		.perform(post("/add")
	// 			.accept(MediaType.APPLICATION_JSON)
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(this.jsonifier.writeValueAsString(test_game)))
	// 		.andExpect(status().isCreated())
	// 		.andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(test_game))));
	// }

	// @Test
	// public void testGetGame() throws Exception{
	// 	//expected 202 but got 404, the prepopulation of the database is not working!
	// 	this.mock
	// 		.perform(get("/get/1"))
	// 		.andExpect(status().isAccepted()) 
	// 		.andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(test_game))));
	// }

}
