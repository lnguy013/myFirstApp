package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlin.math.pow
import kotlin.math.roundToInt


class MortgageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mortage)

        val signButton = findViewById<Button>(R.id.signOut)
        val inputPercentDown = findViewById<EditText>(R.id.inputPercentDown)
        val inputHomeValue = findViewById<EditText>(R.id.inputHomePrice)
        val inputInterestRate = findViewById<EditText>(R.id.inputInterestRate)
        val inputTermsOfLoan = findViewById<EditText>(R.id.inputLoanTerm)
        val calculateButton = findViewById<Button>(R.id.calculateBttn)
        val resultInterest = findViewById<EditText>(R.id.resultTotalInterest)
        val inputDownPayment = findViewById<EditText>(R.id.inputDownPayment)
        val resultMonthlyPayment = findViewById<EditText>(R.id.resultMontlyPayment)

        signButton.setOnClickListener{
            val intent = Intent(this@MortgageActivity, MainActivity::class.java)
            startActivity(intent)
        }

        calculateButton.setOnClickListener{

           if(inputHomeValue.text.trim().isNotEmpty() || inputHomeValue.text.trim().isNotBlank()){
               if(inputHomeValue.text.trim().isDigitsOnly()){

                   val purchasePrice: Double = inputHomeValue.text.toString().toDouble()
                   val percentDown: Double = inputPercentDown.text.toString().toDouble()
                   val amount = (((percentDown)/(100))*purchasePrice)
                   inputDownPayment.setText(amount.toString())

                   val interestValue: Double = inputInterestRate.text.toString().toDouble()
                   val termLoanValue: Double = inputTermsOfLoan.text.toString().toDouble()

                   val monthPaymentValue = (mortgageCalculate(interestValue,termLoanValue,amount,purchasePrice)*100.0).roundToInt()/100.0

                   resultMonthlyPayment.setText(monthPaymentValue.toString())
               }else{
                   inputHomeValue.setText("Error Please Don't leave this empty")
               }
           }else{
                inputHomeValue.setText("Please Don't leave this empty")
           }


        }
    }

    private fun mortgageCalculate(
        interestRate: Double,
        termsOfLoan: Double,
        downPaymentValue: Double,
        homePriceValue: Double
    ): Double {

//        this function will calculate the monthly payment for the loan
//        Return value to the loan in Double up to 2 decimals place
//        round up
        val loanAmount = (homePriceValue - downPaymentValue)
        val convertTermsToMonths = (termsOfLoan * 12)
        val annualInterestMod = ((interestRate/100) / 12)
        val factor =
            ((((1 + annualInterestMod).pow(convertTermsToMonths)) - 1) / ((annualInterestMod) * ((1 + annualInterestMod).pow(
                convertTermsToMonths
            ))))

        return (loanAmount / factor)

    }
}