package com.michael.reviewme.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.michael.reviewme.models.Person

@Dao
interface PersonDao {
    @Insert
    fun insertPerson(person: Person)

    @Query("select * from persons")
    fun allPersons(): List<Person>

}
