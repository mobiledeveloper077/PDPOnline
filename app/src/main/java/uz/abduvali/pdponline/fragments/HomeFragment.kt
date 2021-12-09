package uz.abduvali.pdponline.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.adapters.CourseAdapter
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentHomeBinding
import uz.abduvali.pdponline.entities.Course
import uz.abduvali.pdponline.entities.Module

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        binding.apply {
            val courseAdapter = CourseAdapter(object : CourseAdapter.OnItemClick {
                override fun onAllClick(course: Course) {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_modulFragment,
                        Bundle().apply {
                            putInt("courseId", course.id)
                        }
                    )
                }

                override fun onItemClick(module: Module) {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_lessonNumberFragment,
                        Bundle().apply { putInt("moduleId", module.id) }
                    )
                }
            })
            rv.adapter = courseAdapter
            settings.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addCourseFragment)
            }
            service.courseDao().getAllCourses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    courseAdapter.submitList(it)
                }
        }
    }
}