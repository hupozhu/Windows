package com.sampson.sourceimitate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        basicFunction()
    }

    private fun basicFunction() {
        Observable
            .create(ObservableOnSubscribe<String> {
                it.onNext("1")
            })
            .map {
                it.uppercase()
            }
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: String?) {
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }
}