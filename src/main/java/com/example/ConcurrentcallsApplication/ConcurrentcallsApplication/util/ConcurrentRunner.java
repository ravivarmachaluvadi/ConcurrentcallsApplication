package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.util;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service.InvocationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//@Component
public class ConcurrentRunner/* implements CommandLineRunner*/ {

    @Autowired
    InvocationHelper invocationHelper;

   // @Override
    public void run(String... args) throws Exception {

        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<ResponseEntity>> allFutures = new ArrayList<>();

        for (int i = 1; i < 5001; i++) {
            allFutures.add(invocationHelper.getPhotoDTOAsync(i));
        }

        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

       /* for (int i = 0; i < 1000; i++) {
            System.out.println("response: " + allFutures.get(i).get().toString());
        }*/

        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());

    }
}