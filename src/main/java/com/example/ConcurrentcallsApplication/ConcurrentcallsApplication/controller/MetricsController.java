package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.controller;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service.InvocationHelper;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@Slf4j
public class MetricsController {

    @Autowired
    InvocationHelper invocationHelper;

    @GetMapping("/message")
    @Timed(value = "get.message")
    public String getMessage() {
        log.info("getMessage invoked");
        return " Get Working...!!";
    }

    @PostMapping("/message")
    @Timed(value = "post.message")
    public String postMessage() {
        log.info("postMessage invoked");
        return " Post Working...!!";
    }

    @GetMapping("/block/photo/{id}")
    public Object getPhotosNonAsync(@PathVariable Integer id) throws InterruptedException {
        //non-async calls
        Instant start = Instant.now();

        ResponseEntity photoDTOBlockinfCall = invocationHelper.getPhotoDTOBlockinfCall(id);

        log.info("user found : " + photoDTOBlockinfCall.getBody());

        long seqTime=  Duration.between(start, Instant.now()).getSeconds();
        System.out.println("Total time: " + seqTime);

        return photoDTOBlockinfCall.getBody();

    }
}
