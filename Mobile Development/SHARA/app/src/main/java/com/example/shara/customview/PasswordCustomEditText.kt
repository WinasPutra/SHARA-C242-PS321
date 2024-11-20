package com.example.shara.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener

class PasswordCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener { p0 ->
            error = if (p0.toString().length < 8) {
                "Password tidak boleh kurang dari 8"
            } else {
                null
            }
        }
    }
}