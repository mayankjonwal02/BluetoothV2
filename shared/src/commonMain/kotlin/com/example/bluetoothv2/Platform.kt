package com.example.bluetoothv2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform