package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.AppBarConfiguration.Builder.*
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.myapplication.databinding.ActivityTableBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.String
import java.security.Principal
import kotlin.math.roundToInt

class table : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityTableBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.appBarTable.toolbar)
        binding!!.appBarTable.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawer = binding!!.drawerLayout
        val navigationView = binding!!.navView
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = Builder(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
        )
            .setOpenableLayout(drawer)
            .build()
        val navController = findNavController(this, R.id.nav_host_fragment_content_table)
        setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        setupWithNavController(navigationView, navController)
        drawTable()
    }

    fun drawTable(){
        val priceBreakDown = findViewById(R.id.tableMonthly) as TableLayout
        val tableHeader = findViewById(R.id.tableHeader) as TableLayout

        priceBreakDown.isStretchAllColumns = true
        priceBreakDown.bringToFront()
        tableHeader.isStretchAllColumns = true
        tableHeader.bringToFront()
        val hr = TableRow(this)
        val h1 = TextView(this)
        h1.setText("Month")
        val h2 = TextView(this)
        h2.setText("Balance")
        val h3 = TextView(this)
        h3.setText("Interest")
        val h4 = TextView(this)
        h4.setText("Principal")
        hr.addView(h1)
        hr.addView(h2)
        hr.addView(h3)
        hr.addView(h4)
        tableHeader.addView(hr)

        val defaultValue= 0.00
//        val termOfLoan = intent?.getDoubleExtra("terms_of_loan",defaultValue)
//        val intent = activity?.intent
//        val defaultValue = 0.00
        val monthlyPayment = intent?.getDoubleExtra("monthly_payment",defaultValue)
        val interestRate = intent?.getDoubleExtra("interest_rate",defaultValue)
        val termOfLoan = intent?.getDoubleExtra("terms_of_loan",defaultValue)
        val loanValue = intent?.getDoubleExtra("loan_value",defaultValue)
        var balanceValue = loanValue
        var interestValue = 0.00
        var principalValue = 0.00
        val loanLength = (termOfLoan?.times(12))?.toInt()
        for (i in 1 ..loanLength!!) {
            val tr = TableRow(this)
            val monthly = TextView(this)
            val principal = TextView(this)
            val interest = TextView(this)
            val balance = TextView(this)

            //get interest
            if (interestRate != null) {
                interestValue = (((interestRate/1200)* balanceValue!!)*100.0).roundToInt()/100.0
            }

            //get principal
            if (interestRate != null) {
                if (monthlyPayment != null) {
                    principalValue = ((monthlyPayment-interestValue)*100.0).roundToInt()/100.0
                }
            }

            //get monthly Balance
            if(i >1 ){
                if (monthlyPayment != null) {
                    if (balanceValue != null) {
                        balance.setText(balanceValue.toString())
                        balanceValue = getMonthlyBalance(balanceValue,principalValue)
                    }
                }
            }else{
                balance.setText(balanceValue.toString())
                balanceValue = balanceValue?.let { getMonthlyBalance(it,principalValue) }
            }
            monthly.setText(i.toString())
            //balance.setText(balanceValue.toString())
            principal.setText(principalValue.toString())
            interest.setText(interestValue.toString())
            tr.addView(monthly)
            tr.addView(balance)
            tr.addView(interest)
            tr.addView(principal)
            priceBreakDown.addView(tr)
        }
    }

    fun getMonthlyBalance(loanValue: Double, principalValue:  Double):Double{
        return ((loanValue-principalValue)*100.0).roundToInt()/100.0
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.table, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment_content_table)
        return (navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }
}