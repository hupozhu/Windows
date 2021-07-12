package com.sampson.sourceimitate.rxjava

import io.reactivex.rxjava3.functions.Function

/**
 * 被观察着
 */
abstract class Observable<T>: ObservableSource<T> {

    companion object {
        /**
         * 1、静态方法 创建一个可观察的对象。该对象是一个Observable子类，共享Observable的所有操作符。
         * 2、接收一个ObservableOnSubscribe对象，以在订阅发生时，处理相关逻辑。
         */
        fun <T> create(subscribe: ObservableOnSubscribe<T>): Observable<T> {
            return ObservableCreate(subscribe)
        }
    }

    /**
     * 3、创建一个Observable对象，并传入上层事件的Observable对象，和处理方法。
     */
    fun <R> map(mapper: Function<T, R>): Observable<R> {
        return ObservableMap(this, mapper)
    }

    /**
     * 4、发生订阅，最终事件流中的subscribeActual方法
     */
    final override fun subscribe(source: Observer<T>) {
        subscribeActual(source)
    }

    /**
     * 5、ObservableMap对象产生的订阅，该对象继承Observable实现了这个抽象方法，看ObservableMap的subscribeActual方法。
     */
    abstract fun subscribeActual(source: Observer<T>)

}