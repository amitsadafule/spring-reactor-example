package com.amits.practice.core;

import java.time.Duration;
import java.util.stream.IntStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author amits1
 *
 *         26-Sep-2018
 */
public class FewMoreOperationsExample {

	// TODO Create a Flux of user from Flux of username, firstname and lastname.
	static Flux<User> userFluxFromStringFlux(Flux<String> usernameFlux,
			Flux<String> firstnameFlux) {
		firstnameFlux.subscribe(System.out::println);
		return Flux
				.zip(usernameFlux, firstnameFlux)
				.map(zipped -> new User(zipped.getT1(), zipped.getT2()));
	}

	// TODO Return the mono which returns its value faster
	static Mono<User> useFastestMono(Mono<User> mono1, Mono<User> mono2) {
		return Mono.first(mono1, mono2);
	}

	// TODO Return the flux which returns the first value faster
	static Flux<User> useFastestFlux(Flux<User> flux1, Flux<User> flux2) {
		return Flux.first(flux1, flux2);
	}

	// TODO Convert the input Flux<User> to a Mono<Void> that represents the
	// complete signal of the flux
	static Mono<Void> fluxCompletion(Flux<User> flux) {
		return flux.then();
	}

	// TODO Return a valid Mono of user for null input and non null input user
	// (hint: Reactive Streams do not accept null values)
	static Mono<User> nullAwareUserToMono(User user) {
		return Mono.justOrEmpty(user);
	}

	// TODO Return the same mono passed as input parameter, expect that it will
	// emit User.SKYLER when empty
	static Mono<User> emptyToSkyler(Mono<User> mono) {
		return mono.switchIfEmpty(Mono.just(new User("SKYLER")));
	}

	public static void createSubscribers(Flux<Integer> flux) {
	    IntStream.range(1, 5).forEach(value ->
	        flux.subscribe(integer -> System.out.println(value + " consumer processed "
	                + integer + " using thread: " + Thread.currentThread().getName())));
	}

	
	public static void main(String[] args) throws InterruptedException {
		
		Flux<Integer> flux2 = Flux.range(0, 2).delayElements(Duration.ofMillis(1));
		createSubscribers(flux2);
		
		System.out.println("----------");
		
		userFluxFromStringFlux(
				Flux.just("Amit", "Sourabh", "Rupesh"),
				Flux
					.just("Sadafule", "Patil", "Gharat")
					.delaySequence(Duration.ofSeconds(1)))
			.subscribe(user -> System.out.println("userFluxFromStringFlux" + user));
		
		System.out.println("----------");
		
		useFastestMono(
				Mono
					.just(new User("f1"))
					.delayElement(Duration.ofMillis(100)),
				Mono.just(new User("f2"))	
				)
				.subscribe(user -> System.out.println("useFastestMono" + user));
		
		
		System.out.println("----------");
		
		useFastestFlux(
				Flux
					.just(new User("f1"),new User("f4"))
					.delayElements(Duration.ofMillis(100)),
					Flux.just(new User("f2"),new User("f3"))	
				)
				.subscribe(user -> System.out.println("useFastestFlux" + user));
		
		Thread.sleep(10000);
	}

}
