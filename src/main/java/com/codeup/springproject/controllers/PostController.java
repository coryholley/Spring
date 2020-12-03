package com.codeup.springproject.controllers;

import com.codeup.springproject.models.Post;
import com.codeup.springproject.models.PostImage;
import com.codeup.springproject.models.User;
import com.codeup.springproject.repos.PostRepository;
import com.codeup.springproject.repos.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }

    @GetMapping("/posts/")
    public String individualPost(@RequestParam(name = "id") long id, Model model) {
        Post post = postDao.getOne((long) id);
        model.addAttribute("post", post);
        return "/posts/show";
    }

    @GetMapping("/posts/create")
    public String viewCreateForm() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(
            @RequestParam(name = "title-input") String title,
            @RequestParam(name = "body-input") String body
    ) {
        User user = userDao.getOne(1L);
        Post post = new Post(title, body, user, null);
        Post dbPost = postDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit")
    public String viewEditForm(@RequestParam(name = "id") long id, Model model) {
        Post post = postDao.getOne((long) id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String submitEditForm(
            @RequestParam(name = "title-input") String title,
            @RequestParam(name = "body-input") String body,
            @PathVariable long id
    ) {
        Post dbPost = postDao.getOne(id);
        dbPost.setTitle(title);
        dbPost.setBody(body);
        postDao.save(dbPost);
        return "redirect:/posts";
    }

    @GetMapping("/posts/delete")
    public String deletePost(@RequestParam(name = "id") long id, Model model) {
        postDao.deleteById((long) id);
        return "redirect:/posts";
    }

}
