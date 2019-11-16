package it.discovery.reactive.restaurant.model;

import java.util.Collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Order {
	
	private final Waiter waiter;
	
	private final Customer customer; 
	
	private final Collection<String> mealNames;

}
