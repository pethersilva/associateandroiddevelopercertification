package com.pethersilva

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Toast using basic way
        btn_basic_toast.setOnClickListener {
            Toast.makeText(this, resources.getString(R.string.basic_toast), Toast.LENGTH_SHORT).show()
        }

        //You can decide where the toast message will appear
        btn_centralized_toast.setOnClickListener {

            val toast = Toast.makeText(this, resources.getString(R.string.centralized_toast), Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.show()
        }

        //You can use a toast with a custom view
        btn_view_toast.setOnClickListener {

            val inflater = layoutInflater
            val container = findViewById<ViewGroup>(R.id.custom_toast_container)
            val layout: ViewGroup = inflater.inflate(R.layout.custom_toast, container) as ViewGroup
            val toastText: TextView = layout.findViewById(R.id.textToast)
            toastText.text = resources.getString(R.string.view_toast)

            with (Toast(applicationContext)){
                setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                duration = Toast.LENGTH_SHORT
                view = layout
                show()
            }
        }
    }
}