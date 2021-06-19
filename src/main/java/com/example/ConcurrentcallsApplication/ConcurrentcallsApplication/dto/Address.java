package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Address {

@SerializedName("street")
@Expose
private String street;
@SerializedName("suite")
@Expose
private String suite;
@SerializedName("city")
@Expose
private String city;
@SerializedName("zipcode")
@Expose
private String zipcode;
@SerializedName("geo")
@Expose
private Geo geo;


}