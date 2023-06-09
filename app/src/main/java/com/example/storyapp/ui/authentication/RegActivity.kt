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
import com.example.storyapp.databinding.ActivityRegBinding
import com.example.storyapp.model.AuthViewModel
import com.example.storyapp.model.ViewModelFactory
import com.example.storyapp.preference.AuthPreference
import com.example.storyapp.ui.main.MainActivity

class RegActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegBinding
    private val authViewModel by viewModels<AuthViewModel> { ViewModelFactory.getInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        setupAction()
        playAnimation()

        binding.login.setOnClickListener{
            startActivity(Intent(this@RegActivity,LoginActivity::class.java))
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
        binding.apply {
            regButton.setOnClickListener{
                val name = edRegisterName.text.toString()
                val email = edRegisterEmail.text.toString()
                val pass = edRegisterPassword.text.toString()

                when {
                    name.isEmpty() -> {
                        binding.emailEditTextLayout.error = "Masukkan nama"
                    }
                    email.isEmpty() -> {
                        binding.emailEditTextLayout.error = "Masukkan email"
                    }
                    pass.isEmpty() -> {
                        binding.passwordEditTextLayout.error = "Masukkan password"
                    }

                    else -> {
                        authViewModel.regPostData(name,email,pass)
                        authViewModel.register.observe(this@RegActivity){
                            if(!it.error){
                                Toast.makeText(this@RegActivity,resources.getString(R.string.reg_berhasil), Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@RegActivity,LoginActivity::class.java))
                                finish()
                            }else{
                                Toast.makeText(this@RegActivity,resources.getString(R.string.reg_gagal), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(state : Boolean){
        binding.apply {
            progressBar.visibility = if (state) View.VISIBLE else View.GONE
            regButton.isEnabled = !state
        }
    }

    private fun playAnimation(){
        val orangeTrs = ObjectAnimator.ofFloat(binding.circleOrg, View.TRANSLATION_Y, 100f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }

        val blueTrs = ObjectAnimator.ofFloat(binding.circleBlue, View.TRANSLATION_Y, -100f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }

        val purpleTrs = ObjectAnimator.ofFloat(binding.circlePrpl, View.TRANSLATION_X, 70f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }

        val pic = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val titleTV = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTV = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameETL = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTV = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailETL = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passTV = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passETL = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val isHave = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)
        val signupBtn = ObjectAnimator.ofFloat(binding.regButton, View.ALPHA, 1f).setDuration(500)

        val acs1 = ObjectAnimator.ofFloat(binding.circleOrg, View.ALPHA, 1f).setDuration(500)
        val acs2 = ObjectAnimator.ofFloat(binding.circleBlue, View.ALPHA, 1f).setDuration(500)
        val acs3 = ObjectAnimator.ofFloat(binding.circlePrpl, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(pic,titleTV,nameTV,nameETL,emailTV,emailETL,passTV,passETL,isHave,login, signupBtn, acs1, acs2, acs3)
            playTogether(orangeTrs,purpleTrs,blueTrs)
            startDelay = 600
        }.start()
    }
}