package br.com.riccimac.fincontrol.extensions

fun String.limitUntil(len : Int) : String{
    if(this.length > len){
        return "${this.substring(0,len)}..."
    }
    return this
}