package org.shop.financialservice

import android.content.Intent
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
                if (validName()) {
                    nameLayout.error = null
                    if (phoneLayout.isVisible) {
                        confirmButton.isVisible = true
                    } else {
                        birthdayLayout.isVisible = true
                        birthdayEditText.showKeyboard()
                    }
                } else {
                    confirmButton.isVisible = false
                    nameLayout.error = "1자 이상의 한글을 입력해주세요."
                }
            }

            birthdayEditText.doAfterTextChanged {
                if (birthdayEditText.length() > 7) {
                    if (validBirthday()) {
                        birthdayLayout.error = null
                        if (phoneLayout.isVisible) {
                            confirmButton.isVisible = true
                        } else {
                            genderLayout.isVisible = true
                            birthdayEditText.hideKeyboard()
                        }
                    } else {
                        confirmButton.isVisible = false
                        birthdayLayout.error = "생년원일 형식이 다릅니다."
                    }
                }
            }

            birthdayEditText.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                val isValid = validBirthday() && birthdayEditText.length() > 7
                if (isValid) {
                    confirmButton.isVisible = phoneLayout.isVisible
                    birthdayLayout.error = null
                } else {
                    birthdayLayout.error = "생년월일 형식이 다릅니다."
                }
                birthdayEditText.hideKeyboard()
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
                    if (validPhone()) {
                        phoneLayout.error = null
                        confirmButton.isVisible = true
                        phoneEdit.hideKeyboard()
                    } else {
                        confirmButton.isVisible = false
                        phoneLayout.error = "전화번호 형식이 다릅니다."
                    }
                }
            }

            /**
             *  11자리가 아닌, 10자리일 수도 있기 때문에
             */
            phoneEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                confirmButton.isVisible = phoneEdit.length() > 9 && validPhone()
                phoneEdit.hideKeyboard()

            }
        }
    }

    fun onClickDone() {
        if (!validName()) {
            binding.nameLayout.error = "1자 이상의 한글을 입력해주세요."
            return
        }

        if (!validBirthday()) {
            binding.birthdayLayout.error = "생년월일 형식이 다릅니다."
            return
        }

        if (!validPhone()) {
            binding.phoneLayout.error = "전화번호 형식이 다릅니다."
            return
        }

        startActivity(Intent(this, VerifyOtpActivity::class.java))
    }

    private fun validName() = !binding.nameEditText.text.isNullOrBlank() && REGEX_NAME.toRegex()
        .matches(binding.nameEditText.text!!)

    private fun validBirthday() =
        !binding.birthdayEditText.text.isNullOrBlank() && REGEX_BIRTHDAY.toRegex()
            .matches(binding.birthdayEditText.text!!)

    private fun validPhone() = !binding.phoneEdit.text.isNullOrBlank() && REGEX_PHONE.toRegex()
        .matches(binding.phoneEdit.text!!)

    companion object {
        // 이름: 한글 2자 이상만 허용
        private const val REGEX_NAME = "^[가-힣]{2,}\$"

        // 생년월일: 앞자리가 19XX이거나 20XX 0~9까지 2자리, 월 앞자리가 0일때는 1~9, 1일때는 0~2까지, 일 앞자리가 0일때는 1~9까지, 1이거나 2일때는 0~9까지, 3일때는 0이거나 1
        private const val REGEX_BIRTHDAY = "^(19|20)[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])"

        /**
         *  휴대폰 번호: 01로 시작하고, 010 011 016 017 018 019
         *  중간자리는 0~9까지 3자리나 4자리
         *  마지막 자리는 0~9까지 4자리
         */
        private const val REGEX_PHONE = "^01([016789])([0-9]{3,4})([0-9]{4})"
    }
}