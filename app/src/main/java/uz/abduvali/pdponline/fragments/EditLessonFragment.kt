package uz.abduvali.pdponline.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentEditLessonBinding

class EditLessonFragment : Fragment(R.layout.fragment_edit_lesson) {
    private var lessonId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lessonId = it.getInt("lessonId")
        }
    }

    private val binding by viewBinding(FragmentEditLessonBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val lesson = service.lessonDao().getLessonById(lessonId)
        binding.apply {
            nameTv.text = lesson.number.toString() + "-dars"
            name.setText(lesson.name)
            description.setText(lesson.description)
            number.setText(lesson.number.toString())
            save.setOnClickListener {
                val name = name.text?.trim().toString()
                val description = description.text?.trim().toString()
                val number = number.text?.trim().toString()
                if (name != "" && description != "" && number != "") {
                    service.lessonDao()
                        .editLesson(
                            lesson.copy(
                                name = name,
                                number = number.toInt(),
                                description = description
                            )
                        )
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Ma'lumotlarni kiriting", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}