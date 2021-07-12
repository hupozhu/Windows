package com.sampson.sourceimitate.rxjava

class ObservableSubscribeOn<T>(val source: Observable<T>, val scheduler: Scheduler): Observable<T>() {


    override fun subscribeActual(sourceObserver: Observer<T>) {
        val parent = SubscribeOnObserver(sourceObserver)
        scheduler.scheduleDirect(SubscribeTask(parent))
    }

    class SubscribeOnObserver<T>(val source: Observer<T>): Observer<T> {

        override fun onComplete() {
        }

        override fun onNext(t: T) {
        }

    }

    internal class SubscribeTask<T>(val sourceObservable: SubscribeOnObserver<T>): Runnable {

        override fun run() {
        }

    }

}