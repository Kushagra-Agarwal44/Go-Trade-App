package com.kushagrawithmdg.gotrade.ui.Rates

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kushagrawithmdg.gotrade.Services.CoinService
import com.kushagrawithmdg.gotrade.Models.Coin
import com.kushagrawithmdg.gotrade.Models.Rate
import com.kushagrawithmdg.gotrade.Services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatesViewModel(application: Application) : AndroidViewModel(application) {
//    val data = MutableLiveData<String>()
//    val _data = MutableLiveData<ArrayList<String>>()
//    fun setdata(item: String) {
//        data.value = item
//    }
//    fun set_data(item: ArrayList<String>){
//        _data.value = item
//    }

    private var _text: MutableLiveData<ArrayList<Rate>> ?=null

    fun getCoin(string: String,arrayList: ArrayList<String>):MutableLiveData<ArrayList<Rate >>{
        if (_text== null) {
            _text = MutableLiveData<ArrayList<Rate>>()
            loaddata(string, arrayList)
        }
        return _text as MutableLiveData<ArrayList<Rate>>
    }


    fun loaddata(string: String,arrayList: ArrayList<String>){
        val coinService: CoinService =
                ServiceBuilder.buildService(CoinService::class.java)

        val requestCall: Call<Coin> = coinService.getcoins(string)

        requestCall.enqueue(object : Callback<Coin> {


            override fun onResponse(
                    call: Call<Coin>,
                    response: Response<Coin>
            ) {
                if (response.isSuccessful) {
                    val coin: Coin = response.body()!!
                    var List: ArrayList<Rate>
                    var finallist = ArrayList<Rate>()
                    List = coin.rates
                    for (i in 0 until  List.size){
                        if (arrayList.contains(List[i].asset_id_quote) ){
                            finallist.add(List[i])
                        }
                    }
                    _text?.value = finallist
                }
                else {
                    Toast.makeText(
                            getApplication(),
                            "Failed to retrieve items",
                            Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<Coin>, t: Throwable) {
                Toast.makeText(
                        getApplication(),
                        "Error Occured" + t.toString(),
                        Toast.LENGTH_LONG
                ).show()
            }

        })
    }

}