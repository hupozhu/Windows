import com.sampson.sourceimitate.startup.task.StartupTask
import com.sampson.sourceimitate.startup.task.TaskFutureObserver
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock

abstract class AndroidStartup : StartupTask {

    private val mDependencyCountDown by lazy { CountDownLatch(dependencies()?.size ?: 0) }

    private var mResult: Any? = null
    private var mFutureObserver = mutableListOf<TaskFutureObserver?>()
    private val mLock = ReentrantLock()
    private var mTaskCompleted = AtomicBoolean(true)

    override fun waitDependency() {
        try {
            mDependencyCountDown.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun dependencyComplete(dependency: StartupTask, result: Any?) {
        mDependencyCountDown.countDown()
    }

    override fun create(): Any? {
        mTaskCompleted.set(false)
        return createTask()
    }

    override fun onComplete(result: Any?) {
        // 当前任务执行完成
        if (mTaskCompleted.compareAndSet(true, false)) {
            mLock.unlock()
        }
        mResult = result
        mFutureObserver.forEach {
            it?.observe(mResult)
        }
    }

    // 业务中某些业务场景需要等待这个任务执行完成
    override fun waitCurrentTask(futureObserver: TaskFutureObserver?) {
        // 如果任务已经完成，则直接返回结果。如果任务未完成，等任务
        if (mTaskCompleted.compareAndSet(false, true)) {
            futureObserver?.observe(mResult)
        } else {
            mLock.lock()
            mFutureObserver.add(futureObserver)
        }
    }

    abstract fun createTask(): Any?
}