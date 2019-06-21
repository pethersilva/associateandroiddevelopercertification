package com.pethersilva.certificationandroidcorecontentprovider.providers

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.pethersilva.certificationandroidcorecontentprovider.databases.DatabaseHelper

class TasksProvider : ContentProvider() {

    companion object {
        val PROVIDER_NAME = "com.pethersilva.certificationandroidcorecontentprovider"
        val URL = "content://$PROVIDER_NAME"
        val CONTENT_URI = Uri.parse(URL)
        val id_column = "id"
        val task_column = "task"
        val status_column = "status"
        val TASK_URI = "content://${TasksProvider.PROVIDER_NAME}/tasks"
    }

    private var uriMatcher: UriMatcher? = null
    private val uriCode = 1
    private var values: HashMap<String, String>? = null

    private var db: SQLiteDatabase? = null
    private val DATABASE_NAME = "tasksDB"
    private val TASK_TABLE_NAME = "tasks"
    private val DATABASE_VERSION = 1
    private val CREATE_TASK_TABLE = " CREATE TABLE $TASK_TABLE_NAME($id_column INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " $task_column TEXT NOT NULL, $status_column INTEGER NOT NULL);"

    init {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher?.addURI(PROVIDER_NAME, "tasks", uriCode)
        uriMatcher?.addURI(PROVIDER_NAME, "tasks/*", uriCode)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val rowId = db?.insert(TASK_TABLE_NAME, "", values)

        if (rowId!! > 0 ) {
            val insertURI = ContentUris.withAppendedId(CONTENT_URI, rowId)
            context.contentResolver.notifyChange(insertURI, null)
            return insertURI
        }
        throw SQLiteException("Failed to add a record into $uri")
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        val query = SQLiteQueryBuilder()
        query.tables = TASK_TABLE_NAME
        when (uriMatcher?.match(uri)) {

            uriCode -> {
                query.setProjectionMap(values)
            }
            else -> {
                throw IllegalArgumentException("Unsupported URI: $uri")
            }
        }

        val sortQuery: String? = if (sortOrder == null || sortOrder == "") {
            id_column
        } else {
            sortOrder
        }

        val cursor = query.query(db, projection, selection, selectionArgs, null,
            null, sortQuery)
        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun onCreate(): Boolean {
        val databaseHelper = DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION
            ,TASK_TABLE_NAME, CREATE_TASK_TABLE)

        db = databaseHelper.writableDatabase
        return db != null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0

        when (uriMatcher?.match(uri)) {

            uriCode -> {
                count = db?.update(TASK_TABLE_NAME, values, selection, selectionArgs)!!
            }
            else -> {
                throw IllegalArgumentException("Unsupported URI: $uri")
            }
        }
        context.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0

        when (uriMatcher?.match(uri)) {

            uriCode -> {
                count = db?.delete(TASK_TABLE_NAME, selection, selectionArgs)!!
            }
            else -> {
                throw IllegalArgumentException("Unsupported URI: $uri")
            }
        }
        context.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        when (uriMatcher?.match(uri)) {
            uriCode -> {
                return "vnd.com.pethersilva.certificationandroidcorecontentprovider.provider/tasks"
            }
            else -> {
                throw IllegalArgumentException("Unsupported URI: $uri")
            }
        }
    }
}