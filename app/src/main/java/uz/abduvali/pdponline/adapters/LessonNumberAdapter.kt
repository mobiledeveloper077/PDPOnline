package uz.abduvali.pdponline.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abduvali.pdponline.databinding.ItemLessonNumberBinding
import uz.abduvali.pdponline.entities.Lesson
import uz.abduvali.pdponline.utils.LessonDiffUtil

class LessonNumberAdapter(var listener: OnItemClick) :
    ListAdapter<Lesson, LessonNumberAdapter.Vh>(LessonDiffUtil()) {

    inner class Vh(var binding: ItemLessonNumberBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClick {
        fun onItemClick(lesson: Lesson)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemLessonNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: LessonNumberAdapter.Vh, position: Int) {
        val lesson = getItem(position)
        holder.binding.apply {
            number.text = lesson.number.toString()
            root.setOnClickListener { listener.onItemClick(lesson) }
        }
    }
}