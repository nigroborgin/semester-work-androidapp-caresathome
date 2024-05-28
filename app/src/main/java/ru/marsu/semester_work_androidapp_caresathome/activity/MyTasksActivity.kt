package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyProfileBinding
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyTasksBinding

class MyTasksActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
