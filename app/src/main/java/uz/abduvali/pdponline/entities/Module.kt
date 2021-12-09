package uz.abduvali.pdponline.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = CASCADE
        )
    ]
)
data class Module(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var number: Int,
    @ColumnInfo(name = "course_id")
    var courseId: Int
)