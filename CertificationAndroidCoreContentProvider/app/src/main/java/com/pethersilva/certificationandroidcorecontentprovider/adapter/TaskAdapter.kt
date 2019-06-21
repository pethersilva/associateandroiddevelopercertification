package com.pethersilva.certificationandroidcorecontentprovider.adapter

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pethersilva.certificationandroidcorecontentprovider.R
import com.pethersilva.certificationandroidcorecontentprovider.model.Task
import com.pethersilva.certificationandroidcorecontentprovider.providers.TasksProvider

class TaskAdapter(private val list: MutableList<Task>, private val contentResolver: ContentResolver): RecyclerView.Adapter<TaskAdapter.Companion.ViewHolder>() {

    companion object {
        class ViewHolder(val relativeLayout: RelativeLayout) : RecyclerView.ViewHolder(relativeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.task_item_row, parent, false)
        as RelativeLayout
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.relativeLayout.findViewById<TextView>(R.id.txtTask).text = list[position].taskDescription
        holder.relativeLayout.findViewById<CheckBox>(R.id.checkboxStatus).isChecked = list[position].status == 1

        holder.relativeLayout.findViewById<CheckBox>(R.id.checkboxStatus).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val task = list[position]
                val cv = ContentValues()
                cv.put(TasksProvider.id_column, task.id)
                cv.put(TasksProvider.task_column, task.taskDescription)
                cv.put(TasksProvider.status_column, if (isChecked) 1 else 0)
                contentResolver.update(Uri.parse(TasksProvider.TASK_URI), cv, "id = ${task.id}", null)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}