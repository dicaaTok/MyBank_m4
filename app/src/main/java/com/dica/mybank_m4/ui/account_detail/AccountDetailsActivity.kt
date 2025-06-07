package com.dica.mybank_m4.ui.account_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dica.mybank_m4.R
import com.dica.mybank_m4.data.model.Account
import com.dica.mybank_m4.databinding.ActivityAccountDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountDetailsBinding
    private val viewModel: AccountDetailsViewModel by viewModels()
    private var accountId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accountId = intent.getIntExtra("accountId", -1)
        viewModel.getAccountDetails(accountId)

        subscribeToLiveData()

        binding.btnEdit.setOnClickListener {
            viewModel.account.value?.let { showEditDialog(it) }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteAccount(accountId) {
                finish()
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.account.observe(this) { account ->
            setAccountInfo(account)
        }
    }

    private fun setAccountInfo(account: Account) {
        binding.tvName.text = account.name
        binding.tvBalance.text = account.balance.toString()
    }

    private fun showEditDialog(account: Account) {
        // viewModel.updateAccountFully(accountId, newAccount)
    }
}