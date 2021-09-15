package com.sampson.sourceimitate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rousetime.android_startup.StartupManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
//import com.sampson.sourceimitate.rxjava.*
//import com.sampson.sourceimitate.rxjava.Observable
//import com.sampson.sourceimitate.rxjava.Observer
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import okhttp3.EventListener
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        useOkHttp()
    }

    private fun useOkHttp() {
        val okHttpClient = OkHttpClient.Builder()
            .eventListener(object : EventListener(){

            })
            .build()
        val request = Request.Builder().url("www.baidu.com").get().build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("OKHttp", e.printStackTrace().toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("OKHttp", response.body.toString())
            }
        })
    }

    private fun customRxFunction() {
//        Observable
//            .create(object : ObservableOnSubscribe<String> {
//                override fun subscribe(emitter: ObservableEmitter<String>) {
//                    emitter.onNext("xxx")
//                }
//            })
//            .map(Function<String, String> {
//                it.toUpperCase(Locale.ROOT)
//            })
//            .subscribeOn(IoScheduler())
//            .observerOn(MainHandlerScheduler())
//            .subscribe(object : Observer<String> {
//                override fun onNext(t: String) {
//
//                }
//            })
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
        StartupManager.Builder()
//            .addStartup(SampleFirstStartup())
//            .addStartup(SampleSecondStartup())
//            .addStartup(SampleThirdStartup())
//            .addStartup(SampleFourthStartup())
            .build(this)
            .start()
            .await()
    }


}