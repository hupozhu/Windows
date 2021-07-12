package com.sampson.sourceimitate.rxjava

/**
 * 可观察者创建对象，需要具有使可观察者发送事件的功能，所以引用了ObservableOnSubscribe对象。以在需要的时候发出事件。
 */
class ObservableCreate<T>: Observable<T> {

    var subscribe:ObservableOnSubscribe<T>? = null

    constructor(subscribe: ObservableOnSubscribe<T>) {
        this.subscribe = subscribe
    }

    override fun subscribeActual(source: Observer<T>) {

    }


}