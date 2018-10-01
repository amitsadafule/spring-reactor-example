package com.amits.practice.core;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

/**
 * @author amits1
 *
 * 12-Sep-2018
 */
public class SimpleSubscriber<T> extends BaseSubscriber<T>{
	
	@Override
	public void hookOnSubscribe(Subscription subscription) {
		System.out.println("SimpleSubscriber subscribed");
		request(1);
	}

	@Override
	public void hookOnNext(T value) {
		System.out.println("SimpleSubscriber printing next : "+ value);
		request(2);
	}
}
