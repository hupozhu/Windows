package com.sampson.sourceimitate.rxjava

import java.util.function.Function

/**
 * 被观察着
 */
abstract class Observable<T>: ObservableSource<T> {

    companion object {
        /**
         * 静态方法返回一个可观察的对象
         */
        fun <T> create(subscribe: ObservableOnSubscribe<T>): Observable<T> {
            return ObservableCreate(subscribe)
        }
    }

    fun <R> map(mapper: Function<T, R>): Observable<R> {
        return ObservableMap(this, mapper)
    }


}