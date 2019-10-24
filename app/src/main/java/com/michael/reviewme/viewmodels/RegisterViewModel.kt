package com.michael.reviewme.viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.michael.reviewme.database.PersonRepository
import com.michael.reviewme.models.Person
import com.michael.reviewme.views.RegisterActivity

class RegisterViewModel(private val registerActivity: RegisterActivity, private val personRepository: PersonRepository) : ViewModel(){

    public var imagePath: MutableLiveData<Uri>? = MutableLiveData()

    fun browseImageClicked(){
        registerActivity.browseImageClicked()
    }

    fun submitClicked(fname: String, lname: String, pictureUri: Uri){
        var person =  Person(fname, lname, pictureUri.toString())

//        personRepository.savePerson(person)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue(person)

    }
}