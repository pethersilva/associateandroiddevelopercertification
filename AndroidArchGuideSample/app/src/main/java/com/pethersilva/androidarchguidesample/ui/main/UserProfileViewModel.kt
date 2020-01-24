package com.pethersilva.androidarchguidesample.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pethersilva.androidarchguidesample.ui.main.model.User

class UserProfileViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
	val userId: String = savedStateHandle["uid"] ?:
	throw IllegalArgumentException("missing user id")
	val user: User = TODO()
}
