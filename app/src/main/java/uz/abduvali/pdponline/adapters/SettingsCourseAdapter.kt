package uz.abduvali.pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abduvali.pdponline.databinding.ItemCourseSettingsBinding
import uz.abduvali.pdponline.entities.Course
import uz.abduvali.pdponline.utils.CourseDiffUtil

class SettingsCourseAdapter(var listener: OnItemClick) :
    ListAdapter<Course, SettingsCourseAdapter.Vh>(CourseDiffUtil()) {

    inner class Vh(var binding: ItemCourseSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClick {
        fun onItemClick(course: Course)
        fun onEditClick(course: Course)
        fun onDeleteClick(course: Course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemCourseSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SettingsCourseAdapter.Vh, position: Int) {
        val course = getItem(position)
        holder.binding.apply {
            nameTv.text = course.name
            image.setImageURI(Uri.parse(course.imageUri))
            delete.setOnClickListener { listener.onDeleteClick(course) }
            edit.setOnClickListener { listener.onEditClick(course) }
            root.setOnClickListener { listener.onItemClick(course) }
        }
    }
}