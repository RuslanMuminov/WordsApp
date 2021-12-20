package com.example.wordsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.WordItemBinding
import com.example.wordsapp.model.Words
import com.example.wordsapp.ui.HomeFragment

class WordsListAdapter(val listener: HomeFragment) : RecyclerView.Adapter<WordsListAdapter.ViewHolder>() {

    var list: List<Words> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    inner class ViewHolder(private val binding: WordItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(words: Words) {
            binding.wordName.text = words.words

            itemView.setOnClickListener {
                listener.onItemViewClick(words)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
    interface OnClick{
        fun onItemViewClick(words: Words)
    }