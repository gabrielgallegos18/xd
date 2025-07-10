package com.example.nutritiontracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritiontracker.database.FoodEntry

class FoodEntryAdapter(
    private val onDeleteClick: (FoodEntry) -> Unit
) : ListAdapter<FoodEntry, FoodEntryAdapter.FoodEntryViewHolder>(FoodEntryDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodEntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_entry, parent, false)
        return FoodEntryViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: FoodEntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class FoodEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFoodName: TextView = itemView.findViewById(R.id.tvFoodName)
        private val tvFoodDetails: TextView = itemView.findViewById(R.id.tvFoodDetails)
        private val tvTotalCalories: TextView = itemView.findViewById(R.id.tvTotalCalories)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        
        fun bind(foodEntry: FoodEntry) {
            tvFoodName.text = foodEntry.foodName
            tvFoodDetails.text = "${foodEntry.servings} servings × ${foodEntry.caloriesPerServing} cal"
            tvTotalCalories.text = "${foodEntry.totalCalories} cal"
            
            btnDelete.setOnClickListener {
                onDeleteClick(foodEntry)
            }
        }
    }
    
    class FoodEntryDiffCallback : DiffUtil.ItemCallback<FoodEntry>() {
        override fun areItemsTheSame(oldItem: FoodEntry, newItem: FoodEntry): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: FoodEntry, newItem: FoodEntry): Boolean {
            return oldItem == newItem
        }
    }
}