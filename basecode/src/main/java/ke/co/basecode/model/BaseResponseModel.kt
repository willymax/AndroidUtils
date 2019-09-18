package ke.co.basecode.model

import com.squareup.moshi.Json

/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
class BaseResponseModel<T> {
    @Json(name = "Message")
    var message: String? = null


    @Json(name = "data")
    var data: T? = null


    @Json(name = "meta")
    var meta: Meta? = null
}

class Meta {
    val message: String? = null
    val code: String? = null
}