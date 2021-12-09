package uz.abduvali.pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abduvali.pdponline.databinding.ItemLessonSettingsBinding
import uz.abduvali.pdponline.entities.Lesson
import uz.abduvali.pdponline.utils.LessonDiffUtil

class SettingsLessonAdapter(var imagePath: String, var listener: OnItemClick) :
    ListAdapter<Lesson, SettingsLessonAdapter.Vh>(LessonDiffUtil()) {

    inner class Vh(var binding: ItemLessonSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClick {
        fun onEditClick(lesson: Lesson)
        fun onDeleteClick(lesson: Lesson)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemLessonSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SettingsLessonAdapter.Vh, position: Int) {
        val lesson = getItem(position)
        holder.binding.apply {
            nameTv.text = lesson.number.toString() + "-dars"
            description.text = lesson.name
            image.setImageURI(Uri.parse(imagePath))
            delete.setOnClickListener { listener.onDeleteClick(lesson) }
            edit.setOnClickListener { listener.onEditClick(lesson) }
        }
    }
}