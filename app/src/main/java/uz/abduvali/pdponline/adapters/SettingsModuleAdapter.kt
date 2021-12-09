package uz.abduvali.pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abduvali.pdponline.databinding.ItemModuleSettingsBinding
import uz.abduvali.pdponline.entities.Module
import uz.abduvali.pdponline.utils.ModuleDiffUtil

class SettingsModuleAdapter(var imagePath: String, var listener: OnItemClick) :
    ListAdapter<Module, SettingsModuleAdapter.Vh>(ModuleDiffUtil()) {

    inner class Vh(var binding: ItemModuleSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClick {
        fun onItemClick(module: Module)
        fun onEditClick(module: Module)
        fun onDeleteClick(module: Module)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemModuleSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SettingsModuleAdapter.Vh, position: Int) {
        val module = getItem(position)
        holder.binding.apply {
            nameTv.text = module.name
            image.setImageURI(Uri.parse(imagePath))
            number.text = module.number.toString()
            delete.setOnClickListener { listener.onDeleteClick(module) }
            edit.setOnClickListener { listener.onEditClick(module) }
            root.setOnClickListener { listener.onItemClick(module) }
        }
    }
}