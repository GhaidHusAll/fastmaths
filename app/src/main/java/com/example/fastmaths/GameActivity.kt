package com.example.fastmaths

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.fastmaths.database.FastMathsDatabase
import com.example.fastmaths.database.User
import com.example.fastmaths.databinding.ActivityGameBinding
import com.example.fastmaths.model.FastMathsViewModel
import java.util.*

class GameActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val dao by lazy { FastMathsDatabase.getDatabase(this).dao() }
    private val vm by lazy { ViewModelProvider(this).get(FastMathsViewModel::class.java) }
    private lateinit var binding : ActivityGameBinding
    private lateinit var anim : Animation
    private lateinit var animEnd : Animation
    var time = 20000L
    private lateinit var timer : CountDownTimer
    var winningResult = 0F
    var level = 1
    var score = 0
    private val operations = listOf("+","-","×","÷")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setOperation()
        binding.tvScore.text = "Your Score : $score"
        binding.apply {
            btnOption1.setOnClickListener {
                checkResult(btnOption1.text.toString().toFloat())
            }
            btnOption2.setOnClickListener {
                checkResult(btnOption2.text.toString().toFloat())
            }
            btnOption3.setOnClickListener {
                checkResult(btnOption3.text.toString().toFloat())
            }
            btnQuit.setOnClickListener {endGame("Quit Playing")}
        }

    }
   @SuppressLint("SetTextI18n")
   private fun setOperation(){
       val randomFirst = (7..15).random() + level
       val randomSecond = (1..7).random() + level
       val randomOperation = (operations.indices).random()
       binding.tvProblem.text = "$randomFirst ${operations[randomOperation]} $randomSecond"
       when(operations[randomOperation]){
           "+" -> {winningResult = (randomFirst + randomSecond).toFloat()}
           "-" -> {winningResult =  (randomFirst - randomSecond).toFloat()}
           "×" -> {winningResult =  (randomFirst * randomSecond).toFloat()}
           "÷" -> {winningResult = randomFirst.toFloat() / randomSecond.toFloat()}
       }
       setOptions()
       if (this::timer.isInitialized){
           timer.cancel()
       }
       setTime()

   }
    private fun setOptions(){
        val option = mutableListOf(winningResult , winningResult+(1..7).random(),winningResult-(1..3).random()).shuffled()
        binding.apply {
            btnOption1.text = "${option[1]}"
            btnOption2.text = "${option[0]}"
            btnOption3.text = "${option[2]}"
        }
    }
    private fun checkResult(clicked:Float){
        if (winningResult == clicked){
            //win
            setOperation()
            score =+ 1
            binding.tvScore.text = "Your Score : $score"
        }else{
            //lose
           endGame("inCorrect Answer Try Again")
        }
    }
    private fun endGame(message :String){
        val info = vm.theUser
        if (info.value?.highScore!! < score){
            vm.updateInformation(User(info.value?.pk!!,score,info.value?.level!!),dao)
        }
        binding.apply {
            llEnd.isVisible = true
            llProblem.isVisible = false
            llbuttons.isVisible = false
            cvTime.isVisible = false
            tvEndMessage.text = message
            tvResultAndScore.text = "correct answers is $score"
            anim = AnimationUtils.loadAnimation(this@GameActivity, R.anim.enter)
            binding.llEnd.startAnimation(anim)
            btnToMain.setOnClickListener {
                animEnd = AnimationUtils.loadAnimation(this@GameActivity, R.anim.exit)
                binding.llEnd.startAnimation(animEnd)
                animEnd.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationStart(p0: Animation?) {
                       // binding.llEnd.startAnimation(animEnd)
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        val toHome = Intent(this@GameActivity,HomeActivity::class.java)
                        startActivity(toHome)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                        TODO("Not yet implemented")
                    }

                })

            }
        }
    }
    private fun setTime(){
         time = 20000L
        val totalTime = time
         timer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = "0:${time.toInt()/1000}"
                val percentage = (time.toDouble() / totalTime.toDouble()) * 100
                time -= 1000
                binding.timeProgress.progress = percentage.toInt()
            }

            override fun onFinish() {
                binding.tvTimer.text = "0:00"
                binding.timeProgress.progress = 0
                endGame("Time Is Up Try Again")
            }
        }
        timer.start()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("GameActivity","IN onRestart")

    }

    override fun onPause() {
        super.onPause()
        Log.d("GameActivity","IN onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("GameActivity","IN onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("GameActivity","IN onDestroy")

    }

}