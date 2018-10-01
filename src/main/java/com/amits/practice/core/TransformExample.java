package com.amits.practice.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author amits1
 *
 * 20-Sep-2018
 */
public class TransformExample {

	// TODO Capitalize the user username, firstname and lastname
		static Mono<User> capitalizeOne(Mono<User> mono) {
			return mono
					.map(user -> new User(user.getUserName().toUpperCase()));
		}

		// TODO Capitalize the users username, firstName and lastName
		static Flux<User> capitalizeMany(Flux<User> flux) {
			return  flux
					.map(user -> new User(user.getUserName().toUpperCase()));
		}

		// TODO Capitalize the users username, firstName and lastName using #asyncCapitalizeUser
		static Flux<User> asyncCapitalizeMany(Flux<User> flux) {
			return flux
					.flatMap(TransformExample::asyncCapitalizeUser);
		}

		static Mono<User> asyncCapitalizeUser(User u) {
			return Mono.just(new User(u.getUserName().toUpperCase()));
		}
		
		public static void main(String[] args) {
			capitalizeOne(Mono
					.just(new User("Amit")))
					.subscribe(user -> System.out.println(user.getUserName()));
			
			System.out.println("----------");
			
			capitalizeMany(Flux
					.just(new User("Amit"), new User("Sanjay"), new User("Sadafule")))
					.subscribe(user -> System.out.println(user.getUserName()));
			
			
			System.out.println("----------");
			
			asyncCapitalizeMany(Flux
					.just(new User("Amit"), new User("Sanjay"), new User("Sadafule")))
					.subscribe(user -> System.out.println(user.getUserName()));
		}
}
