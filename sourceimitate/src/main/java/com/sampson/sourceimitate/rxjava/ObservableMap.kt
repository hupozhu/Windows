package com.sampson.sourceimitate.rxjava

import io.reactivex.rxjava3.core.ObservableSource
import java.util.function.Function

class ObservableMap<T, R>: Observable<R>, HasUpstreamObservableSource<T> {

    constructor(source: Observable<T>, mapper: Function<T, R>) {

    }

    override fun subscribe(source: Observable<R>) {
        TODO("Not yet implemented")
    }

    override fun source(): ObservableSource<T> {
        TODO("Not yet implemented")
    }


}