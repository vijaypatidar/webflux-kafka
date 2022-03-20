package com.example.demo.controllers

import com.example.demo.controllers.advices.ValidationException
import com.example.demo.models.User
import com.example.demo.services.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [UserController::class])
@Import(UserService::class)
class UserControllerTest : BaseControllerTest() {
    @MockBean
    lateinit var userService: UserService

    @Test
    fun createUserTest() {
        val user: User = readJsonAs("user.json", User::class.java)
        Mockito.`when`(userService.createUser(user)).thenReturn(Mono.just(user))
        webTestClient
            .post()
            .uri("/users")
            .bodyValue(user)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(objectMapper.writeValueAsString(user));

    }

    @Test
    fun conflictUserTest() {
        val user: User = readJsonAs("user.json", User::class.java)
        Mockito.`when`(userService.createUser(user)).thenReturn(
            Mono.error(
                ValidationException(
                    HttpStatus.CONFLICT,
                    "User already exists"
                )
            )
        )
        val expected = HashMap<String, Any>()
        expected["message"] = "User already exists"
        webTestClient
            .post()
            .uri("/users")
            .bodyValue(user)
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody().json(objectMapper.writeValueAsString(expected));

    }

    @Test
    fun getUserTest() {
        val user: User = readJsonAs("user.json", User::class.java)
        Mockito.`when`(userService.getUser(user.username)).thenReturn(Mono.just(user))

        webTestClient
            .get()
            .uri("/users/vijaypatidar")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(objectMapper.writeValueAsString(user));

    }


    @Test
    fun userNotFoundTest() {
        Mockito.`when`(userService.getUser("vijaypatidar")).thenReturn(
            Mono.error(
                ValidationException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
                )
            )
        )
        val map = HashMap<String, Any>()
        map["message"] = "User not found"
        webTestClient
            .get()
            .uri("/users/vijaypatidar")
            .exchange()
            .expectStatus().isNotFound
            .expectBody().json(objectMapper.writeValueAsString(map));

    }


}