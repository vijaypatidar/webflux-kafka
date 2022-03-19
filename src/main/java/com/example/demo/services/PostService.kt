package com.example.demo.services

import com.example.demo.models.Post
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PostService {
    fun getPosts(pageSize: Int, pageNo: Int): Flux<Post>
    fun createPost(post: Post): Mono<Post>
}