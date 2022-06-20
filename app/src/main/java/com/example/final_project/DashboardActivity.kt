package com.example.final_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var numberArrayList: ArrayList<ModelNumber>
    private lateinit var adapterNumber: AdapterNumber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        loadNumbers()

        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.addNumberBtn.setOnClickListener {
            startActivity(Intent(this, NumberAddActivity::class.java))
        }
    }

    private fun loadNumbers() {
        numberArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("numbers")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                numberArrayList.clear()
                for (i in snapshot.children){
                    val model = i.getValue(ModelNumber::class.java)
                    numberArrayList.add(model!!)
                }
                adapterNumber = AdapterNumber(this@DashboardActivity, numberArrayList)
                binding.numbersRv.adapter = adapterNumber
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            binding.subTitleTv.text = "Not logged in"
        }
        else{
            val email = firebaseUser.email
            binding.subTitleTv.text = email
        }
    }
}