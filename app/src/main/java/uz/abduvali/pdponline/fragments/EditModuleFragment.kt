package uz.abduvali.pdponline.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.FragmentEditModuleBinding

class EditModuleFragment : Fragment(R.layout.fragment_edit_module) {
    private var moduleId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            moduleId = it.getInt("moduleId")
        }
    }

    private val binding by viewBinding(FragmentEditModuleBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val module = service.moduleDao().getModuleById(moduleId)
        binding.apply {
            nameTv.text = module.name
            name.setText(module.name)
            number.setText(module.number.toString())
            save.setOnClickListener {
                val name = name.text?.trim().toString()
                val number = number.text?.trim().toString()
                if (name != "" && number != "") {
                    service.moduleDao()
                        .editModule(
                            module.copy(
                                name = name,
                                number = number.toInt()
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