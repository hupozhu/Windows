package com.sampson.sourceimitate.rxjava

/**
 * 2.2 创建observable对象，应用上层流的Observable 和 线程调度对象。
 */
class ObservableSubscribeOn<T>(private val source: Observable<T>, private val scheduler: Scheduler): Observable<T>() {

    /**
     * 2.6 继续调上层事件的subscribeActual
     */
    override fun subscribeActual(source: Observer<T>) {
        this.source.subscribe(SubscribeOnObserver(source, scheduler))
    }

    class SubscribeOnObserver<T>(private val source: Observer<T>, private val scheduler: Scheduler): Observer<T> {
        override fun onNext(t: T) {
            scheduler.scheduleDirect(Runnable { this.source.onNext(t) })
        }
    }

}