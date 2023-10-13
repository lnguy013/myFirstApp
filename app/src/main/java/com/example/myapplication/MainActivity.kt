package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.util.*

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

        roll.setOnClickListener{
            var seekBarStatus = seekBar.progress
            if (seekBarStatus != 0) {
                val rand = Random().nextInt(seekBarStatus) + 1
                result.text = rand.toString()
            }
            else {
                result.text = "Error Can't set to zero"
            }
        }
    }
}