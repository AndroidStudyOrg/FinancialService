package org.shop.financialservice

import android.os.Bundle
import android.os.CountDownTimer
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.gms.auth.api.phone.SmsRetriever
import org.shop.financialservice.databinding.ActivityVerifyOtpBinding
import org.shop.financialservice.util.ViewUtil.setOnEditorActionListener
import org.shop.financialservice.util.ViewUtil.showKeyboardDelay

class VerifyOtpActivity : AppCompatActivity(), AuthOtpReceiver.OtpReceiveListener {
    private lateinit var binding: ActivityVerifyOtpBinding

    private var smsReceiver: AuthOtpReceiver? = null
    private var timer: CountDownTimer? = object : CountDownTimer((3 * 60 * 1000), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val min = (millisUntilFinished / 1000) / 60
            val sec = (millisUntilFinished / 1000) % 60
            binding.timerTextView.text = "$min:${String.format("%02d", sec)}"
        }

        override fun onFinish() {
            binding.timerTextView.text = ""
            Toast.makeText(
                this@VerifyOtpActivity,
                "입력 제한시간을 초과하였습니다.\n다시 시도해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        binding.otpCodeEditText.showKeyboardDelay()
        startSmsReceiver()
    }

    override fun onDestroy() {
        clearTimer()
        stopSmsReceiver()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.view = this

        initView()
    }

    /**
     *  타이머를 실질적으로 실행시키고 그 이후에 타이머 입력 번호가 다 입력됐을 때 타이머를 멈추는 코드
     */
    private fun initView() {
        startTimer()
        with(binding) {
            otpCodeEditText.doAfterTextChanged {
                if (otpCodeEditText.length() >= 6) {
                    stopTimer()
                    Toast.makeText(this@VerifyOtpActivity, "인증번호 입력이 완료되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            otpCodeEditText.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {

            }
        }
    }

    override fun onOtpReceived(otp: String) {
        binding.otpCodeEditText.setText(otp)
    }

    private fun startTimer() {
        timer?.start()
    }

    private fun stopTimer() {
        timer?.cancel()
    }

    private fun clearTimer() {
        stopTimer()
        timer = null
    }

    private fun startSmsReceiver() {
        SmsRetriever.getClient(this).startSmsRetriever().also { task ->
            task.addOnSuccessListener {
                if (smsReceiver == null) {
                    smsReceiver = AuthOtpReceiver().apply {
                        setOtpListener(this@VerifyOtpActivity)
                    }
                }
                registerReceiver(smsReceiver, smsReceiver!!.doFilter(), RECEIVER_NOT_EXPORTED)
            }

            task.addOnFailureListener {
                stopSmsReceiver()
            }
        }
    }

    private fun stopSmsReceiver() {
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver)
            smsReceiver = null
        }
    }
}