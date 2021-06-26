package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.controller;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto.Photo;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.repository.PhotoRepository;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service.InvocationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class Controller {

    @Autowired
    PhotoRepository photoRepository;

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


    @GetMapping("/mongo/aynsc/photo/{id}")
    public ResponseEntity getMongoPhotoInAsync(@PathVariable Integer id) throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();

        CompletableFuture<Optional<Photo>> dbPhotoDTOAsync = invocationHelper.getDBPhotoDTOAsync(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(dbPhotoDTOAsync.get().get());

    }


    @GetMapping("/db/aynsc/photos")
    public void getDBPhotosInAsync() throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<Optional<Photo>>> allFutures = new ArrayList<>();

        for (int i = 1; i < 5001; i++) {
            allFutures.add(invocationHelper.getDBPhotoDTOAsync(i));
        }

        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

        for (int i = 0; i < 5000; i++) {
            log.info(" Record fetched with id {} ",allFutures.get(i).get().get().getId());
        }

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

    @PostMapping("/photo/{id}")
    public void postPhotoInAsync( @PathVariable  Integer id ) throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();

        CompletableFuture<ResponseEntity> photoDTOAsync1 = invocationHelper.getPhotoDTOAsync(id);

         Photo photo = (Photo) photoDTOAsync1.get().getBody();

        photoRepository.save(photo);

        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());

    }

}
