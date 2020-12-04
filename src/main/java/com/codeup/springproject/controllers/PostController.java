package com.codeup.springproject.controllers;

import com.codeup.springproject.models.Post;
import com.codeup.springproject.models.PostImage;
import com.codeup.springproject.models.User;
import com.codeup.springproject.repos.PostRepository;
import com.codeup.springproject.repos.UserRepository;
import com.codeup.springproject.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
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
        boolean hasPrevious = postDao.existsById(post.getId() - 1);
        boolean hasNext = postDao.existsById(post.getId() + 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        return "/posts/show";
    }

    @PostMapping("/posts/search")
    public String viewSearchedPosts(@RequestParam(name = "searchTerm") String searchTerm, Model viewModel) {
        searchTerm = "%"+searchTerm+"%";
        List<Post> dbPosts = postDao.findAllByTitleIsLike(searchTerm);
        viewModel.addAttribute("posts", dbPosts);
        return "posts/search";
    }

    @GetMapping("/posts/create")
    public String viewCreateForm(Model viewModel) {
        viewModel.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post postToBeSaved) {
        User userDb = userDao.getOne(1L);
        postToBeSaved.setOwner(userDb);
        Post dbPost = postDao.save(postToBeSaved);
        emailService.prepareAndSend(dbPost, "Ad has been created", "You can find it with the id of " + dbPost.getId());
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit")
    public String viewEditForm(@RequestParam(name = "id") long id, Model viewModel) {
        Post post = postDao.getOne((long) id);
        viewModel.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String submitEditForm(
            @PathVariable long id,
            @ModelAttribute Post postToBeUpdated
    ) {
        User user = userDao.getOne(1L);
        postToBeUpdated.setOwner(user);
        postDao.save(postToBeUpdated);
        return "redirect:/posts";
    }

    @GetMapping("/posts/delete")
    public String deletePost(@RequestParam(name = "id") long id, Model model) {
        postDao.deleteById((long) id);
        return "redirect:/posts";
    }

}
