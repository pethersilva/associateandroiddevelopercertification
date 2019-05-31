package com.pethersilva.certificationandroidcoresnackbar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_snackbar.setOnClickListener {
            Snackbar.make(main_activity_layout, resources.getString(R.string.main_activity_regular_snackbar_button),
                Snackbar.LENGTH_LONG).show()
        }

        btn_snackbar_action.setOnClickListener {

            val snackbar = Snackbar.make(main_activity_layout, resources.getString(R.string.main_activity_action_snackbar_button),
                Snackbar.LENGTH_LONG)
            snackbar.setAction(resources.getString(R.string.main_activity_action_snackbar_button_delete_message),
                restoredClickListener)
            snackbar.show()
        }

        btn_custom_snackbar.setOnClickListener {
            val snackbar = Snackbar.make(main_activity_layout, resources.getString(R.string.main_activity_custom_snackbar_button_no_internet_connection_message),
                Snackbar.LENGTH_LONG)
            snackbar.setAction(resources.getString(R.string.main_activity_custom_snackbar_button_retry_message),
                customClickListener)
            snackbar.setActionTextColor(Color.GREEN)
            val view = snackbar.view
            val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(Color.RED)
            snackbar.show()
        }
    }

    private val restoredClickListener: View.OnClickListener = View.OnClickListener {
        Snackbar.make(main_activity_layout, resources.getString(R.string.main_activity_action_snackbar_button_restore_message),
            Snackbar.LENGTH_LONG).show()
    }

    private val customClickListener: View.OnClickListener = View.OnClickListener {
        //Retry connection here!!!!
    }
}
