package pethersilva.com.certificationandroidcorejobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pethersilva.com.certificationandroidcorejobscheduler.services.NotificationJobService

class MainActivity : AppCompatActivity() {

    private var mScheduler : JobScheduler? = null
    private val JOB_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioButtonNone.isChecked = true

        btn_jobschedule.setOnClickListener { scheduleJob() }

        btn_cancel_jobs.setOnClickListener { cancelJobs() }

        setupSeekBar()
    }

    private fun setupSeekBar() {

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, currentValue: Int, p2: Boolean) {
                if(currentValue > 1){
                    txtCurrentValue.text = ("$currentValue s")
                } else {
                    txtCurrentValue.text = resources.getString(R.string.main_activity_network_device_current_value)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun scheduleJob() {

        var selectedNetworkID = radioGroup.checkedRadioButtonId
        var selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE

        when(selectedNetworkID) {
            R.id.radioButtonNone -> selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
            R.id.radioButtonAny -> selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY
            R.id.radioButtonWifi -> selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED
        }

        mScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val serviceName = ComponentName(packageName, NotificationJobService::class.java.name)
        val builder = JobInfo.Builder(JOB_ID, serviceName)
            .setRequiredNetworkType(selectedNetworkOption)
            .setRequiresDeviceIdle(switchIdle.isChecked)
            .setRequiresCharging(switchCharging.isChecked)

        val seekbarInteger = seekBar.progress
        val isSeekBarSet = seekbarInteger > 0

        if (isSeekBarSet) {
            builder.setOverrideDeadline(seekbarInteger * 1000L)
        }

        val constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE) || switchCharging.isChecked ||
                switchIdle.isChecked || isSeekBarSet

        if (constraintSet) {
            val myJobInfo = builder.build()
            mScheduler?.schedule(myJobInfo)
            Toast.makeText(this, resources.getString(R.string.main_activity_job_scheduled_message), Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this, resources.getString(R.string.main_activity_job_schedule_missing_constraint_message)
                , Toast.LENGTH_LONG).show()
        }
    }

    private fun cancelJobs() {
        mScheduler?.cancelAll()
        mScheduler = null
        Toast.makeText(this, resources.getString(R.string.main_activity_job_canceled_message), Toast.LENGTH_LONG).show()
    }
}