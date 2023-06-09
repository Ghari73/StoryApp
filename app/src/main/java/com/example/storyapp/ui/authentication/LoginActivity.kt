package com.example.storyapp.ui.authentication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.model.AuthViewModel
import com.example.storyapp.model.ViewModelFactory
import com.example.storyapp.preference.AuthPreference
import com.example.storyapp.preference.UserModel
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.ui.story.StoryActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel by viewModels<AuthViewModel> { ViewModelFactory.getInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        setupAction()
        playAnimation()

        binding.register.setOnClickListener{
            startActivity(Intent(this@LoginActivity,RegActivity::class.java))
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction(){
        authViewModel.isLoading.observe(this){
            showLoading(it)
        }
        binding.loginButton.setOnClickListener{
            val email = binding.edLoginEmail.text.toString()
            val pass = binding.edLoginPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                pass.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }

                else -> {
                    authViewModel.loginPostData(email,pass)
                    authViewModel.login.observe(this@LoginActivity){

                        initSaveUser(UserModel(it.result?.name.toString(),"Bearer " + it.result?.token.toString(),true))
                    }

                    authViewModel.userLogin()
                    authViewModel.login.observe(this@LoginActivity){
                        if (!it.error){
                            startActivity(Intent(this@LoginActivity,StoryActivity::class.java))
                            finish()
                            Toast.makeText(this@LoginActivity,
                                resources.getString(R.string.login_berhasil),
                                Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@LoginActivity,resources.getString(R.string.login_gagal),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(state : Boolean){
        binding.apply {
            progressBar.visibility = if (state) View.VISIBLE else View.GONE
            loginButton.isEnabled = !state
        }
    }

    private fun playAnimation(){
        val orangeTrs = ObjectAnimator.ofFloat(binding.circleOrgLgn, View.TRANSLATION_X, -70f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }

        val blueTrs = ObjectAnimator.ofFloat(binding.circleBlueLgn, View.TRANSLATION_Y, 100f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }

        val purpleTrs = ObjectAnimator.ofFloat(binding.circlePrplLgn, View.TRANSLATION_Y, -100f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val msg = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val pic = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val emailTV = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailETL = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passTV = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passETL = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val isHave = ObjectAnimator.ofFloat(binding.isHave, View.ALPHA, 1f).setDuration(500)
        val reg = ObjectAnimator.ofFloat(binding.register, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        val blueFade = ObjectAnimator.ofFloat(binding.circleBlueLgn, View.ALPHA, 1f).setDuration(500)
        val orangeFade = ObjectAnimator.ofFloat(binding.circleOrgLgn, View.ALPHA, 1f).setDuration(500)
        val purpleFade = ObjectAnimator.ofFloat(binding.circlePrplLgn, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(pic,title,msg,emailTV,emailETL,passTV,passETL,login,isHave,reg,blueFade,orangeFade,purpleFade)
            startDelay = 600
            playTogether(blueTrs,orangeTrs,purpleTrs)
        }.start()
    }

    private fun initSaveUser(model : UserModel){
        authViewModel.saveStateUser(model)
    }
}