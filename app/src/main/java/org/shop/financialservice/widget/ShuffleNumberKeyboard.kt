package org.shop.financialservice.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import org.shop.financialservice.databinding.WidgetShuffleNumberKeyboardBinding
import kotlin.random.Random

class ShuffleNumberKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {
    /**
     *  Activity 같은 경우 자동으로 bidning instance를 제거해서 지시의 대상이 되는데,
     *  CustomeView에 연결된 binding instance는 자동으로 참조가 끊어지지 않기 때문에 View가 끝날 때 참조를 끊어줘야 한다.
     *  따라서 실질적으로 사용하는 부분은 private으로 하고 직접 가져오는 부분은 private get 메소드만 활용해서 사용하면 된다.
     */
    private var _binding: WidgetShuffleNumberKeyboardBinding? = null
    private val binding get() = _binding!!

    init {
        _binding =
            WidgetShuffleNumberKeyboardBinding.inflate(LayoutInflater.from(context), this, true)
        shuffle()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    fun shuffle() {
        val keyNumberArray = ArrayList<String>()
        for (i in 0..9) {
            keyNumberArray.add(i.toString())
        }

        binding.gridLayout.children.forEach { view ->
            if (view is TextView && view.tag != null) {
                val randIndex = Random.nextInt(keyNumberArray.size)
                view.text = keyNumberArray[randIndex]
                keyNumberArray.removeAt(randIndex)
            }
        }
    }
}