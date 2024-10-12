package ru.marsu.semester_work_androidapp_caresathome.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.ServiceLocation
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMyTasksBinding
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.fragment.CompletedTasksFragment
import ru.marsu.semester_work_androidapp_caresathome.fragment.FragmentsOfTasksActivity
import ru.marsu.semester_work_androidapp_caresathome.fragment.PendingTasksFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTasksBinding
    private val sl = ServiceLocation.instance
    private lateinit var taskRepository: TaskRepository

    private var selectedFrame: FragmentsOfTasksActivity = FragmentsOfTasksActivity.PENDING
    private var launcherForCreate: ActivityResultLauncher<Intent>? = null

    private lateinit var pendingBackground: ConstraintLayout
    private lateinit var completedBackground: ConstraintLayout
    private lateinit var pendingText: TextView
    private lateinit var completedText: TextView
    lateinit var pendingCountText: TextView
    lateinit var completedCountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pendingBackground = binding.clPending
        completedBackground = binding.clCompleted

        pendingText = binding.tvPending
        completedText = binding.tvCompleted

        pendingCountText = binding.tvCountPendingTasks
        completedCountText = binding.tvCountCompletedTasks

        sl.init(this)
        taskRepository = sl.services["taskRepository"] as TaskRepository
        // Status: 1-"completed", 2-"pending", 3-"missed"
        completedCountText.text = String.format(taskRepository.getByStatus(StatusDto(1)).size.toString())
        pendingCountText.text = String.format(taskRepository.getByStatus(StatusDto(2)).size.toString())

        selectPendingTab()

        launcherForCreate = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val taskIdInserted = result.data?.getIntExtra("taskIdInserted", -1)
                    val taskById = taskIdInserted?.let { taskRepository.getOneById(it) }
                    // Status: 1-"completed", 2-"pending", 3-"missed"
                    if (taskById!!.status!!.id == 1) {
                        unselectPendingTab()
                        selectCompletedTab()
                        selectedFrame = FragmentsOfTasksActivity.COMPLETED
                    } else {
                        unselectCompletedTab()
                        selectPendingTab()
                        selectedFrame = FragmentsOfTasksActivity.PENDING
                    }
                }
        }
        binding.tvDateToday.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM yyyy")).toString()
        binding.tvDayOfWeek.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE")).toString()
    }

    fun onClickCreateTask(view: View) {
        launcherForCreate?.launch(Intent(this, CreateTaskActivity::class.java))
    }

    fun onClickBack(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }



    fun onClickOpenPendingTasksFrame(view: View) {
        if (selectedFrame != FragmentsOfTasksActivity.PENDING) {
            unselectCompletedTab()
            selectPendingTab()
            selectedFrame = FragmentsOfTasksActivity.PENDING
        }
    }

    fun onClickOpenCompletedTasksFrame(view: View) {
        if (selectedFrame != FragmentsOfTasksActivity.COMPLETED) {
            unselectPendingTab()
            selectCompletedTab()
            selectedFrame = FragmentsOfTasksActivity.COMPLETED
        }
    }

    private fun selectCompletedTab() {
        completedBackground.setBackgroundResource(R.drawable.background_task_tabs_active)
        completedText.setTextColor(getColor(R.color.white))
        completedBackground.startAnimation(createAnimationForTabs())

        openFragment(CompletedTasksFragment.newInstance(this, completedCountText))
    }

    private fun selectPendingTab() {
        pendingBackground.setBackgroundResource(R.drawable.background_task_tabs_active)
        pendingText.setTextColor(getColor(R.color.white))
        pendingBackground.startAnimation(createAnimationForTabs())

        openFragment(PendingTasksFragment.newInstance(this, pendingCountText))
    }

    private fun unselectCompletedTab() {
        completedBackground.setBackgroundColor(getColor(R.color.transparent))
        completedText.setTextColor(getColor(R.color.black))
    }

    private fun unselectPendingTab() {
        pendingBackground.setBackgroundColor(getColor(R.color.transparent))
        pendingText.setTextColor(getColor(R.color.black))
    }

    private fun createAnimationForTabs(): Animation {
        val scaleAnimation = ScaleAnimation(
            0.8f,
            1.0f,
            1.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f
        )
        scaleAnimation.setDuration(200)
        scaleAnimation.setFillAfter(true)

        return scaleAnimation
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcvTasks, fragment)
            .commit()
    }

}
