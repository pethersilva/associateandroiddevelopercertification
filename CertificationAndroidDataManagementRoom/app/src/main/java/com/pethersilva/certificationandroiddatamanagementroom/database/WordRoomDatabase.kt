package com.pethersilva.certificationandroiddatamanagementroom.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pethersilva.certificationandroiddatamanagementroom.entity.Word
import com.pethersilva.certificationandroiddatamanagementroom.entity.WordDao

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

	abstract fun wordDao(): WordDao

	companion object {
		@Volatile
		private lateinit var INSTANCE: WordRoomDatabase

		private val sRoomDatabaseCallback: Callback =
			object : Callback() {
				override fun onOpen(db: SupportSQLiteDatabase) {
					super.onOpen(db)
					PopulateDbAsync(INSTANCE).execute()
				}
			}

		fun getAppDatabase(context: Context): WordRoomDatabase {

			synchronized(WordRoomDatabase::class.java) {
				if (!::INSTANCE.isInitialized) {
					INSTANCE = Room.databaseBuilder(context.applicationContext,
						WordRoomDatabase::class.java, "word_database")
						.fallbackToDestructiveMigration()
						.addCallback(sRoomDatabaseCallback)
						.build()
				}
			}
			return INSTANCE
		}

		private class PopulateDbAsync(db: WordRoomDatabase): AsyncTask<Void, Void, Void>() {

			private val wordDao: WordDao = db.wordDao()
			var words = arrayOf("dolphin", "crocodile", "cobra")

			override fun doInBackground(vararg p0: Void?): Void? {
				wordDao.deleteAll()

				for (element in words) {
					val word = Word(element)
					wordDao.insert(word)
				}
				return null
			}
		}
	}
}
