package com.czajor.rxjavatutorial;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class RxJavaTutorial {
    private static String[] titles = {"title"};
    private static List<String> titleList = Arrays.asList(titles);

    public static Observable<String> getTitle() {
        return Observable.fromArray(titles);
    }

    public static void main(String[] args) {

    }
}