package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyPatientsBinding
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyProfileBinding

class MyProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
