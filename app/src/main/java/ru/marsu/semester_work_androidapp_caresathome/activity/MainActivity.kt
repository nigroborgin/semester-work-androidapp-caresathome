package ru.marsu.semester_work_androidapp_caresathome.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.adapter.AilmentAdapter
import ru.marsu.semester_work_androidapp_caresathome.adapter.RemediationAdapter
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMainBinding
import ru.marsu.semester_work_androidapp_caresathome.entity.Ailment
import ru.marsu.semester_work_androidapp_caresathome.entity.Remediation

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val ailmentHorizontalLinearLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        val remediationHorizontalLinearLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        val ailmentAdapter = AilmentAdapter()
        val remediationAdapter = RemediationAdapter()

        binding.apply {
            rcvAilmentList.layoutManager = ailmentHorizontalLinearLayoutManager
            rcvAilmentList.adapter = ailmentAdapter
            rcvRemediationList.layoutManager = remediationHorizontalLinearLayoutManager
            rcvRemediationList.adapter = remediationAdapter

            // TODO: тестовая часть. Удалить
            imAvatar.setImageResource(R.drawable.im_avatar)
            tNumTasks.text = "20"
            tNumPatients.text = "10"
            tNamePatient.text = "Arvind Malhotra"
            tSexPatient.text = "Male"
            tAgePatient.text = "38 Years"
            ailmentAdapter.addAilment(Ailment(0, "Diabetes"))
            ailmentAdapter.addAilment(Ailment(1, "Asthama"))
            ailmentAdapter.addAilment(Ailment(2, "Tuberculosis"))
            tAilmentSizeList.text = "+12"
            remediationAdapter.addRemediation(Remediation(0, "Amino morphine", "2X", "A Day"))
            remediationAdapter.addRemediation(Remediation(1, "Insulin Injection", "3X", "A Day"))
            tRemediationsSizeList.text = "+45"
            tClickToNextTask.text = "Fill up Form Number 51"
        }
    }

    fun onClickGoMyTasks(view : View) {
        val intent = Intent(this, MyTasksActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoMyPatients(view : View) {
        val intent = Intent(this, MyPatientsActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoPatientProfile(view : View) {
        val intent = Intent(this, PatientProfileActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoMyProfile(view : View) {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent)
    }

}
