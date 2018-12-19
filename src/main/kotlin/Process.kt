package org.whitings.bdayics

import biweekly.ICalendar
import biweekly.component.VEvent
import biweekly.property.DateStart
import biweekly.property.Description
import biweekly.property.RecurrenceRule
import biweekly.property.Summary
import biweekly.util.Frequency
import biweekly.util.Recurrence
import java.io.FileReader
import java.io.FileWriter
import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.bean.CsvBindByName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BdayList {
    @CsvBindByName(column = "Preferred First Name")
    val first: String? = null
    @CsvBindByName(column = "Last Name")
    val last: String? = null
    @CsvBindByName(column = "Birthday")
    val bday: String? = null
    @CsvBindByName(column = "Work Email Address")
    val email: String? = null
    @CsvBindByName(column = "Business Title")
    val title: String? = null
    @CsvBindByName(column = "Direct Manager Name")
    val manager: String? = null
}

fun main(args: Array<String>) {
    try {
        if (args.size != 2) throw Exception("include csv with birthday list as first argument and output file as second")
        val inputFilename = args[0]
        val outputFilename = args[1]
        val row = CsvToBeanBuilder<BdayList>(FileReader(inputFilename)).withType(BdayList::class.java).build().parse()
        val ical = ICalendar()
        row.forEach { person ->
            ical.addEvent(VEvent().apply {
                summary = Summary(person.first + " " + person.last?.take(1) + "'s birthday")
                description = Description( "${person.email} ${person.title} manager: ${person.manager}")
                var l = LocalDate.parse("${person.bday}/2018",DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                dateStart = DateStart(java.sql.Date.valueOf(l),false)
                recurrenceRule = RecurrenceRule(Recurrence.Builder(Frequency.YEARLY).build())
            }
            )
        }
        ical.write(FileWriter(outputFilename))
    } catch (e: Exception) {
        println("Terminating with errors\n $e")
    }
}

