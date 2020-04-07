package com.example

import kotlinx.coroutines.CoroutineDispatcher
import platform.darwin.dispatch_queue_t
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async


internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}


internal actual val ApplicationDispatcher: CoroutineContext = NsQueueDispatcher(dispatch_get_main_queue())