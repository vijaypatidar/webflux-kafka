package com.example.demo.controllers

import org.springframework.web.server.ServerWebExchange

abstract class BaseController {

    protected fun getAuthToken(exchange:ServerWebExchange):String{
        throw java.lang.RuntimeException("getAuthToken not implemented")
    }
}