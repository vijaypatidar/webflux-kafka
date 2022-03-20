package com.example.demo.controllers

import com.example.demo.models.Post
import com.example.demo.services.PostService
import io.micrometer.core.annotation.Timed
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/posts")
@Timed("Post.Controller")
class PostController(private val postService: PostService):BaseController() {

    @PostMapping
    fun createPost(@RequestBody post: Post): Mono<Post> {
        return postService.createPost(post)
    }

    @GetMapping
    fun getPosts(
        @RequestParam(defaultValue = "5") pageSize: Int,
        @RequestParam(defaultValue = "1") pageNo: Int,
    ): Flux<Post> {
        return postService.getPosts(pageSize, pageNo)
    }
}