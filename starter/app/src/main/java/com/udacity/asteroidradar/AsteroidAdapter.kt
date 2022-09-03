package com.udacity.asteroidradar

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter(private val clickListener: AsteroidListener)
    :ListAdapter<Asteroid,AsteroidAdapter.ViewHolder>(asteroidDiffCallBack()){

    class asteroidDiffCallBack():DiffUtil.ItemCallback<Asteroid>(){
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
           return oldItem.id == newItem.id
        }
    }

    class ViewHolder private constructor (private val binding: ListItemAsteroidBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:Asteroid,clickListener: AsteroidListener) {
            binding.asteroid = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidListener(val clickListener: (asteroid:Asteroid)-> Unit){
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid,clickListener)
    }



}