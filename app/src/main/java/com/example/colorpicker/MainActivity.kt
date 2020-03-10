package com.example.colorpicker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var background: ColorPickerCanvas
    private lateinit var savedColors: ArrayList<SkyColor>

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSavedColors()
        setSkyColorOptions()
        background = ColorPickerCanvas(this)
        setInitialColorValues()
        constraintLayout_main.addView(background)
        setupListeners()
    }

    private fun setInitialColorValues() {
        textView_red_value.text = background.red.toString()
        textView_green_value.text = background.green.toString()
        textView_blue_value.text = background.blue.toString()
    }

    private fun loadSavedColors() {
        var file = File(this.baseContext.filesDir, "skyColor")
        if (file.exists()) {
            val json = file.readText()
            val jsonObj = Gson().fromJson<ArrayList<SkyColor>>(json, object: TypeToken<ArrayList<SkyColor>>(){}.type)
            savedColors = jsonObj
        } else {
            savedColors = arrayListOf()
        }
    }

    private fun writeSavedColorsToFile() {
        var file = File(this.baseContext.filesDir, "skyColor")
        if (!file.exists()) {
            file.createNewFile()
        }
        val json = Gson().toJson(savedColors)
        file.writeText(json)
    }

    private fun setupListeners() {
        seekBar_red.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView_red_value.text = progress.toString()
                background.changeColor(progress, background.green, background.blue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBar_green.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView_green_value.text = progress.toString()
                background.changeColor(background.red, progress, background.blue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBar_blue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView_blue_value.text = progress.toString()
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
            writeSavedColorsToFile()
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
            textView_red_value.text = skyColor.Red.toString()
            seekBar_green.progress = skyColor.Green
            textView_green_value.text = skyColor.Green.toString()
            seekBar_blue.progress = skyColor.Blue
            textView_blue_value.text = skyColor.Blue.toString()
            constraintLayout_restoreColor.visibility = View.GONE
            val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

//    private fun restoreSelectedColor() {
//        val skyColor = spinner_SelectColor.selectedItem as SkyColor
//        seekBar_red.progress = skyColor.Red
//        textView_red_value.text = skyColor.Red.toString()
//        seekBar_green.progress = skyColor.Green
//        textView_green_value.text = skyColor.Green.toString()
//        seekBar_blue.progress = skyColor.Blue
//        textView_blue_value.text = skyColor.Blue.toString()
//        constraintLayout_restoreColor.visibility = View.GONE
//        val imm = this.baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(View(this).windowToken, 0)
//    }

    private fun setSkyColorOptions() {
        val adapter = ArrayAdapter<SkyColor>(this, android.R.layout.simple_spinner_item, savedColors)
        spinner_SelectColor.adapter = adapter
    }
}
