package com.pethersilva.jobschedulercodechallenge.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.AsyncTask
import android.util.Log

class AsyncTaskJobService: JobService() {

    companion object {
        var jobParameters: JobParameters? = null
        val TAG = "AsyncTaskJobService"
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        jobParameters = params

        val asyncTask = AsyncTaskSleep()
        asyncTask.execute(this)
        return true
    }
}

class AsyncTaskSleep : AsyncTask<JobService, Void, Void>() {

    private var jobService: JobService? = null

    override fun doInBackground(vararg params: JobService?): Void? {

        jobService = params[0]

        for (interaction in 0 until 10) {
            Thread.sleep(1000)
            Log.d(AsyncTaskJobService.TAG, "interaction: $interaction")
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        if (AsyncTaskJobService.jobParameters != null) {
            jobService?.jobFinished(AsyncTaskJobService.jobParameters, false)
        }
    }
}