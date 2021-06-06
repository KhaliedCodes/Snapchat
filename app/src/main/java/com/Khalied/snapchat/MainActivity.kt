package com.Khalied.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser!= null){
            val intent = Intent(this, Snapchat::class.java)
            startActivity(intent)
        }else{
            login(findViewById(R.id.login))
        }
    }

    fun register(view: View){
        var email: String = findViewById<EditText>(R.id.email)?.text.toString()
        var password: String = findViewById<EditText>(R.id.password)?.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                Log.d("Anything", "signInWithEmail:success")
                val intent = Intent(this, Snapchat::class.java)
                startActivity(intent)
            }
            else{
                Log.d("Anything", "signInWithEmail:something wrong")
                Toast.makeText(
                        baseContext, "failed.",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    fun login(view: View){

        var email: String = findViewById<EditText>(R.id.email)?.text.toString()
        var password: String = findViewById<EditText>(R.id.password)?.text.toString()
        
        if(email != "" && password != "") {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Anything", "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, Snapchat::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Anything", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // ...
                    }

                    // ...
                }
        }else{
            Toast.makeText(
                baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("Anything", "signInWithEmail:faileed")
        }
    }
}