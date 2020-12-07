package com.codeup.springproject.repos;

import com.codeup.springproject.models.Post;
import com.codeup.springproject.models.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<PostImage, Long> {

}
