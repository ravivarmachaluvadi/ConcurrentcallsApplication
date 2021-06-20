package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.controller;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service.InvocationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    @Autowired
    InvocationHelper invocationHelper;


    @GetMapping("/aynsc/photos")
    public void getPhotosInAsync() throws InterruptedException {
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

    @GetMapping("/aynsc/spin")
    public void getSpinInAsync() throws InterruptedException {
        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<ResponseEntity>> allFutures = new ArrayList<>();

        for (int i = 1; i < 5001; i++) {
            allFutures.add(invocationHelper.spinAsync(i));
        }
        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

       /* for (int i = 0; i < 1000; i++) {
            System.out.println("response: " + allFutures.get(i).get().toString());
        }*/
        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());
    }

    @GetMapping("/block/photos")
    public void getPhotosNonAsync() throws InterruptedException {
        //non-async calls
        Instant start = Instant.now();
        List<ResponseEntity> allUsers = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            allUsers.add(invocationHelper.getPhotoDTOBlockinfCall(i));
        }

        /*for (ResponseEntity user  : allUsers) {
            System.out.println("response: " + user);
        }*/
        long seqTime=  Duration.between(start, Instant.now()).getSeconds();
        System.out.println("Total time: " + seqTime);

    }


    @GetMapping("/aynsc/block/photo")
    public void getPhotosInAsyncBlock() throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();

        CompletableFuture<ResponseEntity> photoDTOAsync1 = invocationHelper.getPhotoDTOAsync(1);

        ResponseEntity responseEntity1 = photoDTOAsync1.get();

        CompletableFuture<ResponseEntity> photoDTOAsync2 = invocationHelper.getPhotoDTOAsync(2);

        ResponseEntity responseEntity2 = photoDTOAsync2.get();

        System.out.println(responseEntity1.getBody());
        System.out.println(responseEntity2.getBody());

        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());

    }


    @GetMapping("/aynsc/photo")
    public void getPhotoInAsync() throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();

        CompletableFuture<ResponseEntity> photoDTOAsync1 = invocationHelper.getPhotoDTOAsync(1);
        CompletableFuture<ResponseEntity> photoDTOAsync2 = invocationHelper.getPhotoDTOAsync(2);

        ResponseEntity responseEntity1 = photoDTOAsync1.get();
        ResponseEntity responseEntity2 = photoDTOAsync2.get();

        System.out.println(responseEntity1.getBody());
        System.out.println(responseEntity2.getBody());

        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());

    }


}
