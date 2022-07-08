package com.itvaib.multidatasource.users;

import com.itvaib.multidatasource.users.entity.User;
import com.itvaib.multidatasource.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    User findUser(){
        return userRepository.findById(1).orElse(new User());
    }
}
