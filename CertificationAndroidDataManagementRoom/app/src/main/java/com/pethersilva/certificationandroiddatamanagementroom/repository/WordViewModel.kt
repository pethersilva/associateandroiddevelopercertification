package com.pethersilva.certificationandroiddatamanagementroom.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pethersilva.certificationandroiddatamanagementroom.entity.Word

class WordViewModel(application: Application) : AndroidViewModel(application) {

	private val mRepository: WordRepository = WordRepository(application)
	private val mAllWords: LiveData<List<Word>>

	init {
		mAllWords = mRepository.getAllWords()
	}

	fun getAllWords() : LiveData<List<Word>> {
		return mAllWords
	}

	fun insertWord(word: Word) {
		mRepository.insert(word)
	}
}
