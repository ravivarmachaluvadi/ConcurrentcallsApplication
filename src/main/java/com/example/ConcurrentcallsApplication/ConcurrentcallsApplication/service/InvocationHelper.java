package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto.Photo;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto.User;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class InvocationHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    PhotoRepository photoRepository;

    @Async
    public CompletableFuture<ResponseEntity> getUserDTO(Integer id) throws InterruptedException {
        ResponseEntity<User> responseEntity= new ResponseEntity("Custom Response", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Thread.sleep(2000);

        // URI (URL) parameters
        Map<String, Integer> uriParams = new HashMap<>();
        uriParams.put("id",id);

        // Query parameters
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String unresolvedUrl = "https://jsonplaceholder.typicode.com/users/{id}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(unresolvedUrl).queryParams(queryParams);
        String resolvedUrl = builder.buildAndExpand(uriParams).toUriString();

        //Setting Up Headers
        HttpHeaders httpHeaders = new HttpHeaders();

        List<MediaType> mediaTypeList = new ArrayList<>();

        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);
        HttpEntity<String> requestEntity = new HttpEntity<>("", httpHeaders);

        try {
            responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,User.class);
            //responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,String.class);
        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }


    public ResponseEntity getUserDTOBlockinfCall(Integer id) throws InterruptedException {
        ResponseEntity<User> responseEntity= new ResponseEntity("Custom Response", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        Thread.sleep(2000);

        // URI (URL) parameters
        Map<String, Integer> uriParams = new HashMap<>();
        uriParams.put("id",id);

        // Query parameters
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String unresolvedUrl = "https://jsonplaceholder.typicode.com/users/{id}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(unresolvedUrl).queryParams(queryParams);
        String resolvedUrl = builder.buildAndExpand(uriParams).toUriString();

        //Setting Up Headers
        HttpHeaders httpHeaders = new HttpHeaders();

        List<MediaType> mediaTypeList = new ArrayList<>();

        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);
        HttpEntity<String> requestEntity = new HttpEntity<>("", httpHeaders);

        try {
            responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,User.class);
            //responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,String.class);
        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return responseEntity;
    }



    @Async
    public CompletableFuture<ResponseEntity> getPhotoDTOAsync(Integer id) throws InterruptedException {
        ResponseEntity<Photo> responseEntity= new ResponseEntity("Custom Response", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        // URI (URL) parameters
        Map<String, Integer> uriParams = new HashMap<>();
        uriParams.put("id",id);

        // Query parameters
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String unresolvedUrl = "https://jsonplaceholder.typicode.com/photos/{id}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(unresolvedUrl).queryParams(queryParams);
        String resolvedUrl = builder.buildAndExpand(uriParams).toUriString();

        System.out.println(" Thread name : "+Thread.currentThread().getName()+"  "+resolvedUrl );

        //Setting Up Headers
        HttpHeaders httpHeaders = new HttpHeaders();

        List<MediaType> mediaTypeList = new ArrayList<>();

        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);
        HttpEntity<String> requestEntity = new HttpEntity<>("", httpHeaders);

        try {
            responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,Photo.class);

            Photo photo= responseEntity.getBody();

            photoRepository.save(photo);

            //responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,String.class);
        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }



    @Async
    public CompletableFuture<Optional<Photo>> getDBPhotoDTOAsync(Integer id) throws InterruptedException {

        Optional<Photo> byId = photoRepository.findById(id);

        log.info(" Record fetched with id {} ",byId.get().getId());

        return CompletableFuture.completedFuture(byId);
    }


    public ResponseEntity getPhotoDTOBlockinfCall(Integer id) throws InterruptedException {
        ResponseEntity<Photo> responseEntity= new ResponseEntity("Custom Response", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        //Thread.sleep(2000);

        // URI (URL) parameters
        Map<String, Integer> uriParams = new HashMap<>();
        uriParams.put("id",id);

        // Query parameters
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String unresolvedUrl = "https://jsonplaceholder.typicode.com/photos/{id}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(unresolvedUrl).queryParams(queryParams);
        String resolvedUrl = builder.buildAndExpand(uriParams).toUriString();

        System.out.println(" Thread name : "+Thread.currentThread().getName()+"  "+resolvedUrl );

        //Setting Up Headers
        HttpHeaders httpHeaders = new HttpHeaders();

        List<MediaType> mediaTypeList = new ArrayList<>();

        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);
        HttpEntity<String> requestEntity = new HttpEntity<>("", httpHeaders);

        try {
            responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,Photo.class);
            //responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,String.class);
        } catch (Exception e) {
            log.error("user not found : id = {} " ,id);
        }
        return responseEntity;
    }


    @Async
    public CompletableFuture<ResponseEntity> spinAsync(Integer id) throws InterruptedException {
        ResponseEntity<Void> responseEntity= new ResponseEntity("Custom Response", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        //Thread.sleep(2000);

        try {
        log.info(" Thread name : "+Thread.currentThread().getName()+" "+ "https://jsonplaceholder.typicode.com/photos/"+id );
        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

}