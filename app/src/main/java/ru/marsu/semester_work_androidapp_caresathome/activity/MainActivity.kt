package ru.marsu.semester_work_androidapp_caresathome.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import ru.marsu.semester_work_androidapp_caresathome.R
import ru.marsu.semester_work_androidapp_caresathome.databinding.ActivityMainBinding
import ru.marsu.semester_work_androidapp_caresathome.fragment.FragmentsOfMainActivity
import ru.marsu.semester_work_androidapp_caresathome.fragment.HomeFragment
import ru.marsu.semester_work_androidapp_caresathome.fragment.InventoryFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var selectedFrame: FragmentsOfMainActivity = FragmentsOfMainActivity.HOME

    lateinit var homeBackground: ConstraintLayout
    lateinit var inventoryBackground: ConstraintLayout
    lateinit var homeImage: ImageView
    lateinit var inventoryImage: ImageView
    lateinit var homeText: TextView
    lateinit var inventoryText: TextView
    lateinit var homeRightTopElem: MaterialCardView
    lateinit var inventoryRightTopElem: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        openFragment(HomeFragment.newInstance())

        homeBackground = binding.clHome
        inventoryBackground = binding.clInventory

        homeImage = binding.imHome
        inventoryImage = binding.imInventory

        homeText = binding.tHome
        inventoryText = binding.tInventory

        homeRightTopElem = binding.cvAvatar
        inventoryRightTopElem= binding.cvCart

        // TODO: тестовая часть. Удалить
        binding.imAvatar.setImageResource(R.drawable.im_avatar)

    }

    fun onClickGoMyTasks(view: View) {
        val intent = Intent(this, MyTasksActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoMyPatients(view: View) {
        val intent = Intent(this, MyPatientsActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoPatientProfile(view: View) {
        val intent = Intent(this, PatientProfileActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoMyProfile(view: View) {
        val intent = Intent(this, MyProfileActivity::class.java)
        startActivity(intent)
    }

    fun onClickOpenDrawer(view: View) {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    fun onClickOpenHomeFrame(view: View) {
        if (selectedFrame != FragmentsOfMainActivity.HOME) {
            unselectInventoryTab()
            selectHomeTab()
            selectedFrame = FragmentsOfMainActivity.HOME
        }
    }

    fun onClickOpenInventoryFrame(view: View) {
        if (selectedFrame != FragmentsOfMainActivity.INVENTORY) {
            unselectHomeTab()
            selectInventoryTab()
            selectedFrame = FragmentsOfMainActivity.INVENTORY
        }
    }

    fun onClickGoMyCart(view: View) {
        val intent = Intent(this, MyCartActivity::class.java)
        startActivity(intent)
    }

    fun selectInventoryTab() {
        inventoryBackground.setBackgroundResource(R.drawable.background_bottom_bar)
        inventoryImage.setImageResource(R.drawable.ic_inventory_selected)
        inventoryText.setVisibility(View.VISIBLE)
        inventoryRightTopElem.setVisibility(View.VISIBLE)
        inventoryBackground.startAnimation(createAnimationForTabs())

        openFragment(InventoryFragment.newInstance())
    }

    fun selectHomeTab() {
        homeBackground.setBackgroundResource(R.drawable.background_bottom_bar)
        homeImage.setImageResource(R.drawable.ic_home_selected)
        homeText.setVisibility(View.VISIBLE)
        homeRightTopElem.setVisibility(View.VISIBLE)
        homeBackground.startAnimation(createAnimationForTabs())

        openFragment(HomeFragment.newInstance())
    }

    fun unselectInventoryTab() {
        inventoryBackground.setBackgroundColor(getColor(R.color.transparent))
        inventoryImage.setImageResource(R.drawable.ic_inventory)
        inventoryText.setVisibility(View.GONE)
        inventoryRightTopElem.setVisibility(View.GONE)
    }

    fun unselectHomeTab() {
        homeBackground.setBackgroundColor(getColor(R.color.transparent))
        homeImage.setImageResource(R.drawable.ic_home)
        homeText.setVisibility(View.GONE)
        homeRightTopElem.setVisibility(View.GONE)
    }

    fun createAnimationForTabs(): Animation {
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

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcvMainContent, fragment)
            .commit()
    }

}
