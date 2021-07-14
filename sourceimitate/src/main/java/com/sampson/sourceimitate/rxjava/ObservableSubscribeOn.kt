package com.sampson.sourceimitate.rxjava

/**
 * 2.2 创建observable对象，应用上层流的Observable 和 线程调度对象。
 */
class ObservableSubscribeOn<T>(private val sourceObservable: Observable<T>, private val scheduler: Scheduler): Observable<T>() {

    /**
     * 2.6 继续调上层事件的subscribeActual
     */
    override fun subscribeActual(source: Observer<T>) {
        scheduler.scheduleDirect(Runnable { sourceObservable.subscribe(SubscribeOnObserver(source)) })
    }

    class SubscribeOnObserver<T>(private val observer: Observer<T>): Observer<T> {
        override fun onNext(t: T) {
            observer.onNext(t)
        }

    }
}