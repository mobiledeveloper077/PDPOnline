package uz.abduvali.pdponline.dao

import androidx.room.*
import io.reactivex.Flowable
import uz.abduvali.pdponline.entities.Module

@Dao
interface ModuleDao {

    @Insert
    fun addModule(module: Module)

    @Update
    fun editModule(module: Module)

    @Delete
    fun deleteModule(module: Module)

    @Query("select * from module where id=:id")
    fun getModuleById(id: Int): Module

    @Transaction
    @Query("select * from module where course_id=:id order by number")
    fun getModulesByCourseId(id: Int): Flowable<List<Module>>

    @Query("select * from module order by number")
    fun getAllModules(): Flowable<List<Module>>
}