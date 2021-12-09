package uz.abduvali.pdponline.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.adapters.SettingsModuleAdapter
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentAddModuleBinding
import uz.abduvali.pdponline.entities.Module

class AddModuleFragment : Fragment(R.layout.fragment_add_module) {

    private val binding by viewBinding(FragmentAddModuleBinding::bind)
    private var courseId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            courseId = it?.getInt("courseId") ?: 0
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val course = service.courseDao().getCourseById(courseId)
        binding.apply {
            nameTv.text = course.name
            add.setOnClickListener {
                val name = name.text?.trim().toString()
                val number = number.text?.trim().toString()
                if (name != "" && number != "") {
                    service.moduleDao()
                        .addModule(
                            Module(
                                name = name,
                                number = number.toInt(),
                                courseId = courseId
                            )
                        )
                } else {
                    Toast.makeText(requireContext(), "Ma'lumotlarni kiriting", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            val moduleAdapter =
                SettingsModuleAdapter(course.imageUri, object : SettingsModuleAdapter.OnItemClick {
                    override fun onItemClick(module: Module) {
                        val bundle = Bundle()
                        bundle.putInt("moduleId", module.id)
                        bundle.putString("imagePath", course.imageUri)
                        findNavController().navigate(
                            R.id.action_addModuleFragment_to_addLessonFragment,
                            bundle
                        )
                    }

                    override fun onEditClick(module: Module) {
                        val bundle = Bundle()
                        bundle.putInt("moduleId", module.id)
                        findNavController().navigate(
                            R.id.action_addModuleFragment_to_editModuleFragment,
                            bundle
                        )
                    }

                    override fun onDeleteClick(module: Module) {
                        AlertDialog.Builder(requireContext())
                            .setMessage("Bu modul ichidagi barcha darslar bilan o'chib ketishiga rozimisiz?")
                            .setPositiveButton(
                                "Ha"
                            ) { _, _ ->
                                service.moduleDao().deleteModule(module)
                            }.setNegativeButton(
                                "Yo'q"
                            ) { dialogInterface, _ ->
                                dialogInterface.dismiss()
                            }.show()
                    }
                })
            rv.adapter = moduleAdapter
            service.moduleDao().getModulesByCourseId(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    moduleAdapter.submitList(it)
                }
        }
    }
}