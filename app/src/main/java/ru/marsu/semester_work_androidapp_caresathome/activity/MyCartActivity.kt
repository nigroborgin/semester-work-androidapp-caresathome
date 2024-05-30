package ru.marsu.semester_work_androidapp_caresathome.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyCartBinding

class MyCartActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
