package uz.abduvali.pdponline.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abduvali.pdponline.databinding.ItemModuleNameBinding
import uz.abduvali.pdponline.entities.Module
import uz.abduvali.pdponline.utils.ModuleDiffUtil

class ModuleNameAdapter(var listener: CourseAdapter.OnItemClick) :
    ListAdapter<Module, ModuleNameAdapter.Vh>(ModuleDiffUtil()) {

    inner class Vh(var binding: ItemModuleNameBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemModuleNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ModuleNameAdapter.Vh, position: Int) {
        val module = getItem(position)
        holder.binding.apply {
            nameTv.text = module.name
            nameTv.setOnClickListener { listener.onItemClick(module) }
        }
    }
}