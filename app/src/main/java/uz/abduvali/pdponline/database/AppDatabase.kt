package uz.abduvali.pdponline.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.abduvali.pdponline.dao.CourseDao
import uz.abduvali.pdponline.dao.LessonDao
import uz.abduvali.pdponline.dao.ModuleDao
import uz.abduvali.pdponline.entities.Course
import uz.abduvali.pdponline.entities.Lesson
import uz.abduvali.pdponline.entities.Module

@Database(
    entities = [Course::class, Module::class, Lesson::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun moduleDao(): ModuleDao
    abstract fun lessonDao(): LessonDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}