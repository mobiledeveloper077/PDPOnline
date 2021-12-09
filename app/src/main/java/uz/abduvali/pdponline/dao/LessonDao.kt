package uz.abduvali.pdponline.dao

import androidx.room.*
import io.reactivex.Flowable
import uz.abduvali.pdponline.entities.Lesson

@Dao
interface LessonDao {

    @Insert
    fun addLesson(lesson: Lesson)

    @Update
    fun editLesson(lesson: Lesson)

    @Delete
    fun deleteLesson(lesson: Lesson)

    @Query("select * from lesson where id=:id")
    fun getLessonById(id: Int): Lesson

    @Transaction
    @Query("select * from lesson where module_id=:id order by number")
    fun getLessonsByModuleId(id: Int): Flowable<List<Lesson>>

    @Query("select * from lesson order by number")
    fun getAllLessons(): Flowable<List<Lesson>>
}