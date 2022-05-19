package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.controller;


import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto.Photo;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.entity.PhotoEntity;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.repository.PhotoEntityRepository;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service.InvocationHelper;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service.OraInvocationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class OraDBController {

    @Autowired
    OraInvocationHelper invocationHelper;

    @Autowired
    PhotoEntityRepository photoEntityRepository;

    @PostMapping("/ora/aynsc/photos")
    public void getPhotosInAsync() throws InterruptedException {
        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<ResponseEntity>> allFutures = new ArrayList<>();

        for (int i = 1; i < 5001; i++) {
            allFutures.add(invocationHelper.postPhotoDTOAsync(i));
        }

        //CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

       /* for (int i = 0; i < 1000; i++) {
            System.out.println("response: " + allFutures.get(i).get().toString());
        }*/

        log.info("Total time: " + Duration.between(start, Instant.now()).getSeconds());

    }

    @PostMapping("/ora/photo/{id}")
    public void postPhotoInAsync( @PathVariable Integer id ) throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();
        invocationHelper.postPhotoDTOAsync(id);
        log.info("Total time: " + Duration.between(start, Instant.now()).getSeconds());

    }

    @GetMapping("ora/aynsc/photos")
    public void getDBPhotosInAsync() throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<Optional<PhotoEntity>>> allFutures = new ArrayList<>();

        for (int i = 1; i < 5001; i++) {
            allFutures.add(invocationHelper.getDBPhotoDTOAsync(i));
        }

        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

        for (int i = 0; i < 5000; i++) {
            log.info(" Record fetched with id {} ",allFutures.get(i).get().get().getId());
        }

        log.info("Total time: " + Duration.between(start, Instant.now()).getSeconds());

    }

    @GetMapping("ora/aynsc/photo/{id}")
    public ResponseEntity getOraPhotoInAsync(@PathVariable Integer id) throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();

        CompletableFuture<Optional<PhotoEntity>> dbPhotoDTOAsync = invocationHelper.getDBPhotoDTOAsync(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(dbPhotoDTOAsync.get().get());

    }

}
