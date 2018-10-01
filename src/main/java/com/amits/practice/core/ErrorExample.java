package com.amits.practice.core;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author amits1
 *
 *         26-Sep-2018
 */
public class ErrorExample {

	// TODO Return a Mono<User> containing User.SAUL when an error occurs in the
	// input Mono, else do not change the input Mono.
	static Mono<User> betterCallSaulForBogusMono(Mono<User> mono) {
		return mono.onErrorReturn(new User("SAUL"));
	}

	// TODO Return a Flux<User> containing User.SAUL and User.JESSE when an
	// error occurs in the input Flux, else do not change the input Flux.
	static Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> flux) {
		return flux
				.onErrorResume(error -> Flux
						.just(new User("SAUL"), new User("JESSE")));
	}

	// TODO Implement a method that capitalizes each user of the incoming flux
	// using the
	// #capitalizeUser method and emits an error containing a
	// GetOutOfHereException error
	static Flux<User> capitalizeMany(Flux<User> flux) {
		return flux
				.map(user -> {
					try {
						return ErrorExample.capitalizeUser(user);
					} catch (Exception e) {
						throw Exceptions.propagate(e);
					}
				});
	}

	static User capitalizeUser(User user) throws GetOutOfHereException {
		if (user.equals(new User("SAUL"))) {
			throw new GetOutOfHereException();
		}
		return new User(user.getUserName());
	}

	protected final static class GetOutOfHereException extends Exception {
	}
	
	static void fluxWithError() {
		/**Flux error handling**/
		Flux<Integer> interror = Flux.range(1, 10);
		interror.map(i -> {
			if(i <= 3) return i;
			throw new RuntimeException("got 4");
		});
		interror.subscribe(System.out::println,
					error -> System.out.println(error),
					() -> System.out.println("completed")); //error and completion are mutually exclusive
	}
	
	public static void main(String[] args) {
		betterCallSaulForBogusMono(Mono
					.just(new User("test"))
					.map(user -> {int v = (1/0); return user;}))
				.subscribe(System.out::println);
		
		System.out.println("----------");
		
		betterCallSaulAndJesseForBogusFlux(Flux
					.just(new User("test"))
					.map(user -> {int v = (1/0); return user;}))
				.subscribe(System.out::println);
		
		capitalizeMany(Flux
					.just(new User("SAUL")))
				.subscribe(System.out::println);
		
		System.out.println("----------");
		fluxWithError();
	}
}
