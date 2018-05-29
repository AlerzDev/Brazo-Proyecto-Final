package com.streamingcar.alexdev.brazoarduinoapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArduinoApi{

    @GET("brazo/{motor}/{degrees}")
    fun sendRequest(@Path("motor") motor: Int,@Path("degrees") degrees: Int): Call<SendMessageResponse>

}