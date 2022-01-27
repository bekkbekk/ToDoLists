package com.example.todolists

data class ToDo (
    val id: Int,
    val title: String,
    var isChecked: Boolean = false
) {

}