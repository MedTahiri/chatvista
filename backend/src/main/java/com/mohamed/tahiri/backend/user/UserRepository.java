package com.mohamed.tahiri.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByFullNameContainsAndEmail(String fullName, String email);

    List<User> findAllByFullNameContainsOrEmailContains(String fullName, String email);
}
