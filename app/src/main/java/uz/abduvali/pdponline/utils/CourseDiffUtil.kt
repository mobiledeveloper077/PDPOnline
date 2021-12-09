package uz.abduvali.pdponline.utils

import androidx.recyclerview.widget.DiffUtil
import uz.abduvali.pdponline.entities.Course

class CourseDiffUtil : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
            oldItem == newItem
    }