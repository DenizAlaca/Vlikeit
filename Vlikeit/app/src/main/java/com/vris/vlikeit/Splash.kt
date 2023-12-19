package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import java.sql.Date
import java.sql.PreparedStatement
import java.text.SimpleDateFormat

class Splash : AppCompatActivity() {
    private var connectSql = ConnectSql()
    lateinit var userlogin: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
        var log=userlogin.getString("ulog","")
        if (log!=null) {

            val login: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select email_address,userName,password,ltc_address,dogrulama_kodu,id from Kullanici where email_address='" + log.toString() + "'")!!


            val rs = login.executeQuery()
            while (rs.next()) {


                val editor2: SharedPreferences.Editor = userlogin.edit()
                editor2.putString("nameuser1", rs.getString(2))

                editor2.apply()
                val editor3: SharedPreferences.Editor = userlogin.edit()
                editor3.putString("passworduser", rs.getString(3))

                editor3.apply()
                val editor4: SharedPreferences.Editor = userlogin.edit()
                editor4.putString("ltcadresi", rs.getString(4))

                editor4.apply()


                val editor: SharedPreferences.Editor = userlogin.edit()
                editor.putString("dogrulamakod", rs.getString(5))

                editor.apply()
                val editor1: SharedPreferences.Editor = userlogin.edit()

                editor1.putString("mailal", rs.getString(1))
                editor1.apply()
                val editor6: SharedPreferences.Editor = userlogin.edit()

                editor6.putInt("idKullanici", rs.getInt(6))
                editor6.apply()
            }

         try {
             val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
             val currentDate = sdf.format(java.util.Date())
             val logindate1:PreparedStatement=connectSql.dbConn()?.prepareStatement("declare @id int set @id=(select id from Kullanici where email_address='"+log.toString()+"') update  lastlogin set lastlogindate='"+currentDate.toString()+"' where id=@id")!!
             logindate1.executeUpdate()

         }
         catch (e:Exception)
         {
          Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
         }


        }
        Handler().postDelayed({
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()

        },1000)


    }

}