package br.com.riccimac.fincontrol.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Calendar.formatToBrazillianStandard() : String {
    return SimpleDateFormat("dd/MM/yyyy").format(this.time)
}