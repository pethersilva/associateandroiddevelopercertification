package com.pethersilva.certificationandroiddatamanagementroom.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pethersilva.certificationandroiddatamanagementroom.R
import kotlinx.android.synthetic.main.activity_new_word.*

class NewWordActivity : AppCompatActivity() {

	companion object {
		val EXTRA_REPLY = "com.example.android.roomwordssample.REPLY"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_new_word)

		button_save.setOnClickListener(object: View.OnClickListener{
			override fun onClick(p0: View?) {
				val replyIntent = Intent()
				if(TextUtils.isEmpty(edit_word.text)) {
					setResult(Activity.RESULT_CANCELED, replyIntent)
				} else {
					val word: String = edit_word.text.toString()
					replyIntent.putExtra(EXTRA_REPLY, word)
					setResult(Activity.RESULT_OK, replyIntent)
				}
				finish()
			}
		})
	}
}