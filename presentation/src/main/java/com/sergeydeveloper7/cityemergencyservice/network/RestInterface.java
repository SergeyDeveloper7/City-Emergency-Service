package com.sergeydeveloper7.cityemergencyservice.network;


import com.sergeydeveloper7.cityemergencyservice.model.response.GetBidsResponse;
import com.sergeydeveloper7.cityemergencyservice.model.response.NewBidResponseModel;
import com.sergeydeveloper7.cityemergencyservice.model.response.SignInResponseModel;
import com.sergeydeveloper7.cityemergencyservice.model.response.SignUpResponseModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestInterface {

    /**
     *  Request for auth, registration and pass recovery
     */

    @POST("api/v1/auth/sign-in")
    Observable<Response<SignInResponseModel>> signIn(@Body RequestBody body);

    @POST("api/v1/auth/sign-up")
    Observable<Response<SignUpResponseModel>> signUp(@Body RequestBody body);

    @GET("api/v1/bid")
    Observable<Response<GetBidsResponse>> getBids(@HeaderMap Map<String, String> headers, @Query("status") String status);

    @POST("api/v1/bid")
    Observable<Response<NewBidResponseModel>> newBid(@HeaderMap Map<String, String> headers, @Body RequestBody body);

    @PATCH("api/v1/bid/{id}")
    Observable<Response<NewBidResponseModel>> editBid(@HeaderMap Map<String, String> headers, @Path("id") int bidID, @Body RequestBody body);

    @DELETE("api/v1/bid/{id}")
    Observable<Response<NewBidResponseModel>> deleteBid(@HeaderMap Map<String, String> headers, @Path("id") int bidID);

}
