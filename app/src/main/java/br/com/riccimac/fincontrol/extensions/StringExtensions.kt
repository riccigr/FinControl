package br.com.riccimac.fincontrol.extensions

fun String.limitUntil(len : Int) : String{

    if(this.length > len){
        val firstChar = 0
        return "${this.substring(firstChar,len)}..."
    }
    return this
}