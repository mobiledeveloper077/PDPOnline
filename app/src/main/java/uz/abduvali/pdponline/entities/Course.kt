package uz.abduvali.pdponline.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var imageUri: String
)