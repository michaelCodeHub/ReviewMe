package com.michael.reviewme.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
class Person (fName: String, lName: String, pictureUri: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "personId")
    public val id: Int = 0

    @ColumnInfo(name = "firstName")
    public  val firstName: String? = fName
    @ColumnInfo(name = "lastName")
    public  val lastName: String? = lName

    @ColumnInfo(name = "pictureURL")
    public  val pictureURL: String? = pictureUri


}