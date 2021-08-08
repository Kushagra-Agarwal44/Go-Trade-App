package com.kushagrawithmdg.gotrade.ui.Rates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kushagrawithmdg.gotrade.R
import com.kushagrawithmdg.gotrade.RatesFragmentAdapter
import com.kushagrawithmdg.gotrade.ViewModels.MainActivityViewModel

class RatesFragment : Fragment(){
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var mAdapter: RatesFragmentAdapter
    private lateinit var model: RatesViewModel
    private lateinit var currencycode : String
    private lateinit var cryptolist : ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val root = inflater.inflate(R.layout.fragment_rates, container, false)
        model = ViewModelProvider(this).get(RatesViewModel::class.java)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            cryptolist= viewModel._data.value!!
            currencycode= viewModel.data.value!!
            Log.d("R","Currency"+currencycode)
            model.getCoin(currencycode,cryptolist).observe(viewLifecycleOwner, Observer {
                mAdapter.updaterate(it)
            })
        })

        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView3)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter  = RatesFragmentAdapter()
        recyclerView.adapter = mAdapter




        return root
    }



}