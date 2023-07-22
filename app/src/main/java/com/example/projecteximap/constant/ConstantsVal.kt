package com.example.projecteximap.constant

import io.grpc.Metadata

class ConstantsVal {

    companion object {

        private const val jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJuZXB0dW5lIiwianRpIjoiTnpZd05UZzJNREl4TXc9PSIsInN1YiI6ImRlZmF1bHQifQ.-mJMez-RtZA5IuZDKeMdS32yna-n1NGxkQVHqgLg-MQ"

        private val jwtTokenKey: Metadata.Key<String> = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)
        private const val jwtTokenValue =
            "Bearer $jwtToken"

        val metaData = Metadata().apply {
            put(jwtTokenKey, jwtTokenValue)
        }
    }

}