package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class ExecutorController {

    @GetMapping("/executor/request")
    public String getPhotosInAsync() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        //ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable task1 = new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("My Task1 started..");
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("My Task1 ended..");
            }
        };

        Runnable task2 = new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("My Task2 started..");
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("My Task2 ended..");
            }
        };


        Runnable task3 = new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("My Task3 started..");
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("My Task3 ended..");
            }
        };

        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);

        executorService.shutdown();
         return "Hello , World";
    }

}
