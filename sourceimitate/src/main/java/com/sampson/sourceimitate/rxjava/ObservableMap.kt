package com.sampson.sourceimitate.rxjava

import io.reactivex.rxjava3.functions.Function

class ObservableMap<T, R>: Observable<R>, HasUpstreamObservableSource<T> {

    var source: ObservableSource<T>? = null
    var function: Function<T, R>? = null


    constructor(source: Observable<T>, mapper: Function<T, R>) {
        this.source = source
        this.function = mapper
    }

    override fun source(): ObservableSource<T>? {
        return source
    }

    override fun subscribeActual(source: Observer<R>) {
        TODO("Not yet implemented")
    }


}