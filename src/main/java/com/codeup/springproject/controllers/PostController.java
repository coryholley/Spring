package com.codeup.springproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String index() {
        return "Posts index page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String individualPost(@PathVariable long id) {
        return "View an individual post with id: " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String viewCreateForm() {
        return "View the form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "Create a new post";
    }

}
