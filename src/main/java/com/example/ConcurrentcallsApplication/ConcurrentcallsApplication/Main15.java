package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Main15 {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(500);

        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            Runnable worker = () -> {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(finalI +"   :   "+Thread.currentThread());
            };
            executorService.execute(worker);
        }

        System.out.println("Ravi From "+Thread.currentThread());
        executorService.shutdown();

    }
}
