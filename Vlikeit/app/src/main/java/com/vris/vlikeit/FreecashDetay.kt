package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FreecashDetay : AppCompatActivity() {
    lateinit var userlogin:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freecash_detay)
        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
        val buton=findViewById<Button>(R.id.button12)
        val freecashyonlendirmesi=findViewById<Button>(R.id.freecashYonlendirme)

        freecashyonlendirmesi.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://freecash.com/r/vlikeit"))
            startActivity(browserIntent);
        }

        buton.setOnClickListener {
            val editor: SharedPreferences.Editor = userlogin.edit()
            editor.putString("FREAPOPUP", "okudu")
            editor.apply()

            val intent = Intent(this, ImageUpload::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}