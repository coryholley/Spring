package com.codeup.springproject.controllers;

import com.codeup.springproject.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String index(Model model) {
        ArrayList<Post> posts = new ArrayList<>();
        Post post1 = new Post("Day One", "This is day one.", 1);
        Post post2 = new Post("Day Two", "This is day two.", 2);
        posts.add(post1);
        posts.add(post2);
        model.addAttribute("posts", posts);
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
