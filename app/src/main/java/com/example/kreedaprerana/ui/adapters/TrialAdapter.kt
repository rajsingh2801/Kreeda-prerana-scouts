package com.example.kreedaprerana.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kreedaprerana.data.local.TrialEntry
import com.example.kreedaprerana.databinding.ItemTrialBinding
import java.text.SimpleDateFormat
import java.util.*

class TrialAdapter : ListAdapter<TrialEntry, TrialAdapter.TrialViewHolder>(TrialDiffCallback()) {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrialViewHolder {
        val binding = ItemTrialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrialViewHolder, position: Int) {
        holder.bind(getItem(position), dateFormat)
    }

    class TrialViewHolder(private val binding: ItemTrialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trial: TrialEntry, dateFormat: SimpleDateFormat) {
            binding.tvEventType.text = trial.eventType
            binding.tvDate.text = dateFormat.format(Date(trial.recordedAt))
            binding.tvValue.text = trial.value.toString()
            binding.tvUnit.text = trial.unit
        }
    }

    class TrialDiffCallback : DiffUtil.ItemCallback<TrialEntry>() {
        override fun areItemsTheSame(oldItem: TrialEntry, newItem: TrialEntry): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TrialEntry, newItem: TrialEntry): Boolean = oldItem == newItem
    }
}
