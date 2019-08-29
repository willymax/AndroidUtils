package ke.co.basecode.network

import ke.co.basecode.listeners.Retryable


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class RequestFailure(private val retryable: Retryable, private val message: String) {
    fun retry() {
        this.retryable.retry()
    }
}