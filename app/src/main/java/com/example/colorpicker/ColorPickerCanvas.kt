package com.example.colorpicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class ColorPickerCanvas(context: Context): View(context) {

    var red = 128
    var green = 128
    var blue = 128

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val width = width
        val brush = Paint()
        brush.setARGB(255, red, green, blue)
        canvas!!.drawRect(50f, 100f, (width-50).toFloat(), 300f, brush)
    }

    fun changeColor(red: Int, green: Int, blue: Int) {
        this.red = red
        this.green = green
        this.blue = blue
        invalidate()
    }
}