package com.kushagrawithmdg.gotrade.ui.Analytics

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kushagrawithmdg.gotrade.Services.CoinService
import com.kushagrawithmdg.gotrade.Models.TimeRate
import com.kushagrawithmdg.gotrade.Services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnalyticsViewModel(application: Application) : AndroidViewModel(application) {

    private var _text: MutableLiveData<ArrayList<TimeRate>> ?=null

    fun get(cryptocode:String,currencycode: String, endtime: String):MutableLiveData<ArrayList<TimeRate>>{
        if (_text== null) {
            _text = MutableLiveData<ArrayList<TimeRate>>()
            load(cryptocode,currencycode,endtime)
        }
        return _text as MutableLiveData<ArrayList<TimeRate>>
    }


    fun load(cryptocode:String,currencycode: String, endtime: String) {





        val coinService: CoinService = ServiceBuilder.buildService(CoinService::class.java)

        val requestCall: Call<ArrayList<TimeRate>> = coinService.gettimerate(cryptocode,currencycode,endtime)

        requestCall.enqueue(object : Callback<ArrayList<TimeRate>> {


            override fun onResponse(
                call: Call<ArrayList<TimeRate>>,
                response: Response<ArrayList<TimeRate>>
            ) {
                if (response.isSuccessful) {
                    var list: ArrayList<TimeRate> = response.body()!!



                    _text?.value = list


                } else {
                    Toast.makeText(
                        getApplication(),
                        "Failed to retrieve items",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<ArrayList<TimeRate>>, t: Throwable) {
                Toast.makeText(
                    getApplication(),
                    "Error Occured" + t.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

        })



    }

}