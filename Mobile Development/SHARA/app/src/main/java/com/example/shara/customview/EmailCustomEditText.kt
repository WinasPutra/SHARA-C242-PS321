package com.example.shara.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.example.shara.R

class EmailCustomEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
): AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                        setError(context.getString(R.string.email_invalid), null)
                    } else {
                        error = null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

}