package it.discovery.reactive.restaurant.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import it.discovery.reactive.restaurant.model.Customer;
import it.discovery.reactive.restaurant.model.Meal;
import it.discovery.reactive.restaurant.model.Order;
import it.discovery.reactive.restaurant.model.Waiter;

public class WaiterService {
	
	private final Set<Waiter> availableWaiters;
	
	private final CookService cookService;
	
	public WaiterService(CookService cookService) {
		availableWaiters = new CopyOnWriteArraySet<>();
		this.cookService = cookService;
		
		availableWaiters.add(new Waiter(1, "Samanta"));
		availableWaiters.add(new Waiter(2, "Ann"));
		availableWaiters.add(new Waiter(3, "Tiffany"));
	}

	/**
	 * Acquire first available waiter 
	 * @return
	 */
	public Waiter acquire() {
		if(availableWaiters.isEmpty()) {
			return null;
		}
		
		Waiter waiter = availableWaiters.iterator().next();
		availableWaiters.remove(waiter);
		
		return waiter;
	}
	
	public void release(Waiter waiter) {
		availableWaiters.add(waiter);
	}
	
	public Order order(Customer customer, Waiter waiter, Collection<String> mealNames) {		
		return new Order(waiter, customer, mealNames);
	}
	
	public List<Meal> take(Order order) {
		return order.getMealNames().stream()
				.map(mealName -> cookService.cook(mealName))
				.collect(Collectors.toList());
	}

}
