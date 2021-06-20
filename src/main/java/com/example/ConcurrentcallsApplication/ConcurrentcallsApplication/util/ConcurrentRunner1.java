package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.util;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service.InvocationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//@Component
public class ConcurrentRunner1 /*implements CommandLineRunner*/ {

    @Autowired
    InvocationHelper invocationHelper;

    //@Override
    public void run(String... args) throws Exception {

        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<ResponseEntity>> allFutures = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            allFutures.add(invocationHelper.getUserDTO(i));
        }

        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

        for (int i = 0; i < 10; i++) {
            System.out.println("response: " + allFutures.get(i).get().toString());
        }

        long asyncTime=  Duration.between(start, Instant.now()).getSeconds();
        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());

        System.out.println("===============================================================");

        Instant start1 = Instant.now();
        List<ResponseEntity> allUsers = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            allUsers.add(invocationHelper.getUserDTOBlockinfCall(i));
        }

        for (ResponseEntity user  : allUsers) {
            System.out.println("response: " + user);
        }
        long seqTime=  Duration.between(start, Instant.now()).getSeconds();
        System.out.println("Total time: " + Duration.between(start1, Instant.now()).getSeconds());

        System.out.println(" seqTime-asyncTime "+(seqTime-asyncTime));

        System.out.println("===============================================================");


    }
}