package ru.otus.basicarchitecture.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val isPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        super.observe(owner, object : Observer<T> {
            override fun onChanged(value: T) {
                if (isPending.compareAndSet(true, false)) {
                    observer.onChanged(value)
                }
            }
        })
    }

    @MainThread
    fun call() { setValue(null) }

    @MainThread
    override fun setValue(value: T?) {
        isPending.set(true)
        super.setValue(value)
    }
}