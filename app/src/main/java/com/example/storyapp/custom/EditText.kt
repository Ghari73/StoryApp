package com.example.storyapp.custom

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyapp.R

class EditText : AppCompatEditText {
    constructor(context: Context):super(context){
        init()
    }
    constructor(context: Context, attrs : AttributeSet): super(context,attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int): super(context,attrs,defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = resources.getDrawable(R.drawable.valid_text)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init(){
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (p0.isEmpty()){
                    error = resources.getString(R.string.pass_empty)
                }

                if(inputType == 128){
                    if(p0.toString().length < 8){
                        error = resources.getString(R.string.pass_restriction)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable) {
                if(inputType == 48){
                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (!this@EditText.text.toString().trim().matches(emailPattern.toRegex())) {
                        error = resources.getString(R.string.email_restriction)
                    }
                }
            }

        })
    }

}