package uz.abduvali.pdponline.utils

import androidx.recyclerview.widget.DiffUtil
import uz.abduvali.pdponline.entities.Lesson

class LessonDiffUtil : DiffUtil.ItemCallback<Lesson>() {
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
        oldItem == newItem
}