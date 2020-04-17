package ke.co.basecode.api

import ke.co.basecode.authentication.login.User
import ke.co.basecode.model.BaseResponseModel
import ke.co.basecode.network.NetworkUtils
import ke.co.basecode.rest.Client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Willy on 08/04/2020
 * Email williammakau070@gmail.com
 * @author willi
 */

fun signIn(
    signInId: String,
    password: String,
    service: BaseApiService,
    onSuccess: (repos: User) -> Unit,
    onError: (error: String) -> Unit
) {
    service.signIn(signInId, password).enqueue(object : Callback<BaseResponseModel<User>?> {
        override fun onFailure(call: Call<BaseResponseModel<User>?>, t: Throwable) {
            onError(t.message ?: "Unknown error")
        }

        override fun onResponse(
            call: Call<BaseResponseModel<User>?>,
            response: Response<BaseResponseModel<User>?>
        ) {
            if (response.isSuccessful) {
                onSuccess(response.body()?.data!!)
            } else {
                onError(response.errorBody()?.string() ?: "Unknown error")
            }
        }
    })
}

fun changePassword(
    currentPassword: String,
    newPassword: String,
    service: BaseApiService,
    onSuccess: (repos: User) -> Unit,
    onError: (error: String) -> Unit
) {
    service.changePassword(currentPassword, newPassword, newPassword)
            .enqueue(object : Callback<BaseResponseModel<User>?> {
                override fun onFailure(call: Call<BaseResponseModel<User>?>, t: Throwable) {
                    onError(t.message ?: "Unknown error")
                }

                override fun onResponse(
                    call: Call<BaseResponseModel<User>?>,
                    response: Response<BaseResponseModel<User>?>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()?.data!!)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            })
}

interface BaseApiService {
    @FormUrlEncoded
    @POST("auth/signIn")
    fun signIn(
            @Field("signInId") signInId: String,
            @Field("password") password: String
    ): Call<BaseResponseModel<User>>

    @FormUrlEncoded
    @POST("users/changePassword")
    fun changePassword(
            @Field("currentPassword") currentPassword: String,
            @Field("newPassword") newPassword: String,
            @Field("confirmPassword") confirmPassword: String
    ): Call<BaseResponseModel<User>>

    companion object {
        //writes to this field are made visible to other threads
        @Volatile
        private var instance: BaseApiService? = null

        fun getInstance(): BaseApiService {
            return instance ?: synchronized(this) {
                instance
                    ?: buildInstance()
                        .also { instance = it }
            }
        }

        private fun buildInstance(): BaseApiService {
            return Retrofit.Builder()
                    .baseUrl(Client.instance.config.baseUrl)
                    .client(NetworkUtils.getClientInstance())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BaseApiService::class.java)
        }
    }
}