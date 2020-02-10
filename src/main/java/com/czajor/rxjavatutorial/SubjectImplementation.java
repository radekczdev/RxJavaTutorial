package com.czajor.rxjavatutorial;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SubjectImplementation {
    public static Integer subscriber1 = 0;
    public static Integer subscriber2 = 0;

    public static Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                subscriber1 += value;
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onComplete() {
                System.out.println("Subscriber1 completed");
            }
        };
    }

    public static Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                subscriber2 += value;
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onComplete() {
                System.out.println("Subscriber2 completed");
            }
        };
    }
}
