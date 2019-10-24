package com.michael.reviewme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michael.reviewme.database.PersonRepository
import com.michael.reviewme.views.RegisterActivity


class RegisterViewModelFactory(private val registerActivity: RegisterActivity) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(registerActivity, personRepository = PersonRepository()) as T
    }
}