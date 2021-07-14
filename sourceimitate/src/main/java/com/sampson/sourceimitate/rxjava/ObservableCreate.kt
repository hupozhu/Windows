package com.sampson.sourceimitate.rxjava

/**
 * 可观察者创建对象，该对象持有对ObservableOnSubscribe的引用（该接口提供一个订阅方法，需要传入ObservableEmitter对象。）
 */
class ObservableCreate<T>(private val subscribe: ObservableOnSubscribe<T>) : Observable<T>() {

    /**
     * 8、被下层的map调用。
     */
    override fun subscribeActual(source: Observer<T>) {
        /**
         * 9、创建CreateEmitter对象
         * 10、将CreateEmitter叫由创建时ObservableOnSubscribe发起订阅。
         */
        subscribe.subscribe(CreateEmitter(source))
    }

    companion object {
        class CreateEmitter<T>(private val observer: Observer<T>) : ObservableEmitter<T> {

            /**
             * 11、调用MapObserver的onNext
             */
            override fun onNext(e: T) { // e ->
                observer.onNext(e)
            }

        }

    }

}