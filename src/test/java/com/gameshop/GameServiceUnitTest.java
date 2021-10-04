package com.gameshop;

import com.gameshop.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameServiceUnitTest {
    @Autowired
    private GameService service;
    
}
