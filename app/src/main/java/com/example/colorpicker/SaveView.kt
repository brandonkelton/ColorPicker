package com.example.colorpicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.widget.EditText
import androidx.core.view.marginLeft

class SaveView(context: Context): View(context) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val saveTextInput = EditText(context)
        saveTextInput.hint = "Name your color..."
        saveTextInput.setTextColor(Color.BLACK)
        saveTextInput.setBackgroundColor(Color.WHITE)

    }
}