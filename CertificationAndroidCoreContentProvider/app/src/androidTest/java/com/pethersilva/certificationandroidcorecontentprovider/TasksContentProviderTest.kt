package com.pethersilva.certificationandroidcorecontentprovider

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.test.ProviderTestCase2
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.pethersilva.certificationandroidcorecontentprovider.providers.TasksProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksContentProviderTest : ProviderTestCase2<TasksProvider>(TasksProvider::class.java, "com.pethersilva.certificationandroidcorecontentprovider")  {

    val URL = "content://${TasksProvider.PROVIDER_NAME}/tasks"

    @Before
    public override fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
        super.setUp()
        val mockRslv : ContentResolver = mockContentResolver
        mockRslv.delete(Uri.parse(URL), "1=1", arrayOf())
    }

    @Test
    fun test_inserted() {
        val mockRslv: ContentResolver = mockContentResolver

        val cv = ContentValues()
        cv.put(TasksProvider.task_column, "Schedule dentist")
        cv.put(TasksProvider.status_column, 0)
        mockRslv.insert(Uri.parse(URL), cv)

        //query
        val cursor = mockRslv.query(Uri.parse(URL), null, null, null)

        cursor?.moveToFirst()
        assertThat(cursor?.count == 1)

        cursor?.moveToFirst()
        val taskIndex = cursor?.getColumnIndex(TasksProvider.task_column)
        assertThat(cursor?.getString(taskIndex!!)).isEqualTo("Schedule dentist")
    }

    @Test
    fun test_update() {

        val mockRslv: ContentResolver = mockContentResolver

        var cv = ContentValues()
        cv.put(TasksProvider.task_column, "Schedule dentist")
        cv.put(TasksProvider.status_column, 0)
        mockRslv.insert(Uri.parse(URL), cv)

        //query
        var cursor = mockRslv.query(Uri.parse(URL), null, null, null)

        cursor?.moveToFirst()
        val taskIdColumnIndex = cursor?.getColumnIndex(TasksProvider.id_column)
        val id = cursor?.getInt(taskIdColumnIndex!!)

        //updating
        cv = ContentValues()
        cv.put(TasksProvider.task_column, "Task Updated")
        cv.put(TasksProvider.status_column, 1)
        mockRslv.update(Uri.parse(URL), cv, "id = $id", null)

        //query updated value
        cursor = mockRslv.query(Uri.parse(URL), null, null, null)

        cursor?.moveToFirst()
        assertThat(cursor?.count).isEqualTo(1)

        cursor?.moveToFirst()
        var taskIndex = cursor?.getColumnIndex(TasksProvider.task_column)
        assertThat(cursor?.getString(taskIndex!!)).isEqualTo("Task Updated")

        cursor?.moveToFirst()
        taskIndex = cursor?.getColumnIndex(TasksProvider.status_column)
        assertThat(cursor?.getInt(taskIndex!!)).isEqualTo(1)
    }

    @Test
    fun test_delete() {

        val mockRslv: ContentResolver = mockContentResolver

        var cv = ContentValues()
        cv.put(TasksProvider.task_column, "Schedule dentist")
        cv.put(TasksProvider.status_column, 0)
        mockRslv.insert(Uri.parse(URL), cv)

        mockRslv.delete(Uri.parse(URL), "1=1", arrayOf())

        //query updated value
        var cursor = mockRslv.query(Uri.parse(URL), null, null, null)

        cursor?.moveToFirst()
        assertThat(cursor?.count).isEqualTo(0)
    }

    @Test
    fun test_insert_multiples_tasks() {

        val mockRslv: ContentResolver = mockContentResolver

        var cv = ContentValues()
        cv.put(TasksProvider.task_column, "Schedule dentist")
        cv.put(TasksProvider.status_column, 0)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Drink water")
        cv.put(TasksProvider.status_column, 1)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Take a shower. LOL")
        cv.put(TasksProvider.status_column, 1)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Stop to play World of Warcraft")
        cv.put(TasksProvider.status_column, 0)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Watch Avengers")
        cv.put(TasksProvider.status_column, 0)
        mockRslv.insert(Uri.parse(URL), cv)

        //getting all rows
        var cursor = mockRslv.query(Uri.parse(URL), null, null, null)

        cursor?.moveToFirst()
        assertThat(cursor?.count).isEqualTo(5)
    }

    @Test
    fun test_query() {

        val mockRslv: ContentResolver = mockContentResolver

        var cv = ContentValues()
        cv.put(TasksProvider.task_column, "Schedule dentist")
        cv.put(TasksProvider.status_column, 0)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Drink water")
        cv.put(TasksProvider.status_column, 1)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Drink beer")
        cv.put(TasksProvider.status_column, 1)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Drink soda")
        cv.put(TasksProvider.status_column, 1)
        mockRslv.insert(Uri.parse(URL), cv)

        cv.put(TasksProvider.task_column, "Take a shower. LOL")
        cv.put(TasksProvider.status_column, 1)
        mockRslv.insert(Uri.parse(URL), cv)

        //getting all rows
        val args = mutableListOf<String>()
        args.add("Drink water")
        val selection = "${TasksProvider.task_column} like ?"
        val selectionArgs = Array(1) {"Drink%"}

        val cursor = mockRslv.query(Uri.parse(URL), null, selection , selectionArgs,  null)
        assertThat(cursor?.count).isEqualTo(3)
    }
}