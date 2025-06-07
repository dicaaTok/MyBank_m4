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
    private lateinit var accountId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accountId = intent.getStringExtra("accountId") ?: return

        viewModel.getAccountDetails(accountId)

        binding.btnEdit.setOnClickListener {
            viewModel.account.value?.let { showEditDialog(it) }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteAccount(accountId) {
                finish()
            }
        }

        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.account.observe(this) { account ->
            setAccountInfo(account)
        }
    }

    private fun setAccountInfo(account: Account) {
        binding.tvName.text = account.name
        binding.tvBalance.text = account.balance
        binding.tvCurrency.text = account.currency
        binding.tvStatus.text = if (account.isActive) "Активен" else "Неактивен"
    }

    private fun showEditDialog(account: Account) {
        val dialog = AlertDialog.Builder(this)
        val input = EditText(this).apply { setText(account.name) }
        dialog.setTitle("Изменить имя")
        dialog.setView(input)
        dialog.setPositiveButton("Сохранить") { _, _ ->
            val updated = account.copy(name = input.text.toString())
            viewModel.updateAccountFully(accountId, updated)
        }
        dialog.setNegativeButton("Отмена", null)
        dialog.show()
    }
}