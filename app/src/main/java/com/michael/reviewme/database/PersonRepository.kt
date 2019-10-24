package com.michael.reviewme.database

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.michael.reviewme.models.Person
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import com.google.firebase.storage.FileDownloadTask
import android.app.Application






class PersonRepository {

    private lateinit var personDao: PersonDao
    private val searchResults = MutableLiveData<List<Person>>()

    fun ProductRepository(application: Application) {
        val db: PersonRoomDatabase
        db = PersonRoomDatabase.getDatabase(application)
        personDao = db.personDao()
    }


    private fun asyncFinished(results: List<Person>) {
        searchResults.setValue(results)

        this.searchResults.value!!.forEach { person ->
        }
    }

    fun savePerson(person: Person){
        InsertAsyncTask(personDao).execute(person)
    }

    companion object{

        fun uploadFile(file: Uri){
            var storageRef: StorageReference = FirebaseStorage.getInstance().getReference();
            val riversRef = storageRef.child("images/rivers.jpg")

            riversRef.putFile(file)
                .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    // Get a URL to the uploaded content

                })
                .addOnFailureListener(OnFailureListener {
                    // Handle unsuccessful uploads
                    // ...
                    Log.e("FirebAse Error", it.toString())
                })
        }

        fun downloadFile(){
            var storageRef: StorageReference = FirebaseStorage.getInstance().getReference();

            val localFile = File.createTempFile("images", "jpg")
            storageRef.getFile(localFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                    // Successfully downloaded data to local file
                    // ...
                }).addOnFailureListener(OnFailureListener {
                    // Handle failed download
                    // ...
                })
        }
    }

    private class QueryAsyncTask internal constructor(private val asyncTaskDao: PersonDao) :
        AsyncTask<String, Void, List<Person>>() {
        private val delegate: PersonRepository? = null

        override fun doInBackground(vararg params: String): List<Person> {
            return asyncTaskDao.allPersons()
        }

        override fun onPostExecute(result: List<Person>) {
            delegate!!.asyncFinished(result)
        }
    }

    private class InsertAsyncTask  internal constructor(private var asyncTaskDao: PersonDao) :
        AsyncTask<Person, Void, Void>() {
        private val delegate: PersonRepository? = null

        fun InsertAsyncTask(dao: PersonDao) {
            asyncTaskDao = dao
        }

        override fun doInBackground(vararg persons: Person): Void? {
            asyncTaskDao.insertPerson(persons[0])
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            QueryAsyncTask(asyncTaskDao).execute()
        }
    }
}