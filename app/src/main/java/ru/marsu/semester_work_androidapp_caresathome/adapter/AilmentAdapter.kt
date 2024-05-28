package ru.marsu.semester_work_androidapp_caresathome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.databinding.AilmentItemBinding
import ru.marsu.semester_work_androidapp_caresathome.entity.Ailment

class AilmentAdapter : RecyclerView.Adapter<AilmentAdapter.AilmentHolder>(){

    val ailmentList = ArrayList<Ailment>()

    class AilmentHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = AilmentItemBinding.bind(item)

        fun bind(ailment: Ailment) /*with(binding)*/ { //позволяет не писать "binding" внутри функции для обращения к его элементам
            binding.tTitle.text = ailment.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AilmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ailment_item, parent, false)
        return AilmentHolder(view)
    }

    override fun onBindViewHolder(holder: AilmentHolder, position: Int) {
        holder.bind(ailmentList[position])
    }

    override fun getItemCount(): Int {
        return ailmentList.size
    }

    fun addAilment(ailment: Ailment) {
        ailmentList.add(ailment)
        notifyDataSetChanged()
    }
}