package uz.abduvali.pdponline.fragments

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.RuntimePermission
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.abduvali.pdponline.R
import uz.abduvali.pdponline.database.AppDatabase
import uz.abduvali.pdponline.databinding.DialogAddImageBinding
import uz.abduvali.pdponline.databinding.FragmentEditCourseBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditCourseFragment : Fragment(R.layout.fragment_edit_course) {

    private var courseId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseId = it.getInt("courseId")
        }
    }

    private val binding by viewBinding(FragmentEditCourseBinding::bind)
    private var imagePath = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = AppDatabase.getInstance(requireContext())
        val course = service.courseDao().getCourseById(courseId)
        imagePath = course.imageUri
        binding.apply {
            nameTv.text = course.name
            name.setText(course.name)
            image.setImageURI(Uri.parse(course.imageUri))
            image.setOnClickListener {
                val dialogBinding = DialogAddImageBinding.inflate(layoutInflater)
                val dialog = AlertDialog.Builder(requireContext())
                    .setView(dialogBinding.root)
                    .show()
                dialogBinding.camera.setOnClickListener {
                    RuntimePermission.askPermission(
                        this@EditCourseFragment,
                        Manifest.permission.CAMERA
                    ).onAccepted {
                        val photoFile = try {
                            createImageFile()
                        } catch (e: Exception) {
                            null
                        }
                        photoFile?.also {
                            val uri =
                                FileProvider.getUriForFile(
                                    requireContext(),
                                    activity?.applicationContext?.packageName + ".provider",
                                    it
                                )
                            getCameraImage.launch(uri)
                            dialog.dismiss()
                        }
                    }.onDenied {
                        AlertDialog.Builder(requireContext())
                            .setMessage("Rasmga olish uchun ilovaga ruxsat berasizmi?")
                            .setPositiveButton("Ha") { _, _ ->
                                it.askAgain()
                            }
                            .setNegativeButton("Yo'q") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }.onForeverDenied {
                        it.goToSettings()
                    }.ask()
                }
                dialogBinding.gallery.setOnClickListener {
                    RuntimePermission.askPermission(
                        this@EditCourseFragment,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ).onAccepted {
                        getImageContent.launch("image/*")
                        dialog.dismiss()
                    }.onDenied {
                        AlertDialog.Builder(requireContext())
                            .setMessage("Rasmlarni o'qish uchun ilovaga ruxsat berasizmi?")
                            .setPositiveButton("Ha") { _, _ ->
                                it.askAgain()
                            }
                            .setNegativeButton("Yo'q") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }.onForeverDenied {
                        it.goToSettings()
                    }.ask()
                }
            }
            save.setOnClickListener {
                val name = name.text?.trim().toString()
                if (name != "" && imagePath != "") {
                    service.courseDao().editCourse(course.copy(name = name, imageUri = imagePath))
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Ma'lumotlarni kiriting", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) {
                Toast.makeText(requireContext(), "Rasm tanlanmadi", Toast.LENGTH_SHORT).show()
            } else {
                binding.image.setImageURI(uri)
                val openInputStream = activity?.contentResolver?.openInputStream(uri)
                val m = System.currentTimeMillis()
                val file = File(activity?.filesDir, "$m.jpg")
                val fileOutputStream = FileOutputStream(file)
                openInputStream?.copyTo(fileOutputStream)
                openInputStream?.close()
                fileOutputStream.close()
                imagePath = file.absolutePath
            }
        }

    private val getCameraImage =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                binding.image.setImageURI(Uri.fromFile(File(imagePath)))
            }
        }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val m = System.currentTimeMillis()
        val externalFilesDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("G21_$m", ".jpg", externalFilesDir)
            .apply {
                imagePath = absolutePath
            }
    }
}