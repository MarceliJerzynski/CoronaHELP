package com.example.coronahelp.model

import java.time.LocalDateTime

data class Person(
    val name: String,
    val lastName: String,
    val timeOfCreatingAccount: LocalDateTime
)