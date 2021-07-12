package com.sampson.sourceimitate.rxjava

import io.reactivex.rxjava3.core.ObservableSource

interface HasUpstreamObservableSource<T> {

    fun source(): ObservableSource<T>
}