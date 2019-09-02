package ke.co.basecode.api

import ke.co.basecode.model.BaseResponseModel
import ke.co.basecode.model.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * Created by William Makau on 18/06/2019.
 * Company: Think Synergy Limited
 * Email: williammakau070@gmail.com
 * Phone: +254706356815
 * Purpose:
 */
interface BaseAPI {

    @Multipart
    @POST("")
    fun signIn(@Part("") id: RequestBody, @Part("") password: RequestBody): Call<BaseResponseModel<User>>

    /*@GET("varieties")
    Call<List<Variety>> getVarieties();

    @FormUrlEncoded
    @POST("approve/variety")
    Call<BaseResponseModel<Variety>> approveVariety(@Field("id") long id);

    @GET
    Call<BaseResponseListVariety<TempVariety>> fetchVarietyDetails(@Url String url, @Query("item_id") long item_id, @Query("api_key") String api_key, @Query("farm") String farm);

    @GET("varieties")
    Call<BaseResponseList<Variety>> getVarieties(@Query("page") int page, @Query("size") int size, @Query("farm") String farm, @Query("query") String query);

    //get all varieties
    @GET("all/varieties")
    Call<BaseResponseList<Variety>> getAllVarieties();

    @Multipart
    @POST("stages")
    Call<BaseResponseModel<Stage>> addAStage(@Part MultipartBody.Part photo, @Part("name") RequestBody name, @Part("stage") RequestBody stage, @Part("description") RequestBody description, @Part("varietyId") RequestBody varietyId);

    @Multipart
    @POST("varieties")
    Call<BaseResponseModel<Variety>> addAVariety(@Part MultipartBody.Part photo, @Part("name") RequestBody name, @Part("varietyName") RequestBody varietyName, @Part("breeder") RequestBody breeder, @Part("greenHouseId") RequestBody greenHouseId, @Part("quantity") RequestBody quantity, @Part("farm") RequestBody farm, @Part("company") RequestBody company, @Part("description") RequestBody description, @Part("status") RequestBody status);


    @GET("productions")
    Call<BaseResponseList<Production>> getProductions();

    @Multipart
    @POST
    Call<ProductionResponse> createProduction(@Url String url, @Part("api_key") RequestBody api_key, @Part("json") RequestBody json);*/

    /*@GET("subscriptions")
    Call<BaseResponseList<Subscription>> getSubscriptions(@Query("page") int page, @Query("size") int size);
*/
    /*@GET("green-houses")
    Call<BaseResponseList<GreenHouse>> getGreenHouses();

    @GET("zones")
    Call<BaseResponseList<Zone>> getZones();

    //trials
    @Multipart
    @POST
    Call<ProductionResponse> createTrial(@Url String url, @Part("api_key") RequestBody api_key, @Part("json") RequestBody json);

    //get trials
    @GET
    Call<BaseResponseListTrial<Trial>> fetchTrialDetails(@Url String url, @Query("breeder") String breeder, @Query("api_key") String api_key, @Query("type") String type);

    @Multipart
    @POST("trials")
    Call<ResponseBody> addTrialPhoto(@Part MultipartBody.Part photo, @Part("name") RequestBody name);

    //get trial
    @GET
    Call<BaseResponseListTrial<Trial>> fetchATrial(@Url String url, @Query("name") String name, @Query("api_key") String api_key);

    //update trial
    @Multipart
    @POST
    Call<ProductionResponse> updateTrial(@Url String url, @Part("api_key") RequestBody api_key, @Part("json") RequestBody json);

    //delete trial
    @Multipart
    @POST
    Call<ProductionResponse> deleteTrial(@Url String url, @Part("json") RequestBody json, @Part("api_key") RequestBody api_key);

    //get forecast
    @GET
    Call<BaseResponseListForecast<Forecast>> fetchForecasts(@Url String url, @Query("api_key") String api_key);

    //notifications
    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseBody> sendMessage(@Url String url, @Body FirebaseMessage message, @Header("Authorization") String authHeader);

    //get orders
    @GET
    Call<BaseResponseListOrder<Order>> fetchOrders(@Url String url, @Query("api_key") String api_key, @Query("status") String status, @Query("farm") String farm);

    //comments
    @Multipart
    @POST
    Call<ProductionResponse> postComment(@Url String url, @Part("api_key") RequestBody api_key, @Part("json") RequestBody json);

    @GET
    Call<BaseResponseListComment<Comment>> fetchVarietyComments(@Url String url, @Query("varietyname") String varietyname, @Query("api_key") String api_key);

    //simulations
    @Multipart
    @POST
    Call<ProductionResponse> createSimulation(@Url String url, @Part("api_key") RequestBody api_key, @Part("json") RequestBody json);

    @Multipart
    @POST("simulations")
    Call<ResponseBody> saveSimulationPhoto(@Part MultipartBody.Part photo, @Part("name") RequestBody name);

    @GET
    Call<BaseResponseListSimulation<Simulation>> fetchVarietySimulations(@Url String url, @Query("api_key") String api_key, @Query("trial_id") String trialId);*/
}