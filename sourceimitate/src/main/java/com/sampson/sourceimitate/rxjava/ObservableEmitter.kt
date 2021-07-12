package com.sampson.sourceimitate.rxjava

/**
 * 事件发送
 */
interface ObservableEmitter<T> {

    fun onNext(e: T)

}