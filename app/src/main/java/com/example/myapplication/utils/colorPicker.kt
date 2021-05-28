package com.example.myapplication.utils

object colorPicker {
    var color = arrayOf(
        "#3E89DF",
        "#3685BC",
        "#D36280",
        "#E44F55",
        "#FA8056",
        "#818BCA",
        "#7D659F",
        "#51BAB3",
        "#4FB66C",
        "#E3AD17",
        "#627991",
        "#EFBEAD",
        "#B5BFC6"
    )
    var currentColorIndex = 0

    fun getColor(): String {
        currentColorIndex = (currentColorIndex + 1) % color.size
        return color[currentColorIndex]
    }
}