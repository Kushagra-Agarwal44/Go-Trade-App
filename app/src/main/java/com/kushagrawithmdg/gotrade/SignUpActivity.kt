package com.kushagrawithmdg.gotrade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class
SignUpActivity : AppCompatActivity() {
    private lateinit var edittextemail: EditText
    private lateinit var edittextpassword: EditText
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        edittextemail = findViewById(R.id.signupemail)
        edittextpassword = findViewById(R.id.signuppassword)


        auth = Firebase.auth




        signupbutton.setOnClickListener {
            registerUser()
        }


    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            finish()
            startActivity(Intent(this, InformationActivity::class.java))
        }
    }







    private fun registerUser() {

        val email: String = edittextemail.text.toString()
        val password: String = edittextpassword.text.toString()

        if (email.isEmpty()) {
            edittextemail.error = "Email is required"
            edittextemail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            edittextpassword.error = "Password is required"
            edittextpassword.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edittextemail.error = "Please Enter a valid Email"
            edittextemail.requestFocus()
            return
        }
        if (password.length < 6) {
            edittextpassword.error = "Minimum length should be 6 "
            edittextpassword.requestFocus()
            return
        }
        progressBar.visibility = View.VISIBLE



        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Registration Successful", Toast.LENGTH_SHORT).show()
//                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
//                        Toast.makeText(this, "Verification Email sent", Toast.LENGTH_LONG).show()
//                    }
                    progressBar.visibility= View.GONE
                    val intent = Intent(this, InformationActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
                if (task.exception is FirebaseAuthUserCollisionException) {
                    Toast.makeText(baseContext, "User already registered ", Toast.LENGTH_LONG)
                        .show()
                    progressBar.visibility= View.GONE

                }
                else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    progressBar.visibility= View.GONE
                }
            }



    }








}