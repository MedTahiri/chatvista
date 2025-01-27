package com.mohamed.tahiri.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    public User getUser(String email, String password) {
        for (int i = 0; i < allUsers().size(); i++) {
            if (allUsers().get(i).getEmail().equals(email) && allUsers().get(i).getPassword().equals(password)) {
                return allUsers().get(i);
            }
        }
        return new User();
    }

    public User newUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updateUser) {
        User user = getUserById(updateUser.getId());
        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        user.setImage(updateUser.getImage());

        return userRepository.save(user);
    }
}
