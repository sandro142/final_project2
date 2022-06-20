package com.example.final_project

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.widget.Toast
import com.example.final_project.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.registerButton.setOnClickListener {
            validatedata()
        }
    }

    private var name =""
    private var email =""
    private var password =""

    private fun validatedata() {

        name = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        val confirmPassword = binding.confirmEt.text.toString().trim()

        if(name.isEmpty()){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
        }
        else if (confirmPassword.isEmpty()){
            Toast.makeText(this, "Confirm your password", Toast.LENGTH_SHORT).show()
        }
        else if (password != confirmPassword){
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show()
        }
        else{
            CreateUserAccount()
        }

    }

    private fun CreateUserAccount() {
        progressDialog.setMessage("creating new account")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                UpdateUserInfo()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "Error due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun UpdateUserInfo() {
        progressDialog.setMessage("saving info...")
        val timestamp = System.currentTimeMillis()

        val uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["name"] = name
        hashMap["email"] = email
        hashMap["profileImage"] = ""
        hashMap["userType"] = "uer"
        hashMap["timestamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "failed saving info due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}