package ru.marsu.semester_work_androidapp_caresathome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.databinding.RemediationItemBinding
import ru.marsu.semester_work_androidapp_caresathome.entity.Remediation

class RemediationAdapter : RecyclerView.Adapter<RemediationAdapter.RemediationHolder>(){

    val remediationList = ArrayList<Remediation>()

    class RemediationHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = RemediationItemBinding.bind(item)

        fun bind(remediation: Remediation) /*with(binding)*/ { //позволяет не писать "binding" внутри функции для обращения к его элементам
            binding.tTitle.text = remediation.title
            binding.tFrequency.text = remediation.frequency
            binding.tPeriodicity.text = remediation.periodicity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemediationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.remediation_item, parent, false)
        return RemediationHolder(view)
    }

    override fun onBindViewHolder(holder: RemediationHolder, position: Int) {
        holder.bind(remediationList[position])
    }

    override fun getItemCount(): Int {
        return remediationList.size
    }

    fun addRemediation(remediation: Remediation) {
        remediationList.add(remediation)
        notifyDataSetChanged()
    }
}