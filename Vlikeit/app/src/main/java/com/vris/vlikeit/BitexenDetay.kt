package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BitexenDetay : AppCompatActivity() {
    lateinit var userlogin:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitexen_detay)

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
        val buton=findViewById<Button>(R.id.button21)
        buton.setOnClickListener {
            val editor: SharedPreferences.Editor = userlogin.edit()
            editor.putString("BITEXENPOPUP","okudu")
            editor.apply()
            val intent = Intent(this, MailUpload::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}