package ru.marsu.semester_work_androidapp_caresathome.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.ServiceLocation
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityEditTaskBinding
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository
import ru.marsu.semester_work_androidapp_caresathome.entity.Periodicity
import ru.marsu.semester_work_androidapp_caresathome.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.entity.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EditTaskActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener,
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityEditTaskBinding
    private val sl = ServiceLocation.instance
    private var taskRepository = sl.services["taskRepository"] as TaskRepository
    private lateinit var intent: Intent

    private var lastTaskId: Int = -1
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
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent = this.getIntent()
        val task = intent.getSerializableExtra("task", Task::class.java)
        if (task != null) {
            init(task)
        }
    }

    private fun init(task: Task) {
        binding.apply {
            lastTaskId = task.id!!
            inTitle.text = Editable.Factory.getInstance().newEditable(task.title)

            tvInputTime.text = task.dueDateTime?.format(DateTimeFormatter.ofPattern("HH:mm"))
            dueOnTime = task.dueDateTime!!.toLocalTime()
            timePickerDialog = TimePickerDialog(
                this@EditTaskActivity,
                this@EditTaskActivity,
                task.dueDateTime?.hour!!,
                task.dueDateTime?.minute!!,
                true)

            tvInputDate.text = task.dueDateTime?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            dueOnDate = task.dueDateTime!!.toLocalDate()
            datePickerDialog = DatePickerDialog(
                this@EditTaskActivity,
                this@EditTaskActivity,
                task.dueDateTime?.year!!,
                task.dueDateTime?.monthValue!!,
                task.dueDateTime?.dayOfMonth!!)

            sPeriodicity.adapter = ArrayAdapter.createFromResource(
                this@EditTaskActivity,
                R.array.periodicity_array,
                android.R.layout.simple_spinner_item
            ).also { sp -> sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            sPeriodicity.onItemSelectedListener = this@EditTaskActivity
            sPeriodicity.setSelection(task.periodicity?.id!! - 1)
            periodicityPosition = task.periodicity?.id!! - 1

            // Status: 1-"completed", 2-"pending", 3-"missed"
            isCompleted = task.status?.id != 1
            checkAndSwitchCompleted()

            timeWitchCompleted = task.timeWitchCompleted

            inPatientName.text = Editable.Factory.getInstance().newEditable(task.patient)
            inBloodPressure1.text = Editable.Factory.getInstance().newEditable(task.bloodPressure)
            inHeartRate1.text = Editable.Factory.getInstance().newEditable(task.heartRate.toString())

            isBloodCollected = task.isBloodSampleCollection == true
            tvBloodSampleCollectionMarkCollected1.text =
                if (task.isBloodSampleCollection == true) "Collected" else "Mark as Collected"
        }
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
        checkAndSwitchCompleted()
    }

    private fun checkAndSwitchCompleted() {
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

    fun onClickSaveTask(view: View) {
        val task = Task()

        task.id = lastTaskId
        task.title = checkAndReturnString(binding.inTitle.text.toString())
        task.patient = checkAndReturnString(binding.inPatientName.text.toString())
        task.periodicity = Periodicity(periodicityPosition+1)
        task.bloodPressure = checkAndReturnString(binding.inBloodPressure1.text.toString())

        if (checkAndReturnString(binding.inHeartRate1.text.toString()).isDigitsOnly()) {
            task.heartRate = binding.inHeartRate1.text.toString().toInt()
        }

        val localDateTime = LocalDateTime.of(dueOnDate, dueOnTime)
        task.dueDateTime = localDateTime
        // Status: 1-"completed", 2-"pending", 3-"missed"
        task.status = if (localDateTime.isAfter(LocalDateTime.now())) Status(2) else Status(3)
        if (isCompleted) {
            task.status = Status(1)
            task.timeWitchCompleted = timeWitchCompleted
        }
        task.isBloodSampleCollection = isBloodCollected
        taskRepository.update(task)
        val idUpdated = task.id

        val returnIntent = Intent()
        returnIntent.putExtra("taskIdUpdated", idUpdated)
        returnIntent.putExtra("adapterPosition", intent.getIntExtra("adapterPosition", -1))
        setResult(Activity.RESULT_OK, returnIntent)
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
