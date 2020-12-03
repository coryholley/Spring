package com.codeup.springproject.models;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @OneToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<PostImage> images;

    public Post() { }

    //READ
    public Post(String title, String body, long id, User owner, List<PostImage> images) {
        this.title = title;
        this.body = body;
        this.id = id;
        this.owner = owner;
        this.images = images;
    }

    // CREATE
    public Post(String title, String body, User owner, List<PostImage> images) {
        this.title = title;
        this.body = body;
        this.owner = owner;
        this.images = images;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<PostImage> getImages() {
        return images;
    }

    public void setImages(List<PostImage> images) {
        this.images = images;
    }

}
