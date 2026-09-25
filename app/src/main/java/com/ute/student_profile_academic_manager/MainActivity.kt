package com.ute.student_profile_academic_manager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.student_profile_academic_manager.databinding.ActivityMainBinding
import com.ute.student_profile_academic_manager.model.Student
import com.ute.student_profile_academic_manager.utils.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val defaultStudent = Student(
        id = "2415053122117",
        name = "Võ Minh Huy",
        className = "24T1",
        email = "2415053122117@sv.ute.udn.vn",
        gpa = 3.75
    )
//
    private var currentStudent = defaultStudent

    companion object {
        private const val KEY_STUDENT_DATA = "EXTRA_KEY_STUDENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.getSerializable(KEY_STUDENT_DATA)?.let {
            currentStudent = it as Student
        }

        bindStudentData(currentStudent)

        binding.btnUpdateGpa.setOnClickListener {
            // Sử dụng extension trimmedText()
            val rawInput = binding.edtGpaInput.trimmedText()

            val newGpa = rawInput.toDoubleOrNull()

            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtGpaInput.error = "GPA phải từ 0.0 đến 4.0"
                return@setOnClickListener
            }

            currentStudent = currentStudent.copy(gpa = newGpa)
            bindStudentData(currentStudent)
            toast("Đã cập nhật GPA thành công!")
        }

        binding.btnReset.setOnClickListener {
            currentStudent = defaultStudent
            bindStudentData(currentStudent)
            toast("Đã khôi phục dữ liệu mặc định!")
        }
    }

    private fun bindStudentData(student: Student) {
        with(binding) {
            tvStudentName.text = student.name
            tvStudentDetails.text = "MSSV: ${student.id} - Lớp: ${student.className}"
            tvStudentEmail.text = "Email: ${student.email}"

            tvGpaBadge.text = "${student.gpa} GPA - ${student.gpa.toAcademicRanking()}"

            edtGpaInput.setText(student.gpa.toString())

            edtGpaInput.error = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STUDENT_DATA, currentStudent)
    }
}