package com.sampson.sourceimitate.rxjava

class ObservableSubscribeOn<T>(val source: Observable<T>, val scheduler: IoScheduler): Observable<T>() {

    override fun subscribeActual(sourceObserver: Observer<T>) {
        scheduler.scheduleDirect(SubscribeTask(sourceObserver))
    }


    inner class SubscribeTask(private val sourceObservable: Observer<T>): Runnable {

        override fun run() {
            source.subscribe(this.sourceObservable)
        }

    }

}