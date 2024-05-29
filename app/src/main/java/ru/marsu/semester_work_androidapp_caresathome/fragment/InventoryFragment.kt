package ru.marsu.semester_work_androidapp_caresathome.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.marsu.semester_work_androidapp_caresathome.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment() {

    lateinit var binding: FragmentInventoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInventoryBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = InventoryFragment()
    }

}
