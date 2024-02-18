package org.shop.financialservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.shop.financialservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.view = this
    }

    fun openShuffle() {

    }

    fun openVerifyOTP() {

    }
}