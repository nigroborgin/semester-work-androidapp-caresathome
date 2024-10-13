package ru.marsu.semester_work_androidapp_caresathome.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.marsu.semester_work_androidapp_caresathome.adapter.AilmentAdapter
import ru.marsu.semester_work_androidapp_caresathome.adapter.RemediationAdapter
import ru.marsu.semester_work_androidapp_caresathome.databinding.FragmentHomeBinding
import ru.marsu.semester_work_androidapp_caresathome.dto.AilmentDto
import ru.marsu.semester_work_androidapp_caresathome.dto.RemediationDto

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        init()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    fun init() {

        val ailmentHorizontalLinearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        val remediationHorizontalLinearLayoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        val ailmentAdapter = AilmentAdapter()
        val remediationAdapter = RemediationAdapter()

        binding.apply {
            rcvAilmentList.layoutManager = ailmentHorizontalLinearLayoutManager
            rcvAilmentList.adapter = ailmentAdapter
            rcvRemediationList.layoutManager = remediationHorizontalLinearLayoutManager
            rcvRemediationList.adapter = remediationAdapter

            // TODO: тестовая часть. Удалить
            tNumTasks.text = "20"
            tNumPatients.text = "10"
            tNamePatient.text = "Arvind Malhotra"
            tSexPatient.text = "Male"
            tAgePatient.text = "38 Years"
            ailmentAdapter.addAilment(AilmentDto(0, "Diabetes"))
            ailmentAdapter.addAilment(AilmentDto(1, "Asthama"))
            ailmentAdapter.addAilment(AilmentDto(2, "Tuberculosis"))
            tAilmentSizeList.text = "+12"
            remediationAdapter.addRemediation(RemediationDto(0, "Amino morphine", "2X", "A Day"))
            remediationAdapter.addRemediation(RemediationDto(1, "Insulin Injection", "3X", "A Day"))
            tRemediationsSizeList.text = "+45"
            tClickToNextTask.text = "Fill up Form Number 51"
        }
    }

}
