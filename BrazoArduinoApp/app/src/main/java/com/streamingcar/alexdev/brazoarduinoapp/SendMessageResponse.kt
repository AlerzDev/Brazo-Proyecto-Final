package com.streamingcar.alexdev.brazoarduinoapp

import com.google.gson.annotations.SerializedName

data class SendMessageResponse(
        @SerializedName("success") val success: Boolean?
)