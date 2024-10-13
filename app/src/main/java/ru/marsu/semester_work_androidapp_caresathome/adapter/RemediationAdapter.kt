package ru.marsu.semester_work_androidapp_caresathome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.databinding.RemediationItemBinding
import ru.marsu.semester_work_androidapp_caresathome.dto.RemediationDto

class RemediationAdapter : RecyclerView.Adapter<RemediationAdapter.RemediationHolder>(){

    val remediationDtoList = ArrayList<RemediationDto>()

    class RemediationHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = RemediationItemBinding.bind(item)

        fun bind(remediationDto: RemediationDto) /*with(binding)*/ { //позволяет не писать "binding" внутри функции для обращения к его элементам
            binding.tTitle.text = remediationDto.title
            binding.tFrequency.text = remediationDto.frequency
            binding.tPeriodicity.text = remediationDto.periodicity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemediationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.remediation_item, parent, false)
        return RemediationHolder(view)
    }

    override fun onBindViewHolder(holder: RemediationHolder, position: Int) {
        holder.bind(remediationDtoList[position])
    }

    override fun getItemCount(): Int {
        return remediationDtoList.size
    }

    fun addRemediation(remediationDto: RemediationDto) {
        remediationDtoList.add(remediationDto)
        notifyDataSetChanged()
    }
}