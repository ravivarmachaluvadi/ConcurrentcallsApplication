package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Geo {

@SerializedName("lat")
@Expose
private String lat;
@SerializedName("lng")
@Expose
private String lng;

}