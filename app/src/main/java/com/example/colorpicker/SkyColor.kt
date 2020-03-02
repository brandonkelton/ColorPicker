package com.example.colorpicker

class SkyColor {
    var Name: String = ""
    var Red: Int = 128
    var Green: Int = 128
    var Blue: Int = 128

    constructor(name: String, red: Int, green: Int, blue: Int) {
        Name = name
        Red = red
        Green = green
        Blue = blue
    }

    override fun toString(): String {
        return Name
    }
}