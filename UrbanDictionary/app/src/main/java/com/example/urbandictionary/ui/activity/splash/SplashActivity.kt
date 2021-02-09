package com.example.urbandictionary.ui.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.urbandictionary.databinding.ActivitySplashBinding
import com.example.urbandictionary.di.UrbanDictionaryApplication
import com.example.urbandictionary.ui.activity.home.HomeActivity
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SplashViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UrbanDictionaryApplication).getComponent().inject(this)
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