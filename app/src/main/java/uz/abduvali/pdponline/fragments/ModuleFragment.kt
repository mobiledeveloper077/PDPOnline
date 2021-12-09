package uz.abduvali.pdponline.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.adapters.ModuleAdapter
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentModuleBinding
import uz.abduvali.pdponline.entities.Module

class ModuleFragment : Fragment(R.layout.fragment_module) {
    private var courseId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseId = it.getInt("courseId")
        }
    }

    private val binding by viewBinding(FragmentModuleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val course = service.courseDao().getCourseById(courseId)
        binding.apply {
            name.text = course.name
            val moduleAdapter =
                ModuleAdapter(course.imageUri, course.name, object : ModuleAdapter.OnItemClick {
                    override fun onItemClick(module: Module) {
                        val bundle = Bundle()
                        bundle.putInt("moduleId", module.id)
                        findNavController().navigate(
                            R.id.action_modulFragment_to_lessonNumberFragment,
                            bundle
                        )
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