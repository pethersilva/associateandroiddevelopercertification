package com.pethersilva.certificationandroiddatamanagementroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pethersilva.certificationandroiddatamanagementroom.R
import com.pethersilva.certificationandroiddatamanagementroom.entity.Word

class WordListAdapter(context : Context) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

	private val mInflater: LayoutInflater = LayoutInflater.from(context)
	private var mWords: List<Word>? = null

	class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
		 val wordItemView: TextView = itemView.findViewById(R.id.textView)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
		val itemView: View = mInflater.inflate(R.layout.recyclerview_item, parent, false)
		return WordViewHolder(itemView)
	}

	override fun getItemCount() = mWords?.size ?: 0

	override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
		holder.wordItemView.text = mWords?.get(position)?.mWord ?: "Word not found"
	}

	fun setWords(words: List<Word>) {
		mWords = words
		notifyDataSetChanged()
	}
}
