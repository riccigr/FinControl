package br.com.riccimac.fincontrol.model

import java.math.BigDecimal
import java.util.*

class Transaction (val value: BigDecimal,
                   val category: String = "Indefinida",
                   val type: Type,
                   val createDate: Calendar = Calendar.getInstance())
