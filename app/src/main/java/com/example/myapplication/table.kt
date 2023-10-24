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
        val prices = findViewById(R.id.tableMonthly) as TableLayout
        prices.isStretchAllColumns = true
        prices.bringToFront()
        val defaultValue= 0.00
//        val termOfLoan = intent?.getDoubleExtra("terms_of_loan",defaultValue)
//        val intent = activity?.intent
//        val defaultValue = 0.00
        val monthlyPayment = intent?.getDoubleExtra("monthly_payment",defaultValue)
        val interestRate = intent?.getDoubleExtra("interest_rate",defaultValue)
        val termOfLoan = intent?.getDoubleExtra("terms_of_loan",defaultValue)
        val loanValue = intent?.getDoubleExtra("loan_value",defaultValue)
        val loanLength = (termOfLoan?.times(12))?.toInt()
        for (i in 1 ..loanLength!!+1) {
            val tr = TableRow(this)
            val c1 = TextView(this)
            c1.setText(i.toString())
            val c2 = TextView(this)
            c2.setText(loanValue.toString())
            val c3 = TextView(this)
            c3.setText(monthlyPayment.toString())
            tr.addView(c1)
            tr.addView(c2)
            tr.addView(c3)
            prices.addView(tr)
        }
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