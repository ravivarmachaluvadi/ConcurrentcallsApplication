package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.repository;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto.Photo;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,Integer> {

}