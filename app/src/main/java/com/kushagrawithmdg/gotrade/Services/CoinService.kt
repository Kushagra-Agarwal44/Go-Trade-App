package com.kushagrawithmdg.gotrade.Services

import com.kushagrawithmdg.gotrade.Models.Asset
import com.kushagrawithmdg.gotrade.Models.Coin
import com.kushagrawithmdg.gotrade.Models.Rate
import com.kushagrawithmdg.gotrade.Models.TimeRate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinService {


    @GET("exchangerate/BTC/USD/")
    fun getRate(@Query("apikey")apikey:String="8ABF798C-F0DF-4BB2-BFB2-4707A2B3FD47"): Call<Rate>

    @GET("assets")
    fun getcryptolist(@Query("apikey")apikey:String="8ABF798C-F0DF-4BB2-BFB2-4707A2B3FD47" ):Call<List<Asset>>

    @GET("exchangerate/{cid}")
    fun getcoins(@Path("cid")cid:String,@Query("apikey")apikey:String="8ABF798C-F0DF-4BB2-BFB2-4707A2B3FD47", @Query("invert")invert: Boolean=true):Call<Coin>

    @GET("exchangerate/{asset_id_base}/{asset_id_quote}/history")
    fun gettimerate(@Path("asset_id_base")asset_id_base:String,@Path("asset_id_quote")asset_id_quote:String,
                    @Query("time_end")time_end:String,
                    @Query("apikey")apikey:String="8ABF798C-F0DF-4BB2-BFB2-4707A2B3FD47",
                    @Query("period_id")period_id:String="1DAY"
                    ):Call<ArrayList<TimeRate>>
}