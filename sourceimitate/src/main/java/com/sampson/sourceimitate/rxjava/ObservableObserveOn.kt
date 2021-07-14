package com.sampson.sourceimitate.rxjava

/**
 * 2.4 接收上层的事件流，和调度器
 */
class ObservableObserveOn<T>(private val sourceObservable: Observable<T>, private val scheduler: Scheduler): Observable<T>() {

    /**
     * 2.5 调用Observable的subscribe方法，实际调用到subscribeActual
     */
    override fun subscribeActual(source: Observer<T>) {
        /**
         * 2.5 调用上层事件的subscribeActual 并传入一个新的Observer
         */
        sourceObservable.subscribe(ObserveOnObserver(source, scheduler))
    }

    class ObserveOnObserver<T>(private val sourceObserver: Observer<T>, private val scheduler: Scheduler): Observer<T> {

        override fun onNext(t: T) {
            scheduler.scheduleDirect(Runnable { sourceObserver.onNext(t) })
        }

    }

}