package com.dica.mybank_m4.data.model

data class Account(
    val id: String? = null,
    val name: String,
    val balance: String,
    val currency: String,
    val isActive: Boolean
)