package org.shop.financialservice

import android.content.Intent
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
        startActivity(Intent(this, PinActivity::class.java))
    }

    fun openVerifyOTP() {
        startActivity(Intent(this, IdentityInputActivity::class.java))
    }
}