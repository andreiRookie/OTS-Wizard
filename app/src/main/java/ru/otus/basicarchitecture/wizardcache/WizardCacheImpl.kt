package ru.otus.basicarchitecture.wizardcache

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.dto.WizardUserData

class WizardCacheImpl : WizardCache {

//    private var userData = WizardUserData()

    private var _data = WizardUserData()
    private val data = MutableLiveData(_data)

//    override fun getUserData(): WizardUserData {
//        Log.e("AAA", "CURRENT CACHE: " + _data.toString())
//        return userData
//    }

    override fun getData(): LiveData<WizardUserData> {
        Log.e("AAA", "CURRENT CACHE: " + _data.toString())
        return data
    }

    override fun updateMainData(name: String, surname: String, dateOfBirth: String) {

//        userData = userData.copy(name = name, surname = surname, dateOfBirth = dateOfBirth)


        _data = _data.copy(name = name, surname = surname, dateOfBirth = dateOfBirth)
        data.value = _data
       Log.e("AAA", "CACHE: " + _data.toString())
    }

    override fun updateAddress(country: String, city: String, address: String) {

//        userData = userData.copy(country =country, city = city, address = address)

        _data = _data.copy(country =country, city = city, address = address)
        data.value = _data
        Log.e("AAA", "CACHE: " + _data.toString())
    }

    override fun updateInterests(interests: Set<String>) {


//        userData = userData.copy(interests = interests)

        _data = _data.copy(interests = interests)
        data.value = _data
        Log.e("AAA", "CACHE: " + _data.toString())
    }
}