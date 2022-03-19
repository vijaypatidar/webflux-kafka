package com.example.demo.exceptions

class UserNotFoundException(username:String) :Exception("User not found : $username")
