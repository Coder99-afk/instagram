package com.wbd.Instagram.service;

import com.wbd.Instagram.model.Comment;
import com.wbd.Instagram.model.Post;
import com.wbd.Instagram.model.User;
import com.wbd.Instagram.repository.CommentRepository;
import com.wbd.Instagram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public Comment addComment(Long postId, String content, User user){
        Post post= postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        Comment comment= new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }
    public void deleteComment(Long commentId, User user){
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment not found"));
        //Checking if user is deleting it's own comment only
        if(!comment.getUser().equals(user)){
            throw  new RuntimeException("You are not authorised to delete this comment");
        }
        commentRepository.delete(comment);
    }
}