package com.vris.vlikeit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.sql.PreparedStatement

class BitpandaGoturu : AppCompatActivity() {
    private var connectSql = ConnectSql()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitpanda_goturu)

        val anladim=findViewById<Button>(R.id.button46)
        val  gider=findViewById<TextView>(R.id.textView63)

        val idchk: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select gider from GelirGider where teklif_adı='Bitpanda'")!!

        val rs = idchk.executeQuery()
        while (rs.next())
        {
            gider.setText(rs.getString(1).toString())
            break
        }

        anladim.setOnClickListener {
            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}