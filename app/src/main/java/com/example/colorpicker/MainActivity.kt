package com.example.colorpicker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.constraintLayout_main

class MainActivity : AppCompatActivity() {

    private lateinit var background: ColorPickerCanvas
    private var savedColors: ArrayList<SkyColor> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        background = ColorPickerCanvas(this)
        constraintLayout_main.addView(background)
        setupListeners()
    }

    private fun setupListeners() {
        seekBar_red.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                background.changeColor(progress, background.green, background.blue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBar_green.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                background.changeColor(background.red, progress, background.blue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBar_blue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                background.changeColor(background.red, background.green, progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        textView_Save.setOnClickListener {
            constraintLayout_restoreColor.visibility = View.GONE
            constraintLayout_saveColor.visibility = View.VISIBLE
        }

        button_CancelSave.setOnClickListener {
            constraintLayout_saveColor.visibility = View.GONE
            val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        button_Save.setOnClickListener {
            savedColors.add(SkyColor(editText_save.text.toString(), seekBar_red.progress, seekBar_green.progress, seekBar_blue.progress))
            setSkyColorOptions()
            constraintLayout_saveColor.visibility = View.GONE
            val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        textView_Restore.setOnClickListener {
            constraintLayout_saveColor.visibility = View.GONE
            constraintLayout_restoreColor.visibility = View.VISIBLE
        }

        button_CancelRestore.setOnClickListener {
            constraintLayout_restoreColor.visibility = View.GONE
            val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        button_Restore.setOnClickListener {
            val skyColor = spinner_SelectColor.selectedItem as SkyColor
            seekBar_red.progress = skyColor.Red
            seekBar_green.progress = skyColor.Green
            seekBar_blue.progress = skyColor.Blue
            constraintLayout_restoreColor.visibility = View.GONE
            val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun setSkyColorOptions() {
        val adapter = ArrayAdapter<SkyColor>(this, android.R.layout.simple_spinner_item, savedColors)
        spinner_SelectColor.adapter = adapter
    }
}
