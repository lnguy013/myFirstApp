package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.util.*
import com.example.myapplication.Home


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)
        val welcomeMSG = findViewById<TextView>(R.id.welcomeMSG)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val result = findViewById<TextView>(R.id.result)
        val roll = findViewById<Button>(R.id.rollButton)
        val rollDescription = findViewById<TextView>(R.id.randomTextDescription)
//        val zeroError: String = getString(R.string.zero_error)
//        val oneError: String = getString(R.string.one_error)
//        roll button function
        roll.setOnClickListener{
            var seekBarStatus = seekBar.progress
            try{
                if (seekBarStatus != 1) {
                    val rand = Random().nextInt(seekBarStatus) + 1
                    result.text = rand.toString()
                }
                else {
                    result.text = getString(R.string.one_error)
                }
            }
            catch(e: Exception) {
                result.text = getString(R.string.zero_error)
            }
        }

        //start button open new page
        startButton.setOnClickListener{

        }
    }
}