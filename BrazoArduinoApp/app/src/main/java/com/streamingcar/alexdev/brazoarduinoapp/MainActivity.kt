 package com.streamingcar.alexdev.brazoarduinoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

 class MainActivity : AppCompatActivity()
 {
     private var arduinoApi: ArduinoApi? = null
     companion object {
         val TAG = this::class.toString().split(".").last().dropLast(10)
     }
     private var degreesMotorRight: Int = 0
     private var degreesMotorLeft: Int = 0
     private var openClose: Int = 0
     val listItem = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        arduinoApi = ApiUtils.getArduinoAPI()
        initEventViews()
    }
     private fun initEventViews()
     {
         btnPositiveRight.setOnClickListener {
            if(degreesMotorRight in 0..90)
            {
                degreesMotorRight += 5
                sendRequest(2,degreesMotorRight)
            }
            else
            {

            }
        }
         btnNegativeRight.setOnClickListener {
             if(degreesMotorRight in 1..100)
             {
                 degreesMotorRight -= 5
                 sendRequest(2,degreesMotorRight)
             }
             else
             {

             }
         }

         btnPositiveLeft.setOnClickListener {
             if(degreesMotorLeft in 0..90)
             {
                 degreesMotorLeft += 5
                 sendRequest(3,degreesMotorLeft)
             }
             else
             {

             }
         }
         btnNegativeLeft.setOnClickListener {
             if(degreesMotorLeft in 1..100)
             {
                 degreesMotorLeft -= 5
                 sendRequest(3,degreesMotorLeft)
             }
             else
             {

             }
         }
         btnOpen.setOnClickListener {
             if(openClose in 0..90)
             {
                 openClose += 5
                 sendRequest(1,openClose)
             }
             else
             {

             }
         }
         btnClose.setOnClickListener {
             if(openClose in 1..100)
             {
                 openClose -= 5
                 sendRequest(1,openClose)
             }
             else
             {

             }
         }
         btnRotateRight.setOnClickListener {
             sendRequest(4,1)
         }
         btnRotateLeft.setOnClickListener {
             sendRequest(4,0)
         }
         add.setOnClickListener {
             val it = Item(motorPinzaText.text.toString(),motorDerechoText.text.toString(),motorIzquierdoText.text.toString(),motorPasoText.text.toString())
             listItem.add(it)
             val listItems = arrayOfNulls<String>(listItem.size)
// 3
             for (i in 0 until listItem.size) {
                 val recipe = listItem[i]
                 listItems[i] = "Pinza:"+recipe.pinza+" MotorDerecho:"+recipe.motorDerecho+" MotorIzquierdo:"+recipe.motorIzquierdo+" MOtorGIrar:"+recipe.motorGirar
             }
// 4
             val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
             lista.adapter = adapter
             motorPinzaText.setText("")
             motorDerechoText.setText("")
             motorIzquierdoText.setText("")
             motorPasoText.setText("")

         }
         btnDanger.setOnClickListener {

// 3/
             val listItems = arrayOfNulls<String>(listItem.size)
             for (i in 0 until listItem.size) {



                 val recipe = listItem[i]
                 sendRequest(1,recipe.pinza.toInt())
                 Thread.sleep(1_000)
                 sendRequest(3,recipe.motorIzquierdo.toInt())
                 Thread.sleep(1_000)
                 sendRequest(2,recipe.motorDerecho.toInt())
                 Thread.sleep(1_000)
                 sendRequest(4,recipe.motorGirar.toInt())
                 Thread.sleep(1_000)

             }
// 4
             listItem.clear()
             lista.adapter = null
         }
     }

     private fun sendRequest(motor:Int, degrees: Int)
     {
         arduinoApi!!.sendRequest(motor,degrees).enqueue(object : Callback<SendMessageResponse>
         {
             override fun onFailure(call: Call<SendMessageResponse>?, t: Throwable?)
             {
                 Log.d(TAG,t!!.message)
             }

             override fun onResponse(call: Call<SendMessageResponse>?, response: Response<SendMessageResponse>?)
             {
                 try
                 {
                     if(response!!.body()!!.success!!)
                     {
                         Log.d(TAG,"Success")
                     }
                 }
                 catch (ex: Exception)
                 {
                     Log.d(TAG,ex.message)
                 }
             }
         })
     }
     data class Item (var pinza:String, var motorDerecho: String, var motorIzquierdo:String, var motorGirar: String)
}

