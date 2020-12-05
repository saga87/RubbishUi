package com.lp.rubbishui.net.service;


import com.lp.rubbishui.net.subject.NewObjResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RubbishService {

    @POST("/boxApp/queryUserInfoByCard")
    Call<NewObjResponse> queryUserInfoByCard(@Query("card") String userId);


}
