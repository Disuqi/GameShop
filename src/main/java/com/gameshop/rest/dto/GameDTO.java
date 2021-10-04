package com.gameshop.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GameDTO {
    private long id;

    private String title;

	private float price;
}
