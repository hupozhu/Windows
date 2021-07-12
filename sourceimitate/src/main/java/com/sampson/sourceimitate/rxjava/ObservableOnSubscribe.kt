package com.sampson.sourceimitate.rxjava

/**
 * 被观察者订阅功能。用以提供发送事件的功能。
 */
interface ObservableOnSubscribe<T> {

    /**
     * 订阅被观察者
     */
    fun subscribe(emitter: ObservableEmitter<T>)

}