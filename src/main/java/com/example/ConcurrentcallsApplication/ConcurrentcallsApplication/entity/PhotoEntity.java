package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PHOTO_TB")
public class PhotoEntity {

private Integer albumId;

@Id
private Integer id;

private String title;

private String url;

private String thumbnailUrl;

}