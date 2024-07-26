package com.sout.controller;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @PostMapping
    void create(@RequestBody User user) {
    }

    @GetMapping("/me")
    void find(@RequestBody User user) {
    }

    @PatchMapping("/{id}")
    void modify(@PathVariable long id) {
    }

    @DeleteMapping
    void delete(@RequestBody User user) {
    }
}
