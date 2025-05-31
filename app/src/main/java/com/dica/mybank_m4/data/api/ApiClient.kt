package com.dica.mybank_m4.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://683ac40f43bb370a8673abff.mockapi.io/api/v1/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val accountApi: AccountApi = retrofit.create(AccountApi::class.java)
}