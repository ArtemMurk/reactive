package it.discovery.reactive.restaurant.bootstrap;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.discovery.reactive.restaurant.model.Customer;
import it.discovery.reactive.restaurant.model.Meal;
import it.discovery.reactive.restaurant.model.Order;
import it.discovery.reactive.restaurant.model.Waiter;
import it.discovery.reactive.restaurant.repository.MealRepository;
import it.discovery.reactive.restaurant.service.CookService;
import it.discovery.reactive.restaurant.service.WaiterService;
import it.discovery.reactive.restaurant.social.FacebookConnector;
import it.discovery.reactive.restaurant.social.SiteConnector;

public class Starter {

	public static void main(String[] args) {
		MealRepository mealRepository = new MealRepository();
		CookService cookService = new CookService(mealRepository);

		WaiterService waiterService = new WaiterService(cookService);

		FacebookConnector facebookConnector = new FacebookConnector();

		SiteConnector siteConnector = new SiteConnector();

		Set<String> mealNames = mealRepository.getMealNames();

		List<Customer> customers = Stream.iterate(1, i -> i + 1).limit(20).map(i -> new Customer("Donald" + i))
				.collect(Collectors.toList());
		customers.forEach(customer -> {
			Waiter waiter = waiterService.acquire();
			int attempts = 0;
			while (waiter == null && attempts <= 3) {
				waiter = waiterService.acquire();
				attempts++;
			}
			if (waiter == null) {
				String feedback = "Customer " + customer.getName() + 
						" is unhappy because no available waiters";
				siteConnector.saveFeedback(feedback);
				facebookConnector.saveFeedback(feedback);
			} else {
				Order order = waiterService.order(customer, waiter, mealNames);
				List<Meal> meals = waiterService.take(order);
				if (meals == null) {
					System.out.println("No order prepared");
				} else {
					System.out.println("Got order " + order.getMealNames());
				}
				waiterService.release(waiter);
			}
		});

	}
}
