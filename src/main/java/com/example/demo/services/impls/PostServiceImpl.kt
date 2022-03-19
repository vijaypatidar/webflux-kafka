package com.example.demo.services.impls

import com.example.demo.models.Post
import com.example.demo.services.PostService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PostServiceImpl:PostService{
    private val posts = ArrayList<Post>()

    init {
        for ( i in 1 until 1000)posts.add(Post(
            "post$i",
            "title $i",
            "some content",
            "user",
            null
        ))
    }
    override fun getPosts(pageSize: Int, pageNo: Int): Flux<Post> {
        val startIndex = pageSize*(pageNo-1);
        val endIndex = (startIndex + pageSize).coerceAtMost(posts.size);
        return Flux.create {
            if (posts.size>startIndex)posts.subList(startIndex,endIndex).forEach(it::next)
            it.complete();
        }
    }

    override fun createPost(post: Post): Mono<Post> {
        posts.add(post)
        return Mono.just(post)
    }
}