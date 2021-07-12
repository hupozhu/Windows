package com.sampson.sourceimitate.rxjava

abstract class BaseObserver<T, R>(actual: Observer<R>) : Observer<T>, QueueDisposable<R> {

    var downStream: Observer<R>? = actual

    override fun onSubscribe() {
    }

    override fun onComplete() {
    }

}