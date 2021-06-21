package com.example.loans.data.remote;

import com.example.loans.data.remote.model.BaseResponse;
import com.example.loans.data.remote.model.Data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @POST("get_loans.php")
    @FormUrlEncoded
    Call<BaseResponse<Data>> getLoans(@Field("member_id") String memberId);
}