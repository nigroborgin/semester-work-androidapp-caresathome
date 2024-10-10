package ru.marsu.semester_work_androidapp_caresathome.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.marsu.semester_work_androidapp_caresathome.ServiceLocation
import ru.marsu.semester_work_androidapp_caresathome.activity.EditTaskActivity
import ru.marsu.semester_work_androidapp_caresathome.adapter.TaskAdapter
import ru.marsu.semester_work_androidapp_caresathome.adapter.TaskListener
import ru.marsu.semester_work_androidapp_caresathome.databinding.FragmentCompletedTasksBinding
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository
import ru.marsu.semester_work_androidapp_caresathome.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.entity.Task


class CompletedTasksFragment(private val activityContext: Context, private val completedCountText: TextView) : Fragment(), TaskListener {

    private lateinit var binding: FragmentCompletedTasksBinding
    private var launcherForEdit: ActivityResultLauncher<Intent>? = null

    private val sl = ServiceLocation.instance
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskRepository: TaskRepository


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedTasksBinding.inflate(inflater)
        init()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context, completedCountText: TextView) = CompletedTasksFragment(context, completedCountText)
    }

    private fun init() {
        taskAdapter = TaskAdapter(this, activityContext)
        taskRepository = sl.services["taskRepository"] as TaskRepository
        binding.rvTasks.layoutManager = LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false)
        binding.rvTasks.adapter = taskAdapter
        // Status: 1-"completed", 2-"pending", 3-"missed"
        taskAdapter.add(taskRepository.getAllByStatus(Status(1)))
        setCountCompletedTasks()

        launcherForEdit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val taskIdUpdated = result.data?.getIntExtra("taskIdUpdated", -1)
                val adapterPosition = result.data?.getIntExtra("adapterPosition", -1)
                removeInAdapterList(adapterPosition!!)
                val taskById = taskRepository.getOneById(taskIdUpdated!!)
                // Status: 1-"completed", 2-"pending", 3-"missed"
                if (taskById.status!!.id == 1) {
                    taskAdapter.add(taskById)
                }
            }
            setCountCompletedTasks()
        }

    }

    override fun onClickDeleteTask(adapterPosition: Int) {
        val task = removeInAdapterList(adapterPosition)
        taskRepository.delete(task)
    }

    private fun removeInAdapterList(adapterPosition: Int): Task {
        val task = taskAdapter.taskList.removeAt(adapterPosition)
        taskAdapter.notifyItemRemoved(adapterPosition)
        taskAdapter.notifyItemRangeChanged(adapterPosition, taskAdapter.taskList.size)
        setCountCompletedTasks()
        return task
    }

    private fun setCountCompletedTasks() {
        completedCountText.text = String.format(taskAdapter.taskList.size.toString())
    }

    override fun onClickEditTask(adapterPosition: Int) {
        val task = taskAdapter.taskList[adapterPosition]
        val intent = Intent(activityContext, EditTaskActivity::class.java)
        intent.putExtra("task", task)
        intent.putExtra("adapterPosition", adapterPosition)
        launcherForEdit?.launch(intent)
    }

}