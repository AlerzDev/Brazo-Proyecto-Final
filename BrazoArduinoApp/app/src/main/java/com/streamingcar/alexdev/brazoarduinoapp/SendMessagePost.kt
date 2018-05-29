package com.streamingcar.alexdev.brazoarduinoapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SendMessagePost{
    @SerializedName("content")
    @Expose
    private var content: String? = null
    @SerializedName("type")
    private var type: String? = null
}