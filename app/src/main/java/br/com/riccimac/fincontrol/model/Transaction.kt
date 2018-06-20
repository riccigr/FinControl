package br.com.riccimac.fincontrol.model

import java.math.BigDecimal
import java.util.*

class Transaction (val value: BigDecimal,
                   val category: String,
                   val date: Calendar)
