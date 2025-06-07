package com.dica.mybank_m4.data.api

import com.dica.mybank_m4.data.model.Account
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountDetailsApi {

    @GET("accounts/{id}")
    suspend fun getAccountDetails(@Path("id") id: String): Account


}