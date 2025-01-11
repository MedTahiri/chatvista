package com.mohamed.tahiri.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("user/users")
    public List<User> allUsers() {
        return userService.allUsers();
    }

    @GetMapping("user/random")
    public User randomUser() {
        User user = new User("Mohamed Tahiri", "mohamedttaahhiirrii2003@gmail.com", "12345678");
        return userService.createUser(user);
    }

}
