package com.gameshop.persistence.repository;

import java.util.Optional;
import java.util.List;

import com.gameshop.persistence.domain.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends JpaRepository<Game, Long>{
    @Query(value = "SELECT * FROM Game WHERE title = ?1", nativeQuery = true)
    Optional<Game> findByTitle(String title);

    @Query(value = "SELECT * FROM Game WHERE price = ?1", nativeQuery = true)
    List<Game> findByPrice(float price);

    @Query(value = "SELECT * FROM Game WHERE price >= ?1", nativeQuery = true)
    List<Game> findByPriceGreaterThan(float price);

    @Query(value = "SELECT * FROM Game WHERE price <= ?1", nativeQuery = true)
    List<Game> findByPriceLessThan(float price);
}