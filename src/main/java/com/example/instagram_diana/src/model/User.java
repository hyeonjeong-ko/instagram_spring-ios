package com.example.instagram_diana.src.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Entity
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 300)
    private String password;

    @Column(name = "name", length = 45) //nullable = false
    private String name;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Lob
    @Column(name = "profileUrl")
    private String profileUrl;

    @Column(name = "bio", length = 225)
    private String bio;

    @Column(name = "site", length = 225)
    private String site;

    @Column(name = "phone", length = 45)
    private String phone;

    @Column(name = "provider", nullable = false, length = 45)
    private String provider;

    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt",nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "status", length = 20, nullable = false)
    private String status;


    @Builder
    public User(String email,  String username, String name, String phone,String password,String provider) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.provider = provider;
        this.status = "ACTIVE";

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


}

/*
*
*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
* */