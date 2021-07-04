package com.kaleichyk.firstapplication

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}