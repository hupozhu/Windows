package com.sampson.sourceimitate.rxjava

class ObservableObserveOn<T>: Observable<T>() {

    override fun subscribeActual(source: Observer<T>) {
    }

    class ObserveOnObserver<T>(): Observer<T> {

        override fun onNext(t: T) {

        }

        override fun onComplete() {
            TODO("Not yet implemented")
        }

    }
}