package ke.co.basecode.network

import com.squareup.moshi.Json


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class BaseResponseList<T> {
    @Json(name = "status_code")
    var statusCode: Int = -1

    var message: String? = null

    var success: Boolean = false

    var data: List<T>? = emptyList()
}
