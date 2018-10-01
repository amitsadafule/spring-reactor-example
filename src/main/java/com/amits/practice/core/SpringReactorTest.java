package com.amits.practice.core;

import java.time.Duration;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//https://tech.io/playgrounds/929/reactive-programming-with-reactor-3
public class SpringReactorTest {

	//https://tech.io/playgrounds/929/reactive-programming-with-reactor-3
	public static void main(String[] args) {
		
		Mono.just(1)
	    	.map(integer -> integer + 2l)
	    	.or(Mono.delay(Duration.ofMillis(100)))
	    	.subscribe(System.out::println);
		
		System.out.println("-------------");
		
		Flux.just("Amit", "Sanjay", "Saurabh")
				.subscribe(System.out::println)
				.dispose();
		
		Flux<Integer> ints = Flux.range(1, 3);
		ints.subscribe(i -> System.out.println(i));
		ints.subscribe(i -> System.out.println("Hi" + i));
		
		System.out.println("-------------");
		
		
		System.out.println("-------------");
		/**custom subscriber**/
		SimpleSubscriber<Integer> sub = new SimpleSubscriber<Integer>();
		Flux<Integer> intCustomSubscriber = Flux.range(1, 10);
		intCustomSubscriber.map(i -> {
			if(i <= 3) return i*i;
			throw new RuntimeException("custom got 4");
		})
		.subscribe(System.out::println,
				error -> System.out.println(error),
				() -> System.out.println("completedcustom"));
		intCustomSubscriber.subscribe(sub);
		
		
		System.out.println("-------------");
		/**generator**/
		Flux<String> flux = Flux.generate(
			() -> 0,
			(state, sink) -> {
				sink.next("3 x " + state + " = " + 3*state);
				if(state == 10) sink.complete();
				return state + 1;
			},
			(state) -> System.out.println("state: " + state)); //This last method will be used for clean up process e.g. db connection
		
		flux.subscribe(System.out::println);
		

		System.out.println("-------------");
		/**
		 * The more advanced form of programmatic creation of a Flux, 
		 * create can work asynchronously or synchronously and is suitable for multiple emissions per round.
		 */
		/*Flux<String> bridge = Flux.create(sink -> {
		    myEventProcessor.register( 
		      new MyEventListener<String>() { 

		        public void onDataChunk(List<String> chunk) {
		          for(String s : chunk) {
		            sink.next(s); 
		          }
		        }

		        public void processComplete() {
		            sink.complete(); 
		        }
		    });
		});*/
		
		System.out.println("-------------");
		/**
		 * However, handle can be used to generate an arbitrary value out of each source element, 
		 * possibly skipping some elements. In this way, it can serve as a combination of map and filter.
		 */
		Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
			    .handle((i, sink) -> {
			        String letter = alphabet(i); 
			        if (letter != null) 
			            sink.next(letter); 
			    });

		alphabet.subscribe(System.out::println);
		
		
		/**
		 * Reactor offers two means of switching the execution context (or Scheduler) in a reactive chain: publishOn and subscribeOn. 
		 * Both take a Scheduler and let you switch the execution context to that scheduler. 
		 * But the placement of publishOn in the chain matters, while the placement of subscribeOn does not.
		 */
	}
	
	public static String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
                return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
	}
	
	interface MyEventListener<T> {
	    void onDataChunk(List<T> chunk);
	    void processComplete();
	}
}
