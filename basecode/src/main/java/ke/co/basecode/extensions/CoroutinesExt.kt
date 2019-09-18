package ke.co.basecode.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun executeAsync(action: () -> Unit) {

    GlobalScope.launch(Dispatchers.IO) {
        action.invoke()
    }
}

fun <Result> executeAsync(action: () -> Result, onCompletion: (result: Result) -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {

        val job = async(Dispatchers.IO) { action.invoke() }

        val result = job.await()

        onCompletion.invoke(result)

    }
}