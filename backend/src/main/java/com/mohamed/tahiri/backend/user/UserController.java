package com.mohamed.tahiri.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.allUsers();
    }

    @GetMapping("/{email}/{password}")
    public User getUser(@PathVariable("email") String email, @PathVariable("password") String password) {
        return userService.getUser(email, password);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/new")
    public User newUser(@RequestBody User user){
        return userService.newUser(user);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
