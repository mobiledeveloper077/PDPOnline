package uz.abduvali.pdponline.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.adapters.LessonNumberAdapter
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentLessonNumberBinding
import uz.abduvali.pdponline.entities.Lesson

class LessonNumberFragment : Fragment(R.layout.fragment_lesson_number) {
    private var moduleId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            moduleId = it.getInt("moduleId")
        }
    }

    private val binding by viewBinding(FragmentLessonNumberBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val module = service.moduleDao().getModuleById(moduleId)
        binding.apply {
            name.text = module.name
            val lessonNumberAdapter = LessonNumberAdapter(object : LessonNumberAdapter.OnItemClick {
                override fun onItemClick(lesson: Lesson) {
                    val bundle = Bundle()
                    bundle.putInt("lessonId", lesson.id)
                    findNavController().navigate(
                        R.id.action_lessonNumberFragment_to_lessonFragment,
                        bundle
                    )
                }
            })
            rv.adapter = lessonNumberAdapter
            service.lessonDao().getLessonsByModuleId(moduleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    lessonNumberAdapter.submitList(it)
                }
        }
    }
}