package com.amits.practice.core;

import reactor.core.publisher.Mono;

/**
 * @author amits1
 *
 * 20-Sep-2018
 */
public class MonoExamples {

		// TODO Return an empty Mono
		static Mono<String> emptyMono() {
			return Mono.empty();
		}

		// TODO Return a Mono that never emits any signal
		static Mono<String> monoWithNoSignal() {
			return Mono.never();
		}

		// TODO Return a Mono that contains a "foo" value
		static Mono<String> fooMono() {
			return Mono.just("foo");
		}

		// TODO Create a Mono that emits an IllegalStateException
		static Mono<String> errorMono() {
			return Mono.error(new IllegalStateException());
		}
		
		Mono<User> betterCallSaulForBogusMono(Mono<User> mono) {
			return mono
			        .onErrorReturn(new User("Saul"));
		}
		
		/*Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> flux) {
			return flux
					.onerrorre
			        .onErrorResume(
			        		error -> {System.out.println(error);},
			        		Flux.just(new User("Saul"), new User("Jesse"))
			        		);
		}*/
		
		public static void main(String[] args) {
			emptyMono()
				.subscribe(System.out::println);
			
			System.out.println("----------");
			
			monoWithNoSignal()
				.subscribe(System.out::println);
			
			System.out.println("----------");
			
			fooMono()
				.subscribe(System.out::println);
			
			System.out.println("----------");
			
			errorMono()
			.subscribe(System.out::println, error -> System.out.println("error :: " + error));
		}
}
