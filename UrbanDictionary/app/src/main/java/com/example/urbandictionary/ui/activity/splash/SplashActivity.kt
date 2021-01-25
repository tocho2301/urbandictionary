package com.example.urbandictionary.ui.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.urbandictionary.databinding.ActivitySplashBinding
import com.example.urbandictionary.ui.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    private val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.doSplash()
        setUpObservables()
    }

    private fun setUpObservables() {
        viewModel.openHomeActivity.observe(this, {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}