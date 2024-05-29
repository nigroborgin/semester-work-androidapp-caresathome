package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyCartBinding
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyPatientsBinding

class MyCartActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}