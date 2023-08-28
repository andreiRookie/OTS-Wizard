package ru.otus.basicarchitecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.otus.basicarchitecture.di.ActivityComponent
import ru.otus.basicarchitecture.di.DaggerActivityComponent
import ru.otus.basicarchitecture.di.appComponent

class MainActivity : AppCompatActivity() {

    private lateinit var _activityComponent: ActivityComponent
    val activityComponent: ActivityComponent get() = _activityComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        _activityComponent = DaggerActivityComponent.factory().create(appComponent, this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}