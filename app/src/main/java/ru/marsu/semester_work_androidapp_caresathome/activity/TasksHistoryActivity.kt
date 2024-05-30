package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityTasksHistoryBinding

class TasksHistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityTasksHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
