package com.dica.mybank_m4.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.dica.mybank_m4.R
import com.dica.mybank_m4.data.model.Account

class AccountAdapter (
    val onDelete: (String) -> Unit,
    val onEdit: (Account) -> Unit,
    val onStatusToggle: (String, Boolean) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private val items = mutableListOf<Account>()

    fun submitList(data: List<Account>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(account: Account) = with(itemView) {
            findViewById<TextView>(R.id.tvName).text = account.name
            findViewById<TextView>(R.id.tvBalance).text = "${account.balance} ${account.currency}"

            val btnDelete = findViewById<Button>(R.id.btnDelete)
            btnDelete.setOnClickListener {
                account.id?.let { onDelete(it) }
            }

            val btnEdit = findViewById<Button>(R.id.btnEdit)
            btnEdit.setOnClickListener {
                onEdit(account)
            }

            val switchActive = findViewById<SwitchCompat>(R.id.switchActive)
            switchActive.setOnCheckedChangeListener(null)
            switchActive.isChecked = account.isActive
            switchActive.setOnCheckedChangeListener { buttonView, isChecked ->
                account.id?.let { onStatusToggle(it, isChecked) }
            }



        }
    }
}