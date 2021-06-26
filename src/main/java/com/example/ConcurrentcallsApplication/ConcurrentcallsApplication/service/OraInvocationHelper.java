package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.service;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto.Photo;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.entity.PhotoEntity;
import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.repository.PhotoEntityRepository;
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
public class OraInvocationHelper {

    @Autowired
    PhotoEntityRepository photoEntityRepository;

    @Autowired
    RestTemplate restTemplate;


    @Async
    public CompletableFuture<ResponseEntity> postPhotoDTOAsync(Integer id) throws InterruptedException {
        ResponseEntity<Photo> responseEntity= new ResponseEntity("Custom Response", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        // URI (URL) parameters
        Map<String, Integer> uriParams = new HashMap<>();
        uriParams.put("id",id);

        // Query parameters
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String unresolvedUrl = "https://jsonplaceholder.typicode.com/photos/{id}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(unresolvedUrl).queryParams(queryParams);
        String resolvedUrl = builder.buildAndExpand(uriParams).toUriString();

        log.info(" Thread name : "+Thread.currentThread().getName()+"  "+resolvedUrl );

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

            PhotoEntity photoEntity= new PhotoEntity();

            photoEntity.setAlbumId(photo.getAlbumId());
            photoEntity.setId(photo.getId());
            photoEntity.setTitle(photo.getTitle());
            photoEntity.setThumbnailUrl(photo.getThumbnailUrl());
            photoEntity.setUrl(photo.getUrl());

            photoEntityRepository.save(photoEntity);

        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async
    public CompletableFuture<Optional<PhotoEntity>> getDBPhotoDTOAsync(Integer id) throws InterruptedException {

        Optional<PhotoEntity> byId = photoEntityRepository.findById(id);

        log.info(" Record fetched with id {} ",byId.get().getId());

        return CompletableFuture.completedFuture(byId);
    }

}
