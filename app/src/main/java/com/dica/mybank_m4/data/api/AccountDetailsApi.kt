package com.dica.mybank_m4.data.api

import com.dica.mybank_m4.data.model.Account
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountDetailsApi {

    @GET("accounts/{id}")
    suspend fun getAccountDetails(@Path("id") id: Int): Account

    @PUT("accounts/{id}")
    suspend fun updateAccountFully(@Path("id") id: Int, @Body account: Account)

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(@Path("id") id: Int)
}