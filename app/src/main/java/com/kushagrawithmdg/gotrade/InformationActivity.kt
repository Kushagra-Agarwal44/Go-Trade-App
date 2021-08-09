package com.kushagrawithmdg.gotrade

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.kushagrawithmdg.gotrade.Daos.UserDao
import com.kushagrawithmdg.gotrade.Models.User
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.io.IOException

class InformationActivity : AppCompatActivity() {
    private val CHOOSE_IMAGE = 101
  private lateinit var profileimageurl: String
    private lateinit var uriofimage: Uri
    lateinit var editText: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        editText = findViewById(R.id.editTextTextPersonName)
        auth = Firebase.auth

        imageView.setOnClickListener {
            showImageChooser()
        }
        save.setOnClickListener {
            saveUserInformation()
        }


    }



    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            finish()
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }






    private fun saveUserInformation() {


        val displayname: String = editText.getText().toString()

        if (displayname.isEmpty()) {
            editText.setError("Name required")
            editText.requestFocus()
            return
        }
        val user: FirebaseUser? = auth.currentUser
        progressBar3.visibility= View.VISIBLE

        if (user != null && profileimageurl!=null ) {
            val profile = UserProfileChangeRequest.Builder().setDisplayName(displayname)
                .setPhotoUri(uriofimage).build()
            user.updateProfile(profile).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    UpdateUI()
                }else{
                    Toast.makeText(this,"Some Error Occured Please Try Again",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this,"Photo Must be Uploaded First",Toast.LENGTH_SHORT).show()
            return}
    }

    private fun UpdateUI() {
        val firebaseUser: FirebaseUser? = auth.currentUser

        if (firebaseUser != null) {
            val list = ArrayList<String>()
            list.add("BTC")
            val user = User(firebaseUser.uid, firebaseUser.displayName, firebaseUser.photoUrl.toString(),"USD",list)
            val usersDao = UserDao()
            usersDao.addUser(user)
            progressBar3.visibility=View.GONE
            Toast.makeText(this, "Profile Made Successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        } else {
            signinbutton.visibility = View.VISIBLE
            progressBar2.visibility = View.GONE
        }
    }


    private fun uploadimagetofirebase( imageuri: Uri) {
        val profileImageReference = FirebaseStorage.getInstance()
            .getReference("profilepics/" + System.currentTimeMillis() + ".jpg")
        if (imageuri != null) {
            profileImageReference.putFile(imageuri)
                .addOnSuccessListener { taskSnapshot ->
                    if (taskSnapshot.metadata != null) {
                        if (taskSnapshot.metadata!!.reference != null) {
                            val result = taskSnapshot.storage.downloadUrl
                            result.addOnSuccessListener { uri ->
                                uriofimage = uri
                                profileimageurl = uri.toString()
                                Log.d("IA",profileimageurl.toString())
                            }
                        }
                    }
                    Toast.makeText(this, "Image Uploaded", Toast.LENGTH_LONG).show()
                    progressBar3.visibility = View.INVISIBLE
                }
                .addOnFailureListener { e ->
                    progressBar3.setVisibility(View.INVISIBLE)
//                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    Log.d("info",e.message.toString())
                }
        }
    }


    private fun showImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "select profile image"),
            CHOOSE_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
           val uriprofileimage = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriprofileimage)
                progressBar3.visibility = View.VISIBLE
                imageView.setImageBitmap(bitmap)
                uploadimagetofirebase(uriprofileimage)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else Toast.makeText(this, "Image not choosen", Toast.LENGTH_LONG).show()
    }




}