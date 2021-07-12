package com.sampson.sourceimitate.rxjava

interface Observer<T> {

    fun onNext(t: T)

    fun onComplete()
}