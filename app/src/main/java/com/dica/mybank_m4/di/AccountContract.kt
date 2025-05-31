package com.dica.mybank_m4.di

import com.dica.mybank_m4.data.model.Account

interface AccountContract {
    interface View {

        fun showAccounts(accounts: List<Account>)

        fun showError(message: String)

        fun showSuccess(message: String)
    }

    interface Presenter {

        fun loadAccounts()

        fun addAccount(name: String, balance: String, currency: String)

        fun deleteAccount(accountId: String)

        fun updateAccount(accountId: String, account: Account)

        fun updateAccountStatus(accountId: String, isActive: Boolean)
    }
}