package com.baddevelopergames.trackmybaby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eat.setOnClickListener { registerEvent(EventType.EAT) }
        sleep.setOnClickListener { registerEvent(EventType.SLEEP) }
        nosleep.setOnClickListener { registerEvent(EventType.NOSLEEP) }
        carriage.setOnClickListener { registerEvent(EventType.CARRIAGE) }
        nocarriage.setOnClickListener { registerEvent(EventType.NOCARRIAGE) }

        eat.setOnLongClickListener { registerEventWithCustomTimestamp(EventType.EAT); true }
        sleep.setOnLongClickListener { registerEventWithCustomTimestamp(EventType.SLEEP); true }
        nosleep.setOnLongClickListener { registerEventWithCustomTimestamp(EventType.NOSLEEP); true }
        carriage.setOnLongClickListener { registerEventWithCustomTimestamp(EventType.CARRIAGE); true }
        nocarriage.setOnLongClickListener { registerEventWithCustomTimestamp(EventType.NOCARRIAGE); true }

        fetch_last_10.setOnClickListener { fetchLast10() }
    }

    private fun fetchLast10() {
        last_10_events.text = ""
        val sb: StringBuilder = StringBuilder()
        getDao().getEvents().forEach {
            sb.append("${it.name} happend at ${it.timestamp}\n")
        }
        last_10_events.text = sb.toString()
    }

    private fun registerEvent(type: EventType) {
        getDao().addEvent(createEvent(type))
    }

    private fun getDao() = BabyDatabase.getInstance(this).eventsDao()

    private fun createEvent(type: EventType, timestamp: Long = Calendar.getInstance().timeInMillis): Event = Event(
            name = type.name,
            timestamp = timestamp
    )

    private fun registerEventWithCustomTimestamp(type: EventType) {
        TimePickerFragment.show(this) { hourOfDay, minute ->
            getDao().addEvent(createEvent(type,
                    Calendar.getInstance().apply {
                        this.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        this.set(Calendar.MINUTE, minute)
                    }.timeInMillis
            ))
        }
    }
}