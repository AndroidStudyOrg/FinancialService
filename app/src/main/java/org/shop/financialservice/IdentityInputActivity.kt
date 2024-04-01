package org.shop.financialservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.shop.financialservice.databinding.ActivityIdentityInputBinding

class IdentityInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIdentityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityInputBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.view = this
    }
}