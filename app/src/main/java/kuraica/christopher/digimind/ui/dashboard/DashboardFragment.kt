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
import kuraica.christopher.digimind.Reminder
import kuraica.christopher.digimind.databinding.FragmentDashboardBinding
import kuraica.christopher.digimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

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

            if (chkMonday.isChecked) days.add("Monday")
            if (chkTuesday.isChecked) days.add("Tuesday")
            if (chkWednesday.isChecked) days.add("Wednesday")
            if (chkThursday.isChecked) days.add("Thursday")
            if (chkFriday.isChecked) days.add("Friday")
            if (chkSaturday.isChecked) days.add("Saturday")
            if (chkSunday.isChecked) days.add("Sunday")

            val reminder = Reminder(txtTitle.text.toString(), days, txtTime.text.toString())

            HomeFragment.remindersList.add(reminder)

            Toast.makeText(root.context, "Reminder added", Toast.LENGTH_SHORT).show()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}