package com.example.final_project

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.mbms.DownloadProgressListener
import android.widget.Toast
import com.example.final_project.databinding.ActivityNumberAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NumberAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNumberAddBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var  progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.addBtn.setOnClickListener {
            validateData()
        }
    }

    private var number = ""

    private fun validateData() {
        number = binding.numberEt.text.toString().trim()

        if(number.isEmpty()){
            Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show()
        }
        else{
            addNumberFirebase()
        }
    }

    private fun addNumberFirebase() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["timestamp"] = timestamp
        hashMap["number"] = number
        hashMap["uid"] = "${firebaseAuth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("numbers")

        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "error due to ${e.message}", Toast.LENGTH_SHORT).show()
            }


    }
}