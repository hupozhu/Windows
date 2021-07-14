package com.sampson.sourceimitate.rxjava

/**
 * 天生丽质
 */
interface ObservableSource<T> {

    /**
     * 可成为万众瞩目的焦点
     */
    fun subscribe(source: Observer<T>)

}