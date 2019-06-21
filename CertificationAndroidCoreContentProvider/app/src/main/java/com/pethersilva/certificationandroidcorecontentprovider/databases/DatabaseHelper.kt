package com.pethersilva.certificationandroidcorecontentprovider.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context, DATABASE_NAME: String,
                     factory: SQLiteDatabase.CursorFactory?, DATABASE_VERSION: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    private var mTableName: String? = null
    private var mCreateTable: String? = null

    constructor(context: Context, DATABASE_NAME: String,
                factory: SQLiteDatabase.CursorFactory?, DATABASE_VERSION: Int, tableName: String, createTable: String) : this(context, DATABASE_NAME, factory, DATABASE_VERSION) {
        this.mTableName = tableName
        this.mCreateTable = createTable
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(this.mCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $mTableName")
        onCreate(db)
    }
}