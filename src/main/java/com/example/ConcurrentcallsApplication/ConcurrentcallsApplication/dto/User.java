package com.example.ConcurrentcallsApplication.ConcurrentcallsApplication.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("username")
@Expose
private String username;
@SerializedName("email")
@Expose
private String email;
@SerializedName("address")
@Expose
private Address address;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("website")
@Expose
private String website;
@SerializedName("company")
@Expose
private Company company;

}