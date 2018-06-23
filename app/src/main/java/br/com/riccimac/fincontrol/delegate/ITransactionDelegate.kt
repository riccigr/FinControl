package br.com.riccimac.fincontrol.delegate

import br.com.riccimac.fincontrol.model.Transaction

interface ITransactionDelegate {

    fun delegate(transaction: Transaction)
}