package it.discovery.reactive.restaurant.model;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor
public class Meal {
	
	private String name;
	
	private double price;
	
	/**
	 * Time to cook this meal
	 */
	private Duration duration;

}
