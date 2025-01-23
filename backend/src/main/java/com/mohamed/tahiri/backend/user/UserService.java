package com.mohamed.tahiri.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User getUser(String email, String password) {
        for (int i = 0; i < allUsers().size(); i++) {
            if (allUsers().get(i).email.equals(email) && allUsers().get(i).password.equals(password)) {
                return allUsers().get(i);
            }
        }
        return new User();
    }

    public User newUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updateUser) {
        User user = getUserById(updateUser.id);
        user.fullName = updateUser.fullName;
        user.email = updateUser.email;
        //user.password = passwordEncoder.encode(updateUser.password);
        user.password = updateUser.password;
        user.image = updateUser.image;

        return userRepository.save(user);
    }
}
