package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Company {

@SerializedName("name")
@Expose
private String name;
@SerializedName("catchPhrase")
@Expose
private String catchPhrase;
@SerializedName("bs")
@Expose
private String bs;


}