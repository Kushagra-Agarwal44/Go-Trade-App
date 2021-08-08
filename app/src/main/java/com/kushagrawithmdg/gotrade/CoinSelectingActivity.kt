package com.kushagrawithmdg.gotrade

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kushagrawithmdg.gotrade.Daos.UserDao
import com.kushagrawithmdg.gotrade.Models.Asset
import com.kushagrawithmdg.gotrade.Models.User
import com.kushagrawithmdg.gotrade.ViewModels.CoinSelectingActivityViewModel
import kotlinx.android.synthetic.main.activity_coin_selecting.*
import kotlinx.android.synthetic.main.item_fragment.*

class CoinSelectingActivity : AppCompatActivity(), LikeClicked {

    private lateinit var model: CoinSelectingActivityViewModel
    private lateinit var mAdapter: CoinSelectingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_selecting)
        val muser = intent.getSerializableExtra("KKK") as User
        model =
                ViewModelProvider(this).get(CoinSelectingActivityViewModel::class.java)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter= CoinSelectingAdapter(this, muser)
        recyclerView.adapter = mAdapter


        model.getarraylist().observe(this, Observer {
            mAdapter.updatel(it)
        })


    }



    override fun onLikeClicked(item: Asset) {

        Toast.makeText(this@CoinSelectingActivity, "${item.name} is Clicked ", Toast.LENGTH_SHORT).show()
        val userDao = UserDao()
        userDao.updateLikes(item.asset_id)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}