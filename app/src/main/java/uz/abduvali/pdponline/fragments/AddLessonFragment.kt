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
import uz.abduvali.pdponline.adapters.SettingsLessonAdapter
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentAddLessonBinding
import uz.abduvali.pdponline.entities.Lesson

class AddLessonFragment : Fragment(R.layout.fragment_add_lesson) {
    private var moduleId = -1
    private var imagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            moduleId = it.getInt("moduleId")
            imagePath = it.getString("imagePath") ?: ""
        }
    }

    private val binding by viewBinding(FragmentAddLessonBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val module = service.moduleDao().getModuleById(moduleId)
        binding.apply {
            nameTv.text = module.name
            add.setOnClickListener {
                val name = name.text?.trim().toString()
                val description = description.text?.trim().toString()
                val number = number.text?.trim().toString()
                if (name != "" && description != "" && number != "") {
                    service.lessonDao()
                        .addLesson(
                            Lesson(
                                name = name,
                                number = number.toInt(),
                                description = description,
                                moduleId = moduleId
                            )
                        )
                } else {
                    Toast.makeText(requireContext(), "Ma'lumotlarni kiriting", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            val lessonAdapter =
                SettingsLessonAdapter(imagePath, object : SettingsLessonAdapter.OnItemClick {
                    override fun onEditClick(lesson: Lesson) {
                        val bundle = Bundle()
                        bundle.putInt("lessonId", lesson.id)
                        findNavController().navigate(
                            R.id.action_addLessonFragment_to_editLessonFragment,
                            bundle
                        )
                    }

                    override fun onDeleteClick(lesson: Lesson) {
                        AlertDialog.Builder(requireContext())
                            .setMessage("Dars o'chirilishiga rozimisiz?")
                            .setPositiveButton(
                                "Ha"
                            ) { _, _ ->
                                service.lessonDao().deleteLesson(lesson)
                            }.setNegativeButton(
                                "Yo'q"
                            ) { dialogInterface, _ ->
                                dialogInterface.dismiss()
                            }.show()
                    }
                })
            rv.adapter = lessonAdapter
            service.lessonDao().getLessonsByModuleId(moduleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    lessonAdapter.submitList(it)
                }
        }
    }
}