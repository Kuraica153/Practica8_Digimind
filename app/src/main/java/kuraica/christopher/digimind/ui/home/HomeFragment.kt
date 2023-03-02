package kuraica.christopher.digimind.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kuraica.christopher.digimind.R
import kuraica.christopher.digimind.Reminder
import kuraica.christopher.digimind.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        var remindersList = ArrayList<Reminder>()
        var firstTime = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(firstTime){
            fillReminders()
            firstTime = false
        }

        val reminderAdapter = ReminderAdapter(root.context, remindersList)
        binding.reminders.adapter = reminderAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun fillReminders(){
        remindersList.add(Reminder("Wake up", arrayListOf("Monday"),"7:00 AM"))
        remindersList.add(Reminder("Eat breakfast", arrayListOf("Monday"),"8:00 AM"))
        remindersList.add(Reminder("Eat lunch", arrayListOf("Monday"),"12:00 PM"))
        remindersList.add(Reminder("Eat dinner", arrayListOf("Monday"),"6:00 PM"))
        remindersList.add(Reminder("Go to bed", arrayListOf("Monday"),"10:00 PM"))
        remindersList.add(Reminder("Wake up", arrayListOf("Tuesday"),"7:00 AM"))
    }

    class ReminderAdapter: BaseAdapter {

        var reminders = ArrayList<Reminder>()
        var context: Context? = null

        constructor(context: Context, reminders: ArrayList<Reminder>) : super() {
            this.reminders = reminders
            this.context = context
        }

        override fun getCount(): Int {
            return reminders.size
        }

        override fun getItem(position: Int): Any {
            return reminders[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val reminder = reminders[position]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var reminderView = inflator.inflate(R.layout.reminder_cell, null)
            var txtReminderName = reminderView.findViewById<TextView>(R.id.reminder_cell_title)
            var txtReminderDays = reminderView.findViewById<TextView>(R.id.reminder_cell_frequency)
            var txtReminderTime = reminderView.findViewById<TextView>(R.id.reminder_cell_time)
            txtReminderName.text = reminder.title
            txtReminderDays.text = reminder.days.toString()
            txtReminderTime.text = reminder.time
            return reminderView
        }

    }


}