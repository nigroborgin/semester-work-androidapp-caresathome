package ru.marsu.semester_work_androidapp_caresathome.adapter

interface TaskListener {

    fun onClickDeleteTask(adapterPosition: Int)
    fun onClickEditTask(adapterPosition: Int)

}
