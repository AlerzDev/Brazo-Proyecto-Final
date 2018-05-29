package com.streamingcar.alexdev.brazoarduinoapp

class ApiUtils {
    companion object
    {
        fun getArduinoAPI(): ArduinoApi {
            return RetrofitClient.getClient(Constants.URL_BASE_API)!!.create(ArduinoApi::class.java)
        }
    }
}