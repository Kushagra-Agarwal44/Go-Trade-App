package com.kushagrawithmdg.gotrade.ViewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kushagrawithmdg.gotrade.Services.CoinService
import com.kushagrawithmdg.gotrade.Models.Asset
import com.kushagrawithmdg.gotrade.Services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencySelectingActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var _text: MutableLiveData<ArrayList<Asset>>?=null

    fun getarraylist(): MutableLiveData<ArrayList<Asset>> {
        if (_text== null) {
            _text = MutableLiveData<ArrayList<Asset>>()
            load()
        }
        return _text as MutableLiveData<ArrayList<Asset>>
    }


    private fun load(){
        val coinService: CoinService =
                ServiceBuilder.buildService(CoinService::class.java)

        val requestCall: Call<List<Asset>> = coinService.getcryptolist()

        requestCall.enqueue(object : Callback<List<Asset>> {


            override fun onResponse(
                call: Call<List<Asset>>,
                response: Response<List<Asset>>
            ) {
                if (response.isSuccessful) {
                    val list: List<Asset> = response.body()!!
                    Log.d("MOdel",list[0].asset_id)
                    val arrayList = ArrayList<Asset>()
                    for (i in 0 until list.size) {
                        if (list[i].type_is_crypto == 0) {
                            arrayList.add(list[i])
                        }
                    }
                    Log.d("Model",arrayList[0].asset_id)
                    _text?.value = arrayList
                } else {
                    Toast.makeText(
                        getApplication(),
                        "Failed to retrieve items",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<List<Asset>>, t: Throwable) {
                Toast.makeText(

                    getApplication(),
                    "Error Occured" + t.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

        })

    }

}

