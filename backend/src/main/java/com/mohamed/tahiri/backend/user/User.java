package com.mohamed.tahiri.backend.user;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String fullName;
    public String email;
    public String password;
    public String image;

    public List<Long> conversationsId;

}
