package com.czajor.rxjavatutorial.basictests;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import org.junit.jupiter.api.Test;

import static com.czajor.rxjavatutorial.SubjectImplementation.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SubjectTestSuite {
    @Test
    void shouldEmitToTwoSubscribers() {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.subscribe(getFirstObserver());
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.subscribe(getSecondObserver());
        subject.onNext(4);
        subject.onComplete();

        assertEquals(14, subscriber1 + subscriber2);
    }
}
