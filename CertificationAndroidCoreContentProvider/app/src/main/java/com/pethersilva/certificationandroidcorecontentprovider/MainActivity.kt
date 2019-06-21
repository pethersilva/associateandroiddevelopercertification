package com.pethersilva.certificationandroidcorecontentprovider

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pethersilva.certificationandroidcorecontentprovider.adapter.TaskAdapter
import com.pethersilva.certificationandroidcorecontentprovider.model.Task
import com.pethersilva.certificationandroidcorecontentprovider.providers.TasksProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var list: MutableList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getting all tasks from content provider
        val cursor = contentResolver.query(Uri.parse(TasksProvider.TASK_URI), null, null, null, null)
        if (cursor != null && cursor.count > 0) {

            var taskIdIndex = 0
            var taskId = 0L
            var taskIndex = 0
            var taskDescription = ""
            var taskStatusIndex = 0
            var taskStatus = 0
            var task: Task? = null

            cursor.moveToFirst()

            do {

                taskIdIndex = cursor.getColumnIndex(TasksProvider.id_column)
                taskId = cursor.getLong(taskIdIndex)

                taskIndex = cursor.getColumnIndex(TasksProvider.task_column)
                taskDescription = cursor.getString(taskIndex)

                taskStatusIndex = cursor.getColumnIndex(TasksProvider.status_column)
                taskStatus = cursor.getInt(taskStatusIndex)

                task = Task(taskId, taskDescription, taskStatus)
                list.add(task)
            } while (cursor.moveToNext())
        }

        cursor?.close()

        viewManager = LinearLayoutManager(this)
        viewAdapter = TaskAdapter(list, contentResolver)

        recyclerView = recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        btnAddTask.setOnClickListener {
            if (txtTask.text.toString() == "") {
                Toast.makeText(this@MainActivity, "Write your task", Toast.LENGTH_LONG).show()
            } else {
                val task = Task(0,txtTask.text.toString(), 0)

                val cv = ContentValues()
                cv.put(TasksProvider.task_column, txtTask.text.toString())
                cv.put(TasksProvider.status_column, 0)
                contentResolver.insert(Uri.parse(TasksProvider.TASK_URI), cv)
                txtTask.text.clear()

                list.add(task)
                viewAdapter.notifyDataSetChanged()
            }
        }
    }
}