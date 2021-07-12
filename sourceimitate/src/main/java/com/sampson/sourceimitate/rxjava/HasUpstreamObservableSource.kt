package com.sampson.sourceimitate.rxjava

interface HasUpstreamObservableSource<T> {

    fun source(): ObservableSource<T>?
}