package uz.abduvali.pdponline.utils

import androidx.recyclerview.widget.DiffUtil
import uz.abduvali.pdponline.entities.Module

class ModuleDiffUtil : DiffUtil.ItemCallback<Module>() {
    override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean =
        oldItem == newItem
}