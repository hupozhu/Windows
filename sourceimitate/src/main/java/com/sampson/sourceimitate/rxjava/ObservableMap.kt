package com.sampson.sourceimitate.rxjava

import io.reactivex.rxjava3.functions.Function

class ObservableMap<T, R>(val source: Observable<T>, val mapper: Function<T, R>) : Observable<R>(), HasUpstreamObservableSource<T> {


    override fun source(): ObservableSource<T>? {
        return source
    }

    override fun subscribeActual(observerSource: Observer<R>) {
        /**
         * 6、创建MapObserver对象，并传入订阅的Observer。
         * 7、交给上层的subscribeActual方法，即ObservableCreate对象。
         */
        source.subscribeActual(MapObserver(observerSource, mapper))
    }

    companion object {
        class MapObserver<T, R>(private val actual: Observer<R>, private val mapper: Function<T, R>) : BaseObserver<T, R>(actual) {

            /**
             * 12、执行mapper方法。调用最终观察者的onNext方法。
             */
            override fun onNext(t: T) {
                val result = mapper.apply(t)

                actual.onNext(result)
            }
        }
    }

}