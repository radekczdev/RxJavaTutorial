package com.czajor.rxjavatutorial.basictests;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsingGenerateTestSuite {
    private String result = "";
    private String path = "test.txt";


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

    @Test
    void shouldReadFromFile_UsingFlowableGenerate() {
        System.out.println("--- USING FLOWABLE GENERATE TO READ FILE ---");
        Flowable<String> file = Flowable.generate(
                () -> new BufferedReader(new FileReader(path)),
                (reader, emitter) -> {
                    final String line = reader.readLine();
                    if(line != null) {
                        emitter.onNext(line);
                    } else {
                        emitter.onComplete();
                    }
                },
                BufferedReader::close
        );
        file.subscribe(
                line -> System.out.println(line),
                error -> System.out.println(error)
        );
        System.out.println("-----------------------------------------");
    }

    @Test
    void shouldReadFromFile_UsingObservableUsing() {
        System.out.println("--- USING FLOWABLE USING TO READ FILE ---");
        Observable<String> observable = Observable.using(
                () -> new BufferedReader(new FileReader(path)),
                reader -> Observable.fromIterable(() -> reader.lines().iterator()),
                reader -> reader.close()
        );
        observable.subscribe(
                line -> System.out.println(line),
                error -> System.out.println(error)
        );
        System.out.println("-----------------------------------------");
    }
}
