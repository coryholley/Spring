package com.codeup.springproject.controllers;

import com.codeup.springproject.Post;
import com.codeup.springproject.repos.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {
    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String individualPost(@PathVariable long id, Model model) {
        Post post3 = new Post("Day three", "This is day three.", 3);
        id = post3.getId();
        model.addAttribute("post", post3);
        return "/posts/show";
    }

    @GetMapping("/posts/create")
    public String viewCreateForm() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(
            @RequestParam(name = "title-input") String title,
            @RequestParam(name = "body-input") String body
    ) {
        Post post = new Post(title, body);
        Post dbPost = postDao.save(post);
        return "Created a new Post with the id: " + dbPost.getId();
    }

}
