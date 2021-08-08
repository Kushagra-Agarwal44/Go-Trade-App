package com.kushagrawithmdg.gotrade.Daos

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushagrawithmdg.gotrade.Models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserDao {
private final val TAG = "1"
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    val auth = Firebase.auth
    fun addUser(user: User?) {

        Log.d(TAG,"user wala function call to hua")
        user?.let {

            GlobalScope.launch(Dispatchers.IO) {
                var docRef = usersCollection.document(user.uid)


                    if (docRef.get().isSuccessful) {
                        usersCollection.document(user.uid).set(it)

                    } else {
                        null
                    }


                //                val checkuser = getUserById(it.uid).await().toObject(User::class.java)
//                if (checkuser== null){
//                    usersCollection.document(user.uid).set(it)
//                }
//              else {
//                  null
//                }
            }
        }
    }
    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()
    }

    fun updateLikes(s: String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val user = getUserById(currentUserId).await().toObject(User::class.java)!!
            val isLiked = user.likedcrypto.contains(s)
          if (isLiked){
user.likedcrypto.remove(s)
          }
else{
user.likedcrypto.add(s)
          }
            usersCollection.document(currentUserId).set(user)

        }
    }

    fun updatecurrency(assetId: String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val user = getUserById(currentUserId).await().toObject(User::class.java)!!

                user.currencycode = assetId

            usersCollection.document(currentUserId).set(user)

        }

    }
}