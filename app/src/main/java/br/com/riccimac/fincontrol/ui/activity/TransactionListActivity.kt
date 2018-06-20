package br.com.riccimac.fincontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.ui.adapter.TransactionListAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class TransactionListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transactions = listOf(
                Transaction( BigDecimal(20.5), "Meal", Calendar.getInstance()),
                Transaction( BigDecimal(101), "Economies", Calendar.getInstance())
                )

        lista_transacoes_listview.adapter = TransactionListAdapter(transactions,this)
    }
}