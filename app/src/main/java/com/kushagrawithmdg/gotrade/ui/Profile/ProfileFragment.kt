package com.kushagrawithmdg.gotrade.ui.Profile

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kushagrawithmdg.gotrade.*
import com.kushagrawithmdg.gotrade.Daos.UserDao
import com.kushagrawithmdg.gotrade.Models.User
import com.kushagrawithmdg.gotrade.ViewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var homeViewModel: ProfileViewModel
    private lateinit var auth: FirebaseAuth
private lateinit var imageView: ImageView
private lateinit var button: Button
private lateinit var button2: Button
private lateinit var textView: TextView
private lateinit var progressBar: ProgressBar
    private lateinit var muser:User
private lateinit var button3 : Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
         textView = root.findViewById(R.id.textView3)
         imageView = root.findViewById(R.id.imageView2)
        button = root.findViewById(R.id.Coinbutton)
        button2 = root.findViewById(R.id.currencybutton)
        button3 = root.findViewById(R.id.signoutbutton)
        progressBar = root.findViewById(R.id.profileprogressbar)
        progressBar.visibility= View.VISIBLE
        auth = Firebase.auth
        muser= User()
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            withContext(Dispatchers.Main){
                     muser =user
                viewModel.data.value= muser.currencycode
                viewModel._data.value=muser.likedcrypto
                viewModel.text.value = muser.likedcrypto[0]
Log.d("PR","model" +muser.likedcrypto.toString())
            }
        }

        button.setOnClickListener {
            val intent = Intent(activity, CoinSelectingActivity::class.java)
            intent.putExtra("KKK", muser)
            Log.d("PR",muser.likedcrypto.toString())
            startActivity(intent)
            requireActivity().finish()
        }
        button2.setOnClickListener {
            val intent = Intent(activity, CurrencySelectingActivity::class.java)
            intent.putExtra("KKK", muser)
            startActivity(intent)
            requireActivity().finish()
        }
        Log.d("Pr","ONCREATEVIEW"+muser.likedcrypto.toString())

        button3.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity,SignInActivity::class.java))
        }

        loadUserInformation()

        return root
    }






    private fun loadUserInformation() {
        progressBar.visibility=View.VISIBLE
        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            if (user.photoUrl != null) {
                val userkiphoto = user.photoUrl.toString()
//                Toast.makeText(activity, user.photoUrl.toString(), Toast.LENGTH_SHORT).show()
                Log.d("PF", "imageurl ${user.photoUrl.toString()}")
                Glide.with(this@ProfileFragment).load(userkiphoto)
                        .listener(object : RequestListener<Drawable?> {
                            override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    isFirstResource: Boolean
                            ): Boolean {
//                                Toast.makeText(activity, "Sorry bhai image load nahi ho payi", Toast.LENGTH_SHORT).show()
                                Log.d("PF", "Image couldn't be loaded")
                                profileprogressbar.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any,
                                    target: Target<Drawable?>,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                            ): Boolean {
//                                Toast.makeText(activity, "Work Done", Toast.LENGTH_SHORT).show()
                                Log.d("PF","Work Done")
                                progressBar.visibility = View.INVISIBLE
                                return false
                            }
                        }).circleCrop().into(imageView)
            }
            if (user.displayName != null) {
                textView.text =(user.displayName)
            }

        }


    }
}