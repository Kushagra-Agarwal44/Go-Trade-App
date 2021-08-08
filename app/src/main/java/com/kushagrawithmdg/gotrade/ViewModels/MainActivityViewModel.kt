package com.kushagrawithmdg.gotrade.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kushagrawithmdg.gotrade.Models.Asset

class MainActivityViewModel : ViewModel() {
   var text = MutableLiveData<String>()
    val data = MutableLiveData<String>()
    val _data = MutableLiveData<ArrayList<String>>()
    fun setdata(item: String) {
        data.value = item
    }
    fun set_data(item: ArrayList<String>){
        _data.value = item
    }
  fun settext(item: String){
      text.value= item
  }
}
