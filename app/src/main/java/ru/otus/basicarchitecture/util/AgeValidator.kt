package ru.otus.basicarchitecture.util

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object AgeValidator {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun isAgeValid(date: String): Boolean {
        val birthdate: LocalDate
        try {
            birthdate = LocalDate.parse(date, formatter)
        } catch (e: DateTimeParseException) {
            return false
        }
        return Period.between(birthdate, LocalDate.now()).years > 17
    }
}