package it.discovery.reactive.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(of="id")
@AllArgsConstructor 
public class Waiter {
	
	private int id;

	private String name;
}
