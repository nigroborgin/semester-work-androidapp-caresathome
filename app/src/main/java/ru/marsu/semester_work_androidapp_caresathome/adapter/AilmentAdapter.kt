package ru.marsu.semester_work_androidapp_caresathome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.databinding.AilmentItemBinding
import ru.marsu.semester_work_androidapp_caresathome.dto.AilmentDto

class AilmentAdapter : RecyclerView.Adapter<AilmentAdapter.AilmentHolder>(){

    val ailmentDtoList = ArrayList<AilmentDto>()

    class AilmentHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = AilmentItemBinding.bind(item)

        fun bind(ailmentDto: AilmentDto) /*with(binding)*/ { //позволяет не писать "binding" внутри функции для обращения к его элементам
            binding.tTitle.text = ailmentDto.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AilmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ailment_item, parent, false)
        return AilmentHolder(view)
    }

    override fun onBindViewHolder(holder: AilmentHolder, position: Int) {
        holder.bind(ailmentDtoList[position])
    }

    override fun getItemCount(): Int {
        return ailmentDtoList.size
    }

    fun addAilment(ailmentDto: AilmentDto) {
        ailmentDtoList.add(ailmentDto)
        notifyDataSetChanged()
    }
}