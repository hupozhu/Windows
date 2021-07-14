package com.sampson.sourceimitate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sampson.sourceimitate.rxjava.*
import com.sampson.sourceimitate.rxjava.Observable
import com.sampson.sourceimitate.rxjava.Observer
import io.reactivex.rxjava3.functions.Function
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customRxFunction()
    }

    private fun customRxFunction() {
        Observable
            .create(object : ObservableOnSubscribe<String> {
                override fun subscribe(emitter: ObservableEmitter<String>) {
                    emitter.onNext("xxx")
                }
            })
            .map(Function<String, String> {
                it.toUpperCase(Locale.ROOT)
            })
            .subscribeOn(IoScheduler())
            .subscribeOn(MainHandlerScheduler())
            .subscribe(object : Observer<String> {
                override fun onNext(t: String) {

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
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
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