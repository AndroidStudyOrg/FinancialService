package org.shop.financialservice.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object ViewUtil {
    /**
     *  EditText에서 action 버튼을 클릭했을 때 Listener를 받음
     */
    fun EditText.setOnEditorActionListener(action: Int, invoke: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == action) {
                invoke()
                true
            } else {
                false
            }
        }
    }

    /**
     *  키보드를 띄우고 키보드를 닫는 부분
     */
    fun EditText.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     *  키보드가 show 될 때 약간의 지연이 필요함
     *  지연이 있어야 키보드를 띄워줄 수 있음
     */
    fun EditText.showKeyboardDelay() {
        postDelayed({ showKeyboard() }, 200)
    }

    fun EditText.hideKeyboard() {
        this.clearFocus()
        val inputMethodManager =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}