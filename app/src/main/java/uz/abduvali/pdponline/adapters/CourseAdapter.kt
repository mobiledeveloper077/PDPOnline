package uz.abduvali.pdponline.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.ItemCourseBinding
import uz.abduvali.pdponline.entities.Course
import uz.abduvali.pdponline.entities.Module
import uz.abduvali.pdponline.utils.CourseDiffUtil

class CourseAdapter(
    private var listener: OnItemClick
) :
    ListAdapter<Course, CourseAdapter.Vh>(CourseDiffUtil()) {

    inner class Vh(var binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClick {
        fun onAllClick(course: Course)
        fun onItemClick(module: Module)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.binding.apply {
            val course = getItem(position)
            nameTv.text = course.name
            all.setOnClickListener { listener.onAllClick(course) }
            val moduleNameAdapter = ModuleNameAdapter(listener)
            rv.adapter = moduleNameAdapter
            AppDatabase.getInstance(root.context).moduleDao()
                .getModulesByCourseId(course.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    moduleNameAdapter.submitList(it)
                }
        }
    }
}