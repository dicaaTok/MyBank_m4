package com.dica.mybank_m4.ui.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dica.mybank_m4.R
import com.dica.mybank_m4.data.model.Account
import com.dica.mybank_m4.databinding.ActivityMainBinding
import com.dica.mybank_m4.ui.Adapter.AccountAdapter
import com.dica.mybank_m4.ui.ViewModel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    private lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = AccountAdapter(
            onDelete = { id ->
                viewModel.deleteAccount(id)
            },
            onEdit = { account ->
                showEditDialog(account)
            },
            onStatusToggle = { id, isChecked ->
                viewModel.updateAccountStatus(id, isChecked)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btnAdd.setOnClickListener {
            showAddDialog()
        }

        viewModel.loadAccounts()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData(){
        viewModel.accounts.observe(this){
            adapter.submitList(it)
        }
        viewModel.successMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.errorMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_account, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.etName)
        val balanceInput = dialogView.findViewById<EditText>(R.id.etBalance)
        val currencyInput = dialogView.findViewById<EditText>(R.id.etCurrency)

        AlertDialog.Builder(this)
            .setTitle("Добавить счёт")
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->
                val name = nameInput.text.toString()
                val balance = balanceInput.text.toString()
                val currency = currencyInput.text.toString()
                viewModel.addAccount(name, balance, currency)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showEditDialog(account: Account) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_account, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.etName)
        val balanceInput = dialogView.findViewById<EditText>(R.id.etBalance)
        val currencyInput = dialogView.findViewById<EditText>(R.id.etCurrency)

        nameInput.setText(account.name)
        balanceInput.setText(account.balance)
        currencyInput.setText(account.currency)

        AlertDialog.Builder(this)
            .setTitle("Редактировать счёт")
            .setView(dialogView)
            .setPositiveButton("Обновить") { _, _ ->
                val name = nameInput.text.toString()
                val balance = balanceInput.text.toString()
                val currency = currencyInput.text.toString()

                val updated = account.copy(
                    name = name,
                    balance = balance,
                    currency = currency
                )

                viewModel.updateAccountFully(updated.id!!, updated)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

}