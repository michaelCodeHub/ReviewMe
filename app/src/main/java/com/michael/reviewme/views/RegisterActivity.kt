package com.michael.reviewme.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.michael.reviewme.R
import com.michael.reviewme.database.PersonRepository
import com.michael.reviewme.databinding.ActivityRegisterBinding
import com.michael.reviewme.viewmodels.RegisterViewModel
import com.michael.reviewme.viewmodels.RegisterViewModelFactory
import java.io.File
import android.provider.DocumentsContract
import android.view.View
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    var imageView : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        imageView = findViewById(R.id.imageView)

        var registerViewModelFactory = RegisterViewModelFactory(this)

        var viewModel: RegisterViewModel = ViewModelProviders.of(this, registerViewModelFactory).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val nameObserver = Observer<Uri> { newName ->
            imageView?.setImageURI(newName)
            val file = File(convertMediaUriToPath(newName))
            PersonRepository.uploadFile(newName)
        }

        findViewById<Button>(R.id.button2).setOnClickListener(View.OnClickListener {
            binding.viewModel!!.submitClicked("Mcihael", "Adaikalaraj", Uri.fromFile(File("")))
        })

        binding.viewModel?.imagePath!!.observe(this, nameObserver)
    }

    fun convertMediaUriToPath(uri: Uri): String {
        val wholeID = DocumentsContract.getDocumentId(uri)
        val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        val column = arrayOf(MediaStore.Images.Media.DATA)
        val sel = MediaStore.Images.Media._ID + "=?"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )
        var filePath = ""
        val columnIndex = cursor!!.getColumnIndex(column[0])
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }
        cursor.close()
        return filePath
    }

    fun browseImageClicked(){
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data

            binding.viewModel?.imagePath!!.value = selectedFile

        }
    }
}
