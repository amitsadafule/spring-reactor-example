package com.amits.practice.core;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author amits1
 *
 *         20-Sep-2018
 */
public class MergeExample {

	// TODO Merge flux1 and flux2 values with interleave
	static Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
		return Flux.merge(flux1, flux2);
	}

	// TODO Merge flux1 and flux2 values with no interleave (flux1 values and
	// then flux2 values)
	static Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
		return Flux.concat(flux1, flux2);
	}

	// TODO Create a Flux containing the value of mono1 then the value of mono2
	Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
		return null;
	}

	public static void main(String[] args) {
		mergeFluxWithInterleave(Flux
					.just(new User("Amit"), new User("Sanjay"), new User("Sadafule"))
					.delayElements(Duration.ofMillis(300l)),
				Flux.just(new User("abc"), new User("pqr"))
				)
			.subscribe(user -> System.out.println(user.getUserName()));

		System.out.println("----------");
		
		mergeFluxWithNoInterleave(Flux
				.just(new User("Amit"), new User("Sanjay"), new User("Sadafule"))
				.delayElements(Duration.ofMillis(300l)),
			Flux.just(new User("abc"), new User("pqr"))
			)
		.subscribe(user -> System.out.println(user.getUserName()));

	}
}
