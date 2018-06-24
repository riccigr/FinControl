package br.com.riccimac.fincontrol.dao

import br.com.riccimac.fincontrol.model.Transaction

class TransactionDAO {

    val transactions: List<Transaction> = Companion.transactions

    companion object {
        private val transactions : MutableList<Transaction> = mutableListOf()
    }


    fun add(transaction: Transaction) {
        Companion.transactions.add(transaction)
    }

    fun update(transaction: Transaction, position: Int) {
        Companion.transactions[position] = transaction
    }

    fun remove(position: Int){
        Companion.transactions.removeAt(position)
    }

}