package com.vris.vlikeit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.sql.PreparedStatement

class FreecashGetiri : AppCompatActivity() {
    private var connectSql = ConnectSql()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freecash_getiri)


        val anladim=findViewById<Button>(R.id.button40)
        val getiriokx=findViewById<TextView>(R.id.textView51)
        val idchk: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select gelir from GelirGider where teklif_adı='Freecash'")!!

        val rs = idchk.executeQuery()
        while (rs.next())
        {
            getiriokx.setText(rs.getString(1).toString())
            break
        }
        anladim.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}