package org.shop.financialservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.shop.financialservice.databinding.ActivityMainBinding
import org.shop.financialservice.util.AppSignatureHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.view = this

        AppSignatureHelper(this).apply {
            Log.d("hash", "hash : ${appSignature}")
        }
    }

    fun openShuffle() {
        startActivity(Intent(this, PinActivity::class.java))
    }

    fun openVerifyOTP() {
        startActivity(Intent(this, IdentityInputActivity::class.java))
    }
}