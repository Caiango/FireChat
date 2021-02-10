package com.example.firechat.model

data class User(val name: String, val bio: String, val profPicPath: String?) {
    constructor() : this("", "", null)
}