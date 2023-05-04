package kuraica.christopher.digimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kuraica.christopher.digimind.Reminder
import kuraica.christopher.digimind.databinding.FragmentDashboardBinding
import kuraica.christopher.digimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    val db = Firebase.firestore

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Initialize the UI elements
        val txtTitle: TextView = binding.txtReminder
        val txtTime: EditText = binding.txtTime
        val chkMonday: CheckBox = binding.monday
        val chkTuesday: CheckBox = binding.tuesday
        val chkWednesday: CheckBox = binding.wednesday
        val chkThursday: CheckBox = binding.thursday
        val chkFriday: CheckBox = binding.friday
        val chkSaturday: CheckBox = binding.saturday
        val chkSunday: CheckBox = binding.sunday
        val btnAdd: TextView = binding.btnAdd

        //Set event listeners
        txtTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                txtTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(
                root.context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        btnAdd.setOnClickListener {
            var days = ArrayList<String>()

            var daysBool = ArrayList<Boolean>()

            if (chkMonday.isChecked) {
                days.add("Monday")
                daysBool.add(true)
            }else {
                daysBool.add(false)
            }
            if (chkTuesday.isChecked) {
                days.add("Tuesday")
                daysBool.add(true)
            }else {
                daysBool.add(false)
            }
            if (chkWednesday.isChecked) {
                days.add("Wednesday")
                daysBool.add(true)
            }else {
                daysBool.add(false)
            }
            if (chkThursday.isChecked) {
                days.add("Thursday")
                daysBool.add(true)
            }else {
                daysBool.add(false)
            }
            if (chkFriday.isChecked) {
                days.add("Friday")
                daysBool.add(true)
            }else {
                daysBool.add(false)
            }
            if (chkSaturday.isChecked) {
                days.add("Saturday")
                daysBool.add(true)
            }else {
                daysBool.add(false)
            }
            if (chkSunday.isChecked) {
                days.add("Sunday")
                daysBool.add(true)
            }else {
                daysBool.add(false)
            }

            val reminder = Reminder(txtTitle.text.toString(), days, txtTime.text.toString())

            submitToDB(reminder.title, daysBool[0], daysBool[1], daysBool[2], daysBool[3], daysBool[4], daysBool[5], daysBool[6], reminder.time)

            HomeFragment.remindersList.add(reminder)
        }


        return root
    }

    /**
     * Crea un nuevo registro en la base de datos de firestore, la coleccion se llama actividades y tiene los siguientes campos:
     * Actividad: string, lu, ma, mi, ju, vi, sa, do: boolean, tiempo: string
     */
    fun submitToDB(actividad: String, lu: Boolean, ma: Boolean, mi: Boolean, ju: Boolean, vi: Boolean, sa: Boolean, dom: Boolean, tiempo: String) {
        val newReminder = hashMapOf(
            "actividad" to actividad,
            "lu" to lu,
            "ma" to ma,
            "mi" to mi,
            "ju" to ju,
            "vi" to vi,
            "sa" to sa,
            "do" to dom,
            "tiempo" to tiempo
        )
        db.collection("actividades")
            .add(newReminder)
            .addOnSuccessListener {
                Toast.makeText(this.context, "Reminder added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this.context, "Error, try again", Toast.LENGTH_SHORT).show()
            }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}