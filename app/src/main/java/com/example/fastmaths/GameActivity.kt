package com.example.fastmaths

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.example.fastmaths.databinding.ActivityGameBinding
import com.example.fastmaths.databinding.ActivityHomeBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding
    private lateinit var anim : Animation
    var winningResult = 0.0
    var level = 1
    private val operations = listOf("+","-","×","÷")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setOperation()
        binding.apply {
            btnOption1.setOnClickListener {
                checkResult(btnOption1.text.toString().toDouble())
            }
            btnOption2.setOnClickListener {
                checkResult(btnOption2.text.toString().toDouble())
            }
            btnOption2.setOnClickListener {
                checkResult(btnOption2.text.toString().toDouble())
            }
        }
    }
   @SuppressLint("SetTextI18n")
   private fun setOperation(){
       val randomFirst = (7..15).random() + level
       val randomSecond = (1..7).random() + level
       val randomOperation = (operations.indices).random()
       binding.tvProblem.text = "$randomFirst ${operations[randomOperation]} $randomSecond"
       when(operations[randomOperation]){
           "+" -> {winningResult = (randomFirst + randomSecond).toDouble()}
           "-" -> {winningResult =  (randomFirst - randomSecond).toDouble()}
           "×" -> {winningResult =  (randomFirst * randomSecond).toDouble()}
           "÷" -> {winningResult = randomFirst.toDouble() / randomSecond.toDouble()}
       }
       setOptions()
   }
    private fun setOptions(){
        val option = mutableListOf(winningResult , winningResult+(1..7).random(),winningResult-(1..3).random()).shuffled()
        binding.apply {
            btnOption1.text = "${option[1]}"
            btnOption2.text = "${option[0]}"
            btnOption3.text = "${option[2]}"
        }
    }
    private fun checkResult(clicked:Double){
        if (winningResult == clicked){
            //win
            setOperation()
        }else{
            //lose
           endGame()
        }
    }
    private fun endGame(){
        binding.apply {
            llEnd.isVisible = true
            llProblem.isVisible = false
            llbuttons.isVisible = false
            anim = AnimationUtils.loadAnimation(this@GameActivity, R.anim.winning)
            binding.llEnd.startAnimation(anim)
            btnToMain.setOnClickListener {
                val toHome = Intent(this@GameActivity,HomeActivity::class.java)
                startActivity(toHome)
            }
        }
    }
}