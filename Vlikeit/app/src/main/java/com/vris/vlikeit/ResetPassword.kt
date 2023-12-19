package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.sql.PreparedStatement

class ResetPassword : AppCompatActivity() {
    private var connectSql = ConnectSql()
    lateinit var userlogin: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
        //var passwordreset1=userlogin.getString("passwordreset","")
        val resetbutton:Button=findViewById(R.id.sifirlabtn)
        val mail:EditText=findViewById(R.id.editTextTextPersonName10)
        val geridonbutton:Button=findViewById(R.id.button8)
        resetbutton.setOnClickListener {


            val login: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select password,email_address from Kullanici where email_address='"+mail.text.toString()+"'")!!


            val rs = login.executeQuery()
            while (rs.next())
            {
                val editor: SharedPreferences.Editor = userlogin.edit()
                editor.putString("passwordreset", rs.getString(1))
                editor.putString("resetemanil",rs.getString(2))
                editor.apply()
               val intent = Intent(this, Forgotpassword::class.java)
                startActivity(intent)
                finish()

            }



        }

        geridonbutton.setOnClickListener{
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}