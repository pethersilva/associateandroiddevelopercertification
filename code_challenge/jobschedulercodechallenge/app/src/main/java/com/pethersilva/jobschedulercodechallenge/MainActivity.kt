package com.pethersilva.jobschedulercodechallenge

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pethersilva.jobschedulercodechallenge.services.AsyncTaskJobService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mScheduler : JobScheduler? = null
    private val JOB_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_startjob.setOnClickListener {
            scheduleJob()
        }
    }

    private fun scheduleJob() {

        mScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobServiceName = ComponentName(packageName, AsyncTaskJobService::class.java.name)

        val builder = JobInfo.Builder(JOB_ID, jobServiceName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setRequiresCharging(true)
        mScheduler?.schedule(builder.build())
    }
}