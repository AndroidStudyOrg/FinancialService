package org.shop.financialservice

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import org.shop.financialservice.databinding.ActivityIdentityInputBinding
import org.shop.financialservice.util.ViewUtil.hideKeyboard
import org.shop.financialservice.util.ViewUtil.setOnEditorActionListener
import org.shop.financialservice.util.ViewUtil.showKeyboard
import org.shop.financialservice.util.ViewUtil.showKeyboardDelay

class IdentityInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIdentityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentityInputBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.view = this
        initView()
        binding.nameEditText.showKeyboardDelay()
    }

    private fun initView() {
        with(binding) {
            nameEditText.setOnEditorActionListener(EditorInfo.IME_ACTION_NEXT) {
                birthdayLayout.isVisible = true
                birthdayEditText.showKeyboard()
            }

            birthdayEditText.doAfterTextChanged {
                if (birthdayEditText.length() > 7) {
                    genderLayout.isVisible = true
                    birthdayEditText.hideKeyboard()
                }
            }

            genderChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                if (!telecomLayout.isVisible) {
                    telecomLayout.isVisible = true
                }
            }

            telecomChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                if (!phoneLayout.isVisible) {
                    phoneLayout.isVisible = true
                    phoneEdit.showKeyboard()
                }
            }

            phoneEdit.doAfterTextChanged {
                if (phoneEdit.length() > 10) {
                    confirmButton.isVisible = true
                    phoneEdit.hideKeyboard()
                }
            }

            /**
             *  11자리가 아닌, 10자리일 수도 있기 때문에
             */
            phoneEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                if (phoneEdit.length() > 9) {
                    confirmButton.isVisible = true
                    phoneEdit.hideKeyboard()
                }
            }
        }
    }
}