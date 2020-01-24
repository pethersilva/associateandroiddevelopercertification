package com.pethersilva.androidarchguidesample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.pethersilva.androidarchguidesample.R
import kotlinx.android.synthetic.main.main_fragment.*

class UserProfileFragment : Fragment() {

	companion object {
		fun newInstance() = UserProfileFragment()
	}

	private val viewModel: UserProfileViewModel by viewModels()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?): View {
		return inflater.inflate(R.layout.main_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.user.observe(viewLifecycleOwner) {
			//update UI
			editTextUserName.setText(it.name)
			editTextUserAge.setText(it.age)
		}
	}
}