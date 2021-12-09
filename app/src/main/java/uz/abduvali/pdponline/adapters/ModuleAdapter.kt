package uz.abduvali.pdponline.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.ItemModuleBinding
import uz.abduvali.pdponline.entities.Module
import uz.abduvali.pdponline.utils.ModuleDiffUtil

class ModuleAdapter(var imagePath: String, var courseName: String, var listener: OnItemClick) :
    ListAdapter<Module, ModuleAdapter.Vh>(ModuleDiffUtil()) {

    inner class Vh(var binding: ItemModuleBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClick {
        fun onItemClick(module: Module)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ModuleAdapter.Vh, position: Int) {
        val module = getItem(position)
        holder.binding.apply {
            nameTv.text = module.name
            image.setImageURI(Uri.parse(imagePath))
            description.text = courseName
            root.setOnClickListener { listener.onItemClick(module) }
            AppDatabase.getInstance(root.context).lessonDao().getLessonsByModuleId(module.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    number.text = it.size.toString()
                }
        }
    }
}