package com.sampson.sourceimitate

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sampson.sourceimitate.rxjava.*
import com.sampson.sourceimitate.rxjava.Observable
import com.sampson.sourceimitate.rxjava.Observer
import io.reactivex.rxjava3.functions.Function

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sampson.sourceimitate.test", appContext.packageName)
    }

    @Test
    fun testRxjavaDemo() {
        Observable
            .create(object : ObservableOnSubscribe<String> {
                override fun subscribe(emitter: ObservableEmitter<String>) {
                    println("1:=============" + Thread.currentThread().name)
                    emitter.onNext("xxx")
                }
            })
            .map(Function<String, String> {
                println("2:=============" + Thread.currentThread().name)
                it.toUpperCase(Locale.ROOT)
            })
            .subscribeOn(IoScheduler())
            .observerOn(MainHandlerScheduler())
            .subscribe(object : Observer<String> {
                override fun onNext(t: String) {
                    println("3:=============" + Thread.currentThread().name)
                    assertEquals(t, "XXX")
                }
            })
    }
}