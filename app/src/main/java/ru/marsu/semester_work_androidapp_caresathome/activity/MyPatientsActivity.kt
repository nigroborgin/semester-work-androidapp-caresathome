package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMainBinding
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyPatientsBinding

class MyPatientsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyPatientsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPatientsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
