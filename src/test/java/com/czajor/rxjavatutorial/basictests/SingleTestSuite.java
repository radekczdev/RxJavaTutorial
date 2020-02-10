package com.czajor.rxjavatutorial.basictests;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleTestSuite {
    private String result = "";
    @Test
    void shouldEmitOneValue() {

        Single<String> single = Observable.just("Hello")
                .singleElement()
                .toSingle()
                .doOnSuccess(i -> result += i)
                .doOnError(error -> {
                    throw new RuntimeException(error.getMessage());
                });
        single.subscribe();
        assertEquals("Hello", result);
    }
}
