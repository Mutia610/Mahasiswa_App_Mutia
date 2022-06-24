package com.example.mahasiswa_app_mutia.Config

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkModule {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl("http://192.168.43.84/mentoring_kotlin_week4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun service() : ApiService = getRetrofit().create(ApiService::class.java)
}