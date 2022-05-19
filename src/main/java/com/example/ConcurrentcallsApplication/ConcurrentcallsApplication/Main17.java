package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main17 {
    public static void main(String[] args) throws InterruptedException {
        List<CompletableFuture<String>> list=new ArrayList<>();
        long ms1=System.currentTimeMillis();
        System.out.println(ms1);
        System.out.println();
        for (int i = 1; i < 500; i++) {
            int finalI = i;
            CompletableFuture<String> worker = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "String : "+finalI+"  "+Thread.currentThread();
            });
            list.add(worker);
        }
        System.out.println(System.currentTimeMillis()-ms1);
        System.out.println();
        System.out.println("Ravi From "+Thread.currentThread());
        long ms2 = System.currentTimeMillis();
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
        //CompletableFuture.anyOf(list.toArray(new CompletableFuture[0])).join();
        list.forEach(stringCompletableFuture -> {
            try {
                System.out.println(stringCompletableFuture.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(System.currentTimeMillis()-ms2);
    }
}
