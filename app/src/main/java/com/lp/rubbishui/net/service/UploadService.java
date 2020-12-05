package com.lp.rubbishui.net.service;


import com.lp.rubbishui.net.subject.NewObjResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {

    @Multipart
    @POST("/app/upload")
    Call<NewObjResponse> uploadPic(@Part MultipartBody.Part file);
}
