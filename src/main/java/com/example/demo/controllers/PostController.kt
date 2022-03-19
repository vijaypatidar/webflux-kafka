package com.example.demo.controllers

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import com.example.demo.services.PostService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.example.demo.models.Post
import reactor.core.publisher.Mono
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/posts")

class PostController(private val postService: PostService):BaseController() {

    @PostMapping
    fun createPost(@RequestBody post: Post?): Mono<Post> {
        return postService.createPost(post!!)
    }

    @GetMapping
    fun getPosts(
        @RequestParam(defaultValue = "5") pageSize: Int,
        @RequestParam(defaultValue = "1") pageNo: Int,
    ): Flux<Post> {
        return postService.getPosts(pageSize, pageNo)
    }
}