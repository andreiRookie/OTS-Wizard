package ru.otus.basicarchitecture.wizardcache

import javax.inject.Inject

class WizardCacheImpl @Inject constructor() : WizardCache {

    private val interests = listOf("Hiking", "Programming",
        "Travel", "Walking", "Cycling", "Theatre",
        "Playing double bass", "Movies", "Reading books",
        "Running", "Photography", "Bouldering", "Nightclubs",
        "Bikepacking trips", "Football", "Boxing", "Self education")

    private var data = WizardUserData()

    override fun getUserData(): WizardUserData {
        return data
    }

    override fun getInterestsList(): List<String> = interests

    override fun updateMainData(name: String, surname: String, dateOfBirth: String) {
        data = data.copy(name = name, surname = surname, dateOfBirth = dateOfBirth)
    }

    override fun updateAddress(address: String) {
        data = data.copy(address = address)
    }

    override fun updateInterests(interests: List<String>) {
        data = data.copy(checkedInterests = interests)
    }

    override fun clearCache() {
        data = WizardUserData()
    }
}