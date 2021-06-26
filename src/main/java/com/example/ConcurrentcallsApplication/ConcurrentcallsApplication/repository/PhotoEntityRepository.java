package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.repository;

import com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoEntityRepository extends JpaRepository<PhotoEntity, Integer> {

}
