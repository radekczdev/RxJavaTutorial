package com.czajor.rxjavatutorial.basictests;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.czajor.rxjavatutorial.RxJavaTutorial.getTitle;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ObservableTestSuite {

    private String result = "";
    private String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
    private String[] abc = {"a", "b", "c"};
    private Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private int sum = 0;
    private String even = "";
    private String odd = "";

    @Test
    void shouldEmitOneGenericInstance() {
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(s -> result = s);
        assertEquals("Hello", result);
    }

    @Test
    void shouldEmitAnArrayOfLetter() {
        Observable<String> observable = Observable.fromArray(letters);
        observable.subscribe(
                i -> result += i,
                Throwable::printStackTrace,
                () -> result += "_Completed"
        );
        assertEquals("abcdefg_Completed", result);
    }

    @Test
    void shouldChangeResultFromObservable() {
        Observable.fromArray(letters)
                .map(String::toUpperCase)
                .subscribe(letter -> result += letter);
        assertEquals("ABCDEFG", result);
    }

    @Test
    void shouldMapAllElementsUsingFlatMap() {
        Observable.just("book1", "book2")
                .flatMap(s -> getTitle())
                .subscribe(l -> result += l);
        assertEquals("titletitle", result);
    }

    @Test
    void shouldAppendNextElementToTheResultAndAddItToTheResult () {
        Observable.fromArray(abc)
                .scan(new StringBuilder(), StringBuilder::append)
                .subscribe(total -> result += total.toString());
        assertEquals("aababc", result);
    }

    @Test
    void shouldSumAllElementsBeingEmited () {
        List<Integer> numbersList = Arrays.asList(numbers);
        int expected = ((numbers[0] + numbers[numbers.length - 1]) * numbers.length) / 2;
        Observable.fromIterable(numbersList)
                .scan(0, Integer::sum)
                .subscribe(a -> {
                    sum = a;
                    System.out.println(sum);
                });
        assertEquals(expected, sum);
    }

    @Test
    void shouldGroupNumbersByOddEven() {
        Observable.fromArray(numbers)
                .groupBy(a -> (a % 2) == 0 ? "EVEN" : "ODD")
                .subscribe(group ->
                        group.subscribe(number -> {
                            if (group.getKey().equals("EVEN")) {
                                even += number.toString();
                            } else {
                                odd += number.toString();
                            }
                        })
                );
        assertEquals("246810", even);
        assertEquals("13579", odd);
    }

    @Test
    void shouldFilterOnlyEvenNumbers() {
        Observable.fromArray(numbers)
                .filter(a -> (a % 2) == 0)
                .subscribe(a -> even += a);
        assertEquals("246810", even);
    }

    @Test
    void shouldInformAboutEmptyObservable() {
        String info = "Observable is Empty";
        Observable.empty()
                .defaultIfEmpty(info)
                .subscribe(s -> result += s);
        assertEquals(info, result);
    }

    @Test
    void shouldReturnFirstElementOnly() {
        String info = "Observable is Empty";
        Observable.fromArray(letters)
                .first(info)
                .subscribe(s -> result += s);
        assertEquals(letters[0], result);
    }

    @Test
    void shouldReadElementsWhileNextEquals5() {
        Observable.fromArray(numbers)
                .takeWhile(a -> a < 6)
                .subscribe(a -> result = a.toString());
        assertEquals("5", result);
    }


}
