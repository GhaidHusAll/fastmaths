package com.example.fastmaths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import com.example.fastmaths.database.FastMathsDatabase
import com.example.fastmaths.database.User
import com.example.fastmaths.databinding.ActivityHomeBinding
import com.example.fastmaths.model.FastMathsViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var shakeAnim : Animation
    private lateinit var binding : ActivityHomeBinding
    private val dao by lazy { FastMathsDatabase.getDatabase(this).dao() }
    private val vm by lazy { ViewModelProvider(this).get(FastMathsViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        binding.btnStart.startAnimation(shakeAnim)
        binding.btnStart.setOnClickListener {
            val toGame = Intent(this@HomeActivity,GameActivity::class.java)
            startActivity(toGame)
        }
        getInformation()
    }
    private fun getInformation(){
        vm.getInformation(dao).observe(this,{
            user ->
            Log.d("HomeLog","----- ${user.pk}")
            checkUserInformation(user)
        })
    }
    private fun checkUserInformation(theUser:User){
        if (theUser.pk == -1){
            //doesn't exist
            vm.addInformation(User(0,0,1),dao)
        }
    }
}