package com.vris.vlikeit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val  binding=findViewById<Button>(R.id.button26)
        val  binding1=findViewById<Button>(R.id.button27)
        val  profil=findViewById<ConstraintLayout>(R.id.constraintLayout23)
        val  oduller=findViewById<ConstraintLayout>(R.id.constraintLayout22)
        binding.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        oduller.setOnClickListener {
            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()
        }
        profil.setOnClickListener {
            val intent = Intent(this, Pofile::class.java)
            startActivity(intent)
            finish()
        }
    }
}