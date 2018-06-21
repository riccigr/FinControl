package br.com.riccimac.fincontrol.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatToBrazillianStandard() : String {
    return SimpleDateFormat("dd/MM/yyyy").format(this.time)

}