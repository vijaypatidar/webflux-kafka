package com.example.demo.controllers

import com.example.demo.models.User
import com.example.demo.services.UserService
import io.micrometer.core.annotation.Timed
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
@Timed("UserController")
class UserController(private val userServices: UserService) : BaseController() {

    @PostMapping("")
    fun createUser(@RequestBody user: User, exchange:ServerWebExchange): Mono<User> {
        val authToken = getAuthToken(exchange)

        return userServices.createUser(user);
    }

    @PutMapping("")
    fun updateUser(@RequestBody user: User): Mono<User> {
        return userServices.updateUser(user);
    }

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): Mono<User> {
        return userServices.getUser(username)
    }


}