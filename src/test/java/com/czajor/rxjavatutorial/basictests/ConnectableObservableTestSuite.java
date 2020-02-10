package com.czajor.rxjavatutorial.basictests;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ConnectableObservableTestSuite {
    @Test
    void shouldEmittItemsAfterConnecting() throws InterruptedException {
        String[] result = {""};
        ConnectableObservable<Long> connectable = Observable.interval(200, TimeUnit.MICROSECONDS).publish();
        connectable.subscribe(i -> result[0] += i.toString());
        assertNotEquals("01", result[0]);

        connectable.connect();
        Thread.sleep(500);
        assertEquals("01", result[0].substring(0, 2));
    }
}
