package ru.marsu.semester_work_androidapp_caresathome.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.ServiceLocator
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityCreateTaskBinding
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.repository.RoomTaskRepository
import ru.marsu.semester_work_androidapp_caresathome.dto.PeriodicityDto
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.dto.TaskDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CreateTaskActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener,
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityCreateTaskBinding
    private var taskRepository = ServiceLocator.instance.taskRepository

    private var periodicityPosition: Int = 0
    private var timePickerDialog: TimePickerDialog? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var dueOnDate: LocalDate = LocalDate.now()
    private var dueOnTime: LocalTime = LocalTime.now()
    private var timeWitchCompleted: LocalDateTime? = null
    private var isCompleted: Boolean = false
    private var isBloodCollected: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sPeriodicity.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.periodicity_array,
            android.R.layout.simple_spinner_item
        ).also { sp -> sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.sPeriodicity.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.periodicityPosition = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dueOnDate = LocalDate.of(year, month+1, dayOfMonth)
        binding.tvInputDate.text = StringBuilder().append(dayOfMonth).append('.').append(month+1).append('.').append(year).toString()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dueOnTime = LocalTime.of(hourOfDay, minute)
        binding.tvInputTime.text = StringBuilder().append(hourOfDay).append(':').append(minute).toString()
    }

    fun onClickSwitchCompleted(view: View) {
        if (!isCompleted) {
            isCompleted = true
            timeWitchCompleted = LocalDateTime.now()
            binding.clMarkCompleted.setBackgroundResource(R.drawable.background_mark_uncompleted)
            binding.tvMarkCompleted.setTextColor(getColor(R.color.white))
            binding.tvMarkCompleted.text = "Completed"
        } else {
            isCompleted = false
            timeWitchCompleted = null
            binding.clMarkCompleted.setBackgroundResource(R.drawable.background_mark_completed)
            binding.tvMarkCompleted.setTextColor(getColor(R.color.blue_green3))
            binding.tvMarkCompleted.text = "Mark Completed"
        }
    }

    fun onClickSwitchBloodCollected(view: View) {
        if (!isBloodCollected) {
            isBloodCollected = true
            binding.tvBloodSampleCollectionMarkCollected1.text = "Collected"
        } else {
            isBloodCollected = false
            binding.tvBloodSampleCollectionMarkCollected1.text = "Mark as Collected"
        }
    }

    fun onClickOpenDatePickerDialog(view: View) {
        if (datePickerDialog == null) {
            datePickerDialog = DatePickerDialog(this)
            datePickerDialog!!.setOnDateSetListener(this)
        }
        datePickerDialog!!.show()
    }

    fun onClickOpenTimePickerDialog(view: View) {
        if (timePickerDialog == null) {
            timePickerDialog = TimePickerDialog(this, this, 0, 0, true)
        }
        timePickerDialog!!.show()
    }

    fun onClickCreateTask(view: View) {
        val taskDto = TaskDto()

        taskDto.title = checkAndReturnString(binding.inTitle.text.toString())
        taskDto.patient = checkAndReturnString(binding.inPatientName.text.toString())
        taskDto.periodicity = PeriodicityDto(periodicityPosition+1)
        taskDto.bloodPressure = binding.inBloodPressure1.text.toString()

        if (checkAndReturnString(binding.inHeartRate1.text.toString()).isDigitsOnly()) {
            taskDto.heartRate = binding.inHeartRate1.text.toString().toInt()
        }

        val localDateTime = LocalDateTime.of(dueOnDate, dueOnTime)
        taskDto.dueDateTime = localDateTime
        // Status: 1-"completed", 2-"pending", 3-"missed"
        taskDto.status = if (localDateTime.isAfter(LocalDateTime.now())) StatusDto(2) else StatusDto(3)
        if (isCompleted) {
            taskDto.status = StatusDto(1)
            taskDto.timeWitchCompleted = timeWitchCompleted
        }
        taskDto.isBloodSampleCollection = isBloodCollected
        val idInserted = taskRepository.create(taskDto)

        val intent = Intent()
        intent.putExtra("taskIdInserted", idInserted)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun onClickBack(view: View) {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun checkAndReturnString(str: String) : String {
        if (str == "") {
            return "unknown"
        } else {
            return str
        }
    }

}
