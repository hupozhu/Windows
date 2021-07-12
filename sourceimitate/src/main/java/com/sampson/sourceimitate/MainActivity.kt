package com.sampson.sourceimitate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customRxFunction()
    }

    private fun customRxFunction() {
//        Observable.create(object : ObservableOnSubscribe<String> {
//            override fun subscribe(emitter: ObservableEmitter<String>) {
//                emitter.onNext("1")
//            }
//        }).map(Function<String, String> {
//            it.toUpperCase(Locale.ROOT)
//        }).subscribe(object : Observer<String> {
//            override fun onNext(t: String) {
//            }
//
//            override fun onSubscribe() {
//            }
//
//            override fun onComplete() {
//            }
//
//        })
    }

    private fun basicFunction() {
        Observable
            .create(ObservableOnSubscribe<String> {
                it.onNext("1")
            })
            .map {
                it.toUpperCase(Locale.ROOT)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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


    private fun startUp() {
//        StartupManager.Builder()
//            .addStartup(SampleFirstStartup())
//            .addStartup(SampleSecondStartup())
//            .addStartup(SampleThirdStartup())
//            .addStartup(SampleFourthStartup())
//            .build(this)
//            .start()
//            .await()
    }
}