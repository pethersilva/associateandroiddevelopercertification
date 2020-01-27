package com.pethersilva.certificationandroiddatamanagementroom.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pethersilva.certificationandroiddatamanagementroom.R
import com.pethersilva.certificationandroiddatamanagementroom.adapter.WordListAdapter
import com.pethersilva.certificationandroiddatamanagementroom.entity.Word
import com.pethersilva.certificationandroiddatamanagementroom.repository.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

	private lateinit var mWordViewModel: WordViewModel
	private val NEW_WORD_ACTIVITY_REQUEST_CODE = 1

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)

		val wordAdapter = WordListAdapter(this)
		recyclerview.adapter = wordAdapter
		recyclerview.layoutManager = LinearLayoutManager(this)

		mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
		mWordViewModel.getAllWords().observe(this,
			Observer<List<Word>> {
					words -> wordAdapter.setWords(words)
			})

		fab.setOnClickListener { view ->
			val intent = Intent(this@MainActivity, NewWordActivity::class.java)
			startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				val word = data.getStringExtra(NewWordActivity.EXTRA_REPLY) ?: ""
				if (TextUtils.isEmpty(word)) {
					Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()
				} else {
					mWordViewModel.insertWord(Word(word))
				}
			}
		}
	}
}
