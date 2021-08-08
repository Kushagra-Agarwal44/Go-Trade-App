package com.kushagrawithmdg.gotrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kushagrawithmdg.gotrade.Daos.UserDao
import com.kushagrawithmdg.gotrade.Models.Asset
import com.kushagrawithmdg.gotrade.Models.User
import com.kushagrawithmdg.gotrade.ViewModels.CurrencySelectingActivityViewModel

class CurrencySelectingActivity : AppCompatActivity(), ONLikeClicked {

    private lateinit var model: CurrencySelectingActivityViewModel
    private lateinit var mAdapter: CurrencySelectingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_selecting)
        val muser = intent.getSerializableExtra("KKK") as User
        Log.d("CSR",muser.likedcrypto.toString())
        model =
            ViewModelProvider(this).get(CurrencySelectingActivityViewModel::class.java)



        val recyclerView: RecyclerView = findViewById(R.id.recyclerView4)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter= CurrencySelectingAdapter(this, muser)
        recyclerView.adapter = mAdapter


        model.getarraylist().observe(this, Observer {
            mAdapter.updatel(it)
            Log.d("CSR", "model"+ it[0].asset_id)
        })


    }



    override fun onLikeClicked(item: Asset) {

        Toast.makeText(baseContext, "${item.asset_id} will be set as Favourite ", Toast.LENGTH_SHORT).show()
        val userDao = UserDao()
        userDao.updatecurrency(item.asset_id)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}