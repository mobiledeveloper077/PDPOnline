package uz.abduvali.pdponline.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Module::class,
            parentColumns = ["id"],
            childColumns = ["module_id"],
            onDelete = CASCADE
        )
    ]
)
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var description: String,
    var number: Int,
    @ColumnInfo(name = "module_id")
    var moduleId: Int
)