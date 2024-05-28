package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyTasksBinding
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityPatientProfileBinding

class PatientProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityPatientProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
