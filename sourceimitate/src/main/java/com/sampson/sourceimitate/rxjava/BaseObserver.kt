package com.sampson.sourceimitate.rxjava

abstract class BaseObserver<T, R>() : Observer<T>, QueueDisposable<R> {


}