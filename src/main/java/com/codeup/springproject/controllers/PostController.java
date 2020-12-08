package com.codeup.springproject.controllers;

import com.codeup.springproject.models.Post;
import com.codeup.springproject.models.PostImage;
import com.codeup.springproject.models.User;
import com.codeup.springproject.repos.ImageRepository;
import com.codeup.springproject.repos.PostRepository;
import com.codeup.springproject.repos.UserRepository;
import com.codeup.springproject.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final ImageRepository imageDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, ImageRepository imageDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.imageDao = imageDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/")
    public String individualPost(@RequestParam(name = "id") long id, Model model) {
        Post post = postDao.getOne((long) id);
        model.addAttribute("post", post);
        boolean hasPrevious = postDao.existsById(post.getId() - 1);
        boolean hasNext = postDao.existsById(post.getId() + 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        return "posts/show";
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
    public String createPost(
            @ModelAttribute Post postToBeSaved,
            @RequestParam(name = "image-input", required = false) String imagePath,
            @RequestParam(name = "image-input1", required = false) String imagePath1,
            @RequestParam(name = "image-input2", required = false) String imagePath2
            ) {
        List<PostImage> newImages = new ArrayList<>();
        if (imagePath.equals("") || imagePath1.equals("") || imagePath2.equals("")) {
            imagePath = "https://images.pexels.com/photos/338504/pexels-photo-338504.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500";
            PostImage imageOne = new PostImage(imagePath, postToBeSaved);
            imagePath1 = "https://images.pexels.com/photos/413960/pexels-photo-413960.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500";
            PostImage imageTwo = new PostImage(imagePath1, postToBeSaved);
            imagePath2 = "https://images.pexels.com/photos/1004584/pexels-photo-1004584.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500";
            PostImage imageThree = new PostImage(imagePath2, postToBeSaved);
            newImages.add(imageOne);
            newImages.add(imageTwo);
            newImages.add(imageThree);
        } else {
            PostImage imageOne = new PostImage(imagePath, postToBeSaved);
            newImages.add(imageOne);
            PostImage imageTwo = new PostImage(imagePath1, postToBeSaved);
            newImages.add(imageTwo);
            PostImage imageThree = new PostImage(imagePath2, postToBeSaved);
            newImages.add(imageThree);
        }

        postToBeSaved.setImages(newImages);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeSaved.setOwner(user);
        Post dbPost = postDao.save(postToBeSaved);
        emailService.prepareAndSend(dbPost, "Ad has been created", "You can find it with the id of " + dbPost.getId());
        return "redirect:posts";
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeUpdated.setOwner(user);
        postDao.save(postToBeUpdated);
        return "redirect:posts";
    }

    @GetMapping("/posts/delete")
    public String deletePost(@RequestParam(name = "id") long id, Model model) {
        postDao.deleteById((long) id);
        return "redirect:posts";
    }

}
