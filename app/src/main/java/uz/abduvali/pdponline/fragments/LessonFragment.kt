package uz.abduvali.pdponline.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentLessonBinding

class LessonFragment : Fragment(R.layout.fragment_lesson) {
    private var lessonId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lessonId = it.getInt("lessonId")
        }
    }

    private val binding by viewBinding(FragmentLessonBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val lesson = service.lessonDao().getLessonById(lessonId)
        binding.apply {
            number.text = lesson.number.toString() + "-dars"
            name.text = lesson.name
            description.text = lesson.description
            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}