package ru.marsu.semester_work_androidapp_caresathome

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import io.realm.kotlin.UpdatePolicy

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass

import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.converter.PeriodicityConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.converter.StatusConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.converter.TaskConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Task

import ru.marsu.semester_work_androidapp_caresathome.dto.PeriodicityDto
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.dto.TaskDto
import java.time.LocalDateTime


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RoomRepositoryDbTest {

    private val sl = ServiceLocator.instance
    private lateinit var testTaskDto: TaskDto
    private lateinit var testTaskEntity: Task

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            val variantDb = ServiceLocator.DbVariant.ROOM
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            ServiceLocator.instance.init(appContext, variantDb)
        }
    }

    @Before
    fun initTestTask() {
        testTaskDto = TaskDto().apply {
            id = 1
            title = "title"
            dueDateTime = LocalDateTime.MAX
            timeWitchCompleted = LocalDateTime.MAX
            periodicity = PeriodicityDto(1, "Everyday")
            status = StatusDto(1, "completed")
            patient = "patient"
            bloodPressure = "120/60"
            heartRate = 65
            isBloodSampleCollection = true
        }
        testTaskEntity = Task().apply {
            id = 1
            title = "title"
            dueDateTime = LocalDateTime.MAX.toString()
            timeWitchCompleted = LocalDateTime.MAX.toString()
            periodicityId = 1
            statusId = 1
            patient = "patient"
            bloodPressure = "120/60"
            heartRate = 65
            isBloodSampleCollection = 1
        }
    }

    @After
    fun clearTasks() {
        ServiceLocator.instance.roomLocalDb.taskDao().deleteAll()
    }

    private fun getTaskDtoList(): List<TaskDto> {
        val taskDto1 = TaskDto().apply {
            id = 1
            title = ""
            dueDateTime = LocalDateTime.MAX
            timeWitchCompleted = LocalDateTime.MAX
            periodicity = PeriodicityDto(1, "Everyday")
            status = StatusDto(1, "completed")
            patient = ""
            bloodPressure = ""
            heartRate = 70
            isBloodSampleCollection = true
        }
        val taskDto2 = TaskDto().apply {
            id = 2
            title = "title"
            dueDateTime = LocalDateTime.MIN
            timeWitchCompleted = LocalDateTime.MIN
            periodicity = PeriodicityDto(2, "Alternate Day")
            status = StatusDto(2, "pending")
            patient = "patient"
            bloodPressure = "bloodPressure"
            heartRate = 70
            isBloodSampleCollection = true
        }
        val taskDto3 = TaskDto().apply {
            id = 3
            title = "ttt"
            dueDateTime = LocalDateTime.MAX
            timeWitchCompleted = LocalDateTime.MIN
            periodicity = PeriodicityDto(3, "Third Day")
            status = StatusDto(3, "missed")
            patient = "ppp"
            bloodPressure = "120/60"
            heartRate = 70
            isBloodSampleCollection = true
        }
        val taskDtoList: List<TaskDto> = ArrayList<TaskDto>(3).apply {
            add(taskDto1)
            add(taskDto2)
            add(taskDto3)
        }
        return taskDtoList
    }


    @Test
    fun repositoryGetOneByIdTest() {
        sl.roomLocalDb.taskDao().insert(testTaskEntity)
        val resultValue: TaskDto = sl.taskRepository.getOneById(1)

        assertEquals(testTaskDto, resultValue)
    }

    @Test
    fun repositoryCreateTest() {
        val idCreatedTask = sl.taskRepository.create(testTaskDto)
        val resultValue: TaskDto = TaskConverter.instance.convert(
            sl.roomLocalDb.taskDao().getOneById(testTaskDto.id!!)
        )
        resultValue.status = StatusConverter.instance.convert(
            sl.roomLocalDb.statusDao().getOneById(testTaskDto.status?.id!!)
        )
        resultValue.periodicity = PeriodicityConverter.instance.convert(
            sl.roomLocalDb.periodicityDao().getOneById(testTaskDto.periodicity?.id!!)
        )
        assertEquals(idCreatedTask, resultValue.id)
        assertEquals(testTaskDto, resultValue)
    }

    @Test
    fun repositoryGetAllTest() {
        val testValue: List<TaskDto> = getTaskDtoList()
        val taskEntity1 = TaskConverter.instance.convert(testValue[0])
        val taskEntity2 = TaskConverter.instance.convert(testValue[1])
        val taskEntity3 = TaskConverter.instance.convert(testValue[2])
        sl.roomLocalDb.taskDao().insert(taskEntity1)
        sl.roomLocalDb.taskDao().insert(taskEntity2)
        sl.roomLocalDb.taskDao().insert(taskEntity3)

        val resultValue = sl.taskRepository.getAll()

        assertEquals(testValue.size, resultValue.size)
        assertTrue(testValue == resultValue)
    }

    @Test
    fun repositoryGetByStatusTest() {
        val taskDtoList: List<TaskDto> = getTaskDtoList()
        val taskEntity1 =
            TaskConverter.instance.convert(taskDtoList[0])
        val taskEntity2 =
            TaskConverter.instance.convert(taskDtoList[1])
        val taskEntity3 =
            TaskConverter.instance.convert(taskDtoList[2])
        val taskEntity4 =
            TaskConverter.instance.convert(taskDtoList[0].apply { status = StatusDto(2) })
        val taskEntity5 =
            TaskConverter.instance.convert(taskDtoList[1].apply { status = StatusDto(3) })
        val taskEntity6 =
            TaskConverter.instance.convert(taskDtoList[2].apply { status = StatusDto(1) })
        sl.roomLocalDb.taskDao().insert(taskEntity1)
        sl.roomLocalDb.taskDao().insert(taskEntity2)
        sl.roomLocalDb.taskDao().insert(taskEntity3)
        sl.roomLocalDb.taskDao().insert(taskEntity4)
        sl.roomLocalDb.taskDao().insert(taskEntity5)
        sl.roomLocalDb.taskDao().insert(taskEntity6)

        val byStatus1 = sl.taskRepository.getByStatus(StatusDto(1))
        val byStatus2 = sl.taskRepository.getByStatus(StatusDto(2))
        val byStatus3 = sl.taskRepository.getByStatus(StatusDto(3))


        for (taskDto: TaskDto in byStatus1) {
            assertEquals(taskDto.status, StatusDto(1, "completed"))
        }
        for (taskDto: TaskDto in byStatus2) {
            assertEquals(taskDto.status, StatusDto(2, "pending"))
        }
        for (task: TaskDto in byStatus3) {
            assertEquals(task.status, StatusDto(3, "missed"))
        }

    }

    @Test
    fun repositoryUpdateTest() {
        val beforeEntity = Task().apply {
            id = 1
            title = "BEFORE"
            dueDateTime = LocalDateTime.MAX.toString()
            timeWitchCompleted = LocalDateTime.MAX.toString()
            periodicityId = 1
            statusId = 1
            patient = "BEFORE"
            bloodPressure = "BEFORE"
            heartRate = 100
            isBloodSampleCollection = 1
        }
        val afterDto = TaskDto().apply {
            id = 1
            title = "AFTER"
            dueDateTime = LocalDateTime.MAX
            timeWitchCompleted = LocalDateTime.MAX
            periodicity = PeriodicityDto(1, "Everyday")
            status = StatusDto(1, "completed")
            patient = "AFTER"
            bloodPressure = "AFTER"
            heartRate = 65
            isBloodSampleCollection = true
        }

        sl.roomLocalDb.taskDao().insert(beforeEntity)

        val countOfUpdated = sl.taskRepository.update(afterDto)

        val resultValue: TaskDto = TaskConverter.instance.convert(
            sl.roomLocalDb.taskDao().getOneById(afterDto.id!!)
        )
        resultValue.status = StatusConverter.instance.convert(
            sl.roomLocalDb.statusDao().getOneById(afterDto.status?.id!!)
        )
        resultValue.periodicity = PeriodicityConverter.instance.convert(
            sl.roomLocalDb.periodicityDao().getOneById(afterDto.periodicity?.id!!)
        )

        assertEquals(countOfUpdated, 1)
        assertEquals(afterDto, resultValue)

    }


    @Test
    fun repositoryDeleteTest() {
        sl.roomLocalDb.taskDao().insert(testTaskEntity)
        val checkFind1: Task = sl.roomLocalDb.taskDao().getOneById(testTaskEntity.id!!)
        assertNotNull(checkFind1)
        val resultValue: TaskDto = TaskConverter.instance.convert(checkFind1)
        assertNotNull(resultValue)


        val countOfDeleted = sl.taskRepository.delete(resultValue)
        val checkFind2: Task = sl.roomLocalDb.taskDao().getOneById(testTaskEntity.id!!)

        assertEquals(countOfDeleted, 1)
        assertNull(checkFind2)
    }

}