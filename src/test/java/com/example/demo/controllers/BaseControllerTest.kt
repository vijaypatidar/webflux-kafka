package com.example.demo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

abstract class BaseControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var objectMapper: ObjectMapper

    fun <T>readJsonAs(fileName: String, clazz: Class<T>): T {
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        return objectMapper.createParser(inputStream).readValueAs(clazz)
    }

    fun <T>readJsonListAs(fileName: String, clazz: Class<T>): MutableIterator<T>? {
        val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
        return objectMapper.createParser(inputStream).readValuesAs(clazz)
    }
}