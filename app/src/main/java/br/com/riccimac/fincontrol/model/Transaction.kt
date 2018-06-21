package br.com.riccimac.fincontrol.model

import java.math.BigDecimal
import java.util.*

class Transaction (val value: BigDecimal,
                   val category: String = "Undefined",
                   val type: Type,
                   val date: Calendar = Calendar.getInstance())
