package com.dica.mybank_m4.ui.account_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dica.mybank_m4.data.api.AccountDetailsApi
import com.dica.mybank_m4.data.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val accountDetailsApi: AccountDetailsApi
) : ViewModel() {

    private val _account = MutableLiveData<Account>()
    val account: LiveData<Account> get() = _account



    fun getAccountDetails(id: Int) {
        viewModelScope.launch {
            try {
                val result = accountDetailsApi.getAccountDetails(id)
                _account.value = result
            } catch (e: Exception) {
            }
        }
    }



    fun updateAccountFully(id: Int, account: Account) {
        viewModelScope.launch {
            try {
                accountDetailsApi.updateAccountFully(id, account)
                getAccountDetails(id)
            } catch (e: Exception) {
            }
        }
    }

    fun deleteAccount(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                accountDetailsApi.deleteAccount(id)
                onSuccess()
            } catch (e: Exception) {

            }
        }
    }
}