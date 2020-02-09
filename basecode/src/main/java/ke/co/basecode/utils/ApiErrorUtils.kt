package ke.co.basecode.utils

import ke.co.basecode.model.APIError

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException


class ApiErrorUtils {
//    fun parseError(response: Response<*>): APIError<*>? {
//        val converter: Converter<ResponseBody, APIError<*>> = ServiceGenerator.retrofit()
//            .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))
//        val error: APIError<*>?
//        error = try {
//            response.errorBody()?.let {
//                converter.convert(it)
//            }
//        } catch (e: IOException) {
//            return APIError<Any?>()
//        }
//        return error
//    }
}