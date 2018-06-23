package br.com.riccimac.fincontrol.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun String.limitUntil(len : Int) : String{

    if(this.length > len){
        val firstChar = 0
        return "${this.substring(firstChar,len)}..."
    }
    return this
}

@SuppressLint("SimpleDateFormat")
fun String.parseToCalendar(): Calendar {

    val date = Calendar.getInstance()
    date.time = SimpleDateFormat("dd/MM/yyyy").parse(this)

    return date
}