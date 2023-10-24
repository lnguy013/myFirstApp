package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.home.HomeFragment
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
        val nextButton = findViewById<Button>(R.id.mortgageNextButton)
        val intentGoToTable = Intent(this@MortgageActivity, table::class.java)

        signButton.setOnClickListener{
            val intent = Intent(this@MortgageActivity, MainActivity::class.java)
            startActivity(intent)
        }

        nextButton.setOnClickListener{
            try {

                startActivity(intentGoToTable)
            }
            catch(errorMsg: Exception){
                inputHomeValue.setText("Error Please Don't leave this empty")
            }

        }

        calculateButton.setOnClickListener{
            try {
                val purchasePrice: Double = inputHomeValue.text.toString().toDouble()
                val percentDown: Double = inputPercentDown.text.toString().toDouble()
                val amount = (((percentDown)/(100))*purchasePrice)
                inputDownPayment.setText(amount.toString())

                val interestValue: Double = inputInterestRate.text.toString().toDouble()
                val termLoanValue: Double = inputTermsOfLoan.text.toString().toDouble()

                val monthPaymentValue = (calculateMortgage(interestValue,termLoanValue,amount,purchasePrice)*100.0).roundToInt()/100.0
                val totalLoanInterest =  (calculateInterest(interestValue,termLoanValue,amount,purchasePrice)*100.0).roundToInt()/100.0

                resultMonthlyPayment.setText(monthPaymentValue.toString())
                resultInterest.setText(totalLoanInterest.toString())


                intentGoToTable.putExtra("monthly_payment", resultMonthlyPayment.text.toString().toDouble())
                intentGoToTable.putExtra("terms_of_loan", inputTermsOfLoan.text.toString().toDouble())
                intentGoToTable.putExtra("interest_rate",inputInterestRate.text.toString().toDouble())
                intentGoToTable.putExtra("loan_value",inputHomeValue.text.toString().toDouble())
                intentGoToTable.putExtra("total_interest",resultInterest.text.toString().toDouble())

            }
            catch(errorMsg: Exception){
                inputHomeValue.setText("Error Please Don't leave this empty")
            }
        }
    }

    private fun calculateMortgage(
        interestRate: Double,
        termsOfLoan: Double,
        downPaymentValue: Double,
        loanValue: Double
    ): Double {

//        this function will calculate the monthly payment for the loan
//        Return value to the loan in Double up to 2 decimals place
//        round up
        val loanAmount = (loanValue - downPaymentValue)
        val convertTermsToMonths = (termsOfLoan * 12)
        val annualInterestMod = ((interestRate/100) / 12)
        val exponentialValue = (1 +annualInterestMod).pow(convertTermsToMonths)
        val factor =
            (exponentialValue- 1) / (annualInterestMod * exponentialValue)

        return (loanAmount / factor)

    }

    private fun calculateInterest (
        interestRate: Double,
        termsOfLoan: Double,
        downPaymentValue: Double,
        loanValue: Double):
            Double {
        val loanLength =  (termsOfLoan * 12).toInt()
        var totalInterest = 0.00
        val loanAmount = (loanValue - downPaymentValue)
        val annualInterestMod = ((interestRate/100) / 12)
        var loanAmountTemp = loanAmount
        val fixMonthlyPayment = calculateMortgage(interestRate,termsOfLoan,downPaymentValue,loanValue)
        var monthlyInterest : Double
        for (x in 1..loanLength ){
            monthlyInterest = loanAmountTemp*annualInterestMod
            totalInterest = (totalInterest+monthlyInterest)
            loanAmountTemp=(loanAmountTemp-(fixMonthlyPayment-monthlyInterest))
        }

        return totalInterest
    }
}