package com.sampson.sourceimitate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sampson.sourceimitate.rxjava.Observable
import com.sampson.sourceimitate.rxjava.ObservableEmitter
import com.sampson.sourceimitate.rxjava.ObservableOnSubscribe
import com.sampson.sourceimitate.rxjava.Observer
import io.reactivex.rxjava3.functions.Function

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        basicFunction()
    }

    private fun customRxFunction() {
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                emitter.onNext("1")
            }
        }).map(Function<String, String> {
            it.toUpperCase()
        }).subscribe(object : Observer<String> {
            override fun onNext(t: String) {
            }

            override fun onSubscribe() {
            }

            override fun onComplete() {
            }

        })
    }

    private fun basicFunction() {
//        Observable
//            .create(ObservableOnSubscribe<String> {
//                it.onNext("1")
//            })
//            .map {
//                it.toUpperCase(Locale.ROOT)
//            }
//            .subscribe(object : Observer<String> {
//                override fun onSubscribe(d: Disposable?) {
//                }
//
//                override fun onNext(t: String?) {
//                }
//
//                override fun onError(e: Throwable?) {
//                }
//
//                override fun onComplete() {
//                }
//            })
    }
}