package com.example.demo.services

import com.example.demo.models.User
import reactor.core.publisher.Mono

interface UserService {
    fun createUser(user: User): Mono<User>

    fun updateUser(user: User): Mono<User>

    fun getUser(username: String): Mono<User>
}
