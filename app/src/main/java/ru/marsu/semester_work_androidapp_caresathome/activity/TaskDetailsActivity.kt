package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyTasksBinding

class TaskDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickBack(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }

}
