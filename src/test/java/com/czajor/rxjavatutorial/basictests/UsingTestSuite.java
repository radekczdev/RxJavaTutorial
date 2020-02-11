package com.czajor.rxjavatutorial.basictests;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsingTestSuite {
    private String result = "";

    @Test
    void shouldWriteResource() {
        Observable<Character> values = Observable.using(
                () -> "My Resource",
                r -> {return Observable.create(o -> {
                        for (Character c : r.toCharArray()) {
                            o.onNext(c);
                        }
                        o.onComplete();
                        });
                    },
                r -> System.out.println("Disposed: " + r));
        values.subscribe(
                v -> result += v,
                e -> result += e
        );
        assertEquals("My Resource", result);
    }
}
