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

        startButton.setOnClickListener{
            val rand = Random().nextInt(seekBar.progress)+1
            result.text = rand.toString()
        }
    }
}