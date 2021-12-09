package uz.abduvali.pdponline.dao

import androidx.room.*
import io.reactivex.Flowable
import uz.abduvali.pdponline.entities.Course

@Dao
interface CourseDao {

    @Insert
    fun addCourse(course: Course)

    @Update
    fun editCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)

    @Query("select * from course where id=:id")
    fun getCourseById(id: Int):Course

    @Query("select * from course")
    fun getAllCourses(): Flowable<List<Course>>
}