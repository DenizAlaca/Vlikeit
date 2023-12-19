package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OkxDetay : AppCompatActivity() {


    lateinit var userlogin: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okx_detay)

        userlogin = getSharedPreferences("userlog", Context.MODE_PRIVATE)
        val buton = findViewById<Button>(R.id.button21)
        val okxyonlendirmesi=findViewById<Button>(R.id.okxYonlendirme)

        okxyonlendirmesi.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.okx.com/join/56370804"))
            startActivity(browserIntent);
        }

        buton.setOnClickListener {
            val editor: SharedPreferences.Editor = userlogin.edit()
            editor.putString("OKXPOPUP", "okudu")
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