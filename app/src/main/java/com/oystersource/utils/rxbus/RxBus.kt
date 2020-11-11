package com.oystersource.utils.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Zhanglibin on 2017/4/18.
 */
class RxBus private constructor() {
    private val bus: Subject<Any> = PublishSubject.create<Any>().toSerialized()

    /**
     * 发送一个新的事件
     */
    fun post(o: Any) {
        bus.onNext(o)
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    fun <T> toObservable(eventType: Class<T>?): Observable<T> {
        return bus.ofType(eventType)
    }

    companion object {
        @Volatile
        private var defaultInstance: RxBus? = null

        /**
         * 单例RxBus
         */
        @JvmStatic
        val default: RxBus?
            get() {
                if (defaultInstance == null) {
                    synchronized(RxBus::class.java) {
                        if (defaultInstance == null) {
                            defaultInstance = RxBus()
                        }
                    }
                }
                return defaultInstance
            }
    }

}