package ru.marsu.semester_work_androidapp_caresathome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.databinding.TaskItemForLabBinding
import ru.marsu.semester_work_androidapp_caresathome.entity.Task
import java.time.format.DateTimeFormatter

class TaskAdapter(private val listener: TaskListener, val context: Context) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    val taskList = ArrayList<Task>()

    class TaskHolder(item: View) : RecyclerView.ViewHolder(item) {
        // TODO: в курсовой заменить на нормальный TaskItem
        val binding = TaskItemForLabBinding.bind(item)

        fun bind(task: Task, listener: TaskListener, context: Context) = with(binding) {
            tvTitle.text = task.title
            tvDueDate.text = task.dueDateTime.toString()
            tvPeriodicity.text = task.periodicity?.period

            // Period: 1-"Everyday", 2-"Alternate Day", 3-"Third Day", 4-"Weekly", 5-"Monthly"
            when (task.periodicity?.id) {
                1 -> {
                    clPeriodicity.setBackgroundResource(R.drawable.background_periodicity_blue_green)
                    tvPeriodicity.setTextColor(context.getColor(R.color.blue_green3))
                }
                2 -> {
                    clPeriodicity.setBackgroundResource(R.drawable.background_periodicity_blue_green)
                    tvPeriodicity.setTextColor(context.getColor(R.color.blue))
                }
                3 -> {
                    clPeriodicity.setBackgroundResource(R.drawable.background_periodicity_green)
                    tvPeriodicity.setTextColor(context.getColor(R.color.green))
                }
                4 -> {
                    clPeriodicity.setBackgroundResource(R.drawable.background_periodicity_yellow)
                    tvPeriodicity.setTextColor(context.getColor(R.color.yellow))
                }
                5 -> {
                    clPeriodicity.setBackgroundResource(R.drawable.background_periodicity_red)
                    tvPeriodicity.setTextColor(context.getColor(R.color.red3))
                }
            }

            // Status: 1-"completed", 2-"pending", 3-"missed"
            when (task.status?.id) {
                1 -> {
                    clDueDate.setBackgroundResource(R.drawable.background_due_date_light_blue_green)
                    tvDueDate.setTextColor(context.getColor(R.color.blue_green3))
                    tvDueDate.text = StringBuilder()
                        .append("Completed on ")
                        .append(task.dueDateTime?.format(DateTimeFormatter.ofPattern("HH:mm")))
                        .toString()
                    icWatch.setImageResource(R.drawable.ic_watch_blue_green)
                }
                2 -> {
                    clDueDate.setBackgroundResource(R.drawable.background_due_date_light_red)
                    tvDueDate.setTextColor(context.getColor(R.color.red2))
                    tvDueDate.text = StringBuilder()
                        .append("Due by - ")
                        .append(task.dueDateTime?.format(DateTimeFormatter.ofPattern("HH:mm")))
                        .toString()
                    icWatch.setImageResource(R.drawable.ic_watch_red)
                }
                3 -> {
                    clDueDate.setBackgroundResource(R.drawable.background_due_date_light_red2)
                    tvDueDate.setTextColor(context.getColor(R.color.red4))
                    tvDueDate.text = StringBuilder()
                        .append("Missed on ")
                        .append(task.dueDateTime?.format(DateTimeFormatter.ofPattern("HH:mm")))
                        .toString()
                    icWatch.setImageResource(R.drawable.ic_watch_red2)
                }
            }

            tvPatientName.text = task.patient
            // TODO: в курсовой убрать
            cvViewPatientLocation.setOnClickListener {
                listener.onClickDeleteTask(adapterPosition)
            }

            cvCompleteTask.setOnClickListener {
                listener.onClickEditTask(adapterPosition)
            }
//            tvPatientPhone.text = task
//            tvLocation.text = task
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item_for_lab, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList[position], listener, context)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun add(task: Task) {
        taskList.add(task)
        notifyDataSetChanged()
    }

    fun add(list: List<Task>) {
        taskList.clear()
        taskList.addAll(list)
        notifyDataSetChanged()
    }

    fun reload() {
        notifyDataSetChanged()
    }

}
