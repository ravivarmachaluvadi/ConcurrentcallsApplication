package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    @GetMapping("/message")
    @Timed(value = "get.message")
    public String getMessage() {
        return " Get Working...!!";
    }

    @PostMapping("/message")
    @Timed(value = "post.message")
    public String postMessage() {
        return " Post Working...!!";
    }
}
