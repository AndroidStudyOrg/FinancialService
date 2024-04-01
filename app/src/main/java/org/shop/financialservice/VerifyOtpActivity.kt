package org.shop.financialservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.shop.financialservice.databinding.ActivityVerifyOtpBinding

class VerifyOtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.view = this
    }
}