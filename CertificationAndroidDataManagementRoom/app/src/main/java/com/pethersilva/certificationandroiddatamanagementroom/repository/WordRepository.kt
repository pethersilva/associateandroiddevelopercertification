package com.pethersilva.certificationandroiddatamanagementroom.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.pethersilva.certificationandroiddatamanagementroom.database.WordRoomDatabase
import com.pethersilva.certificationandroiddatamanagementroom.entity.Word
import com.pethersilva.certificationandroiddatamanagementroom.entity.WordDao

class WordRepository(application: Application) {

	private val mWordDao: WordDao
	private val mAllWords: LiveData<List<Word>>
	private val db: WordRoomDatabase = WordRoomDatabase.getAppDatabase(application)

	init {
		mWordDao = db.wordDao()
		mAllWords = mWordDao.getAllWords()
	}

	fun getAllWords() : LiveData<List<Word>> {
		return mAllWords
	}

	fun insert(word: Word) {
		InsertAsyncTask(mWordDao).execute(word)
	}

	companion object {

		class InsertAsyncTask(dao: WordDao): AsyncTask<Word, Void, Void>() {

			private val mAsyncTaskDao: WordDao = dao

			override fun doInBackground(vararg params: Word): Void? {
				mAsyncTaskDao.insert(params[0])
				return null
			}
		}
	}
}
