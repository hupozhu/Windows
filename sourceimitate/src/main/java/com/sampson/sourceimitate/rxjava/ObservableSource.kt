package com.sampson.sourceimitate.rxjava

interface ObservableSource<T> {

    fun subscribe(source: Observable<T>)

}