package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import java.util.*

class LogIn : AppCompatActivity() {
    private var connectSql = ConnectSql()
    lateinit var  userlogin: SharedPreferences
    lateinit var chkuser:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        userlogin=getSharedPreferences("userlog",Context.MODE_PRIVATE)
        var log=userlogin.getString("ulog","")

        var ogren=userlogin.getString("ogren","")
        if (log!="")
        {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val kayit:Button=findViewById(R.id.button14)
        kayit.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
        val bizeulas:Button=findViewById(R.id.button30)
        bizeulas.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:kimappinfo@gmail.com"))
            startActivity(intent)
            finish()
        }



        val email=findViewById<EditText>(R.id.editTextTextPersonName5)
        val password=findViewById<EditText>(R.id.editTextTextPersonName6)
        val loginbtn=findViewById<Button>(R.id.button2)
        loginbtn.setOnClickListener {


            if(email.text.toString()==""|| password.text.toString()=="")
            {

                Toast.makeText(this, "Lütfen bilgilerini eksiksiz gir.", Toast.LENGTH_SHORT).show()
            }
            else {
                var test:Int=0
                val login: PreparedStatement = connectSql.dbConn()
                    ?.prepareStatement("select email_address from Kullanici where email_address='" + email.text.toString() + "' and password='" + password.text.toString() + "'")!!

                val rs = login.executeQuery()
                while (rs.next()) {
                    chkuser = rs.getString(1)

                    if (rs.getString(1) != null) {
                        val editor: SharedPreferences.Editor = userlogin.edit()
                        editor.putString("ulog", chkuser)
                        editor.apply()

                        val login1: PreparedStatement = connectSql.dbConn()
                            ?.prepareStatement("select email_address,userName,password,ltc_address,dogrulama_kodu,id from Kullanici where email_address='" + email.text.toString()+ "'")!!


                        val rs1 = login1.executeQuery()
                        while (rs1.next()) {


                            val editor2: SharedPreferences.Editor = userlogin.edit()
                            editor2.putString("nameuser1", rs1.getString(2))

                            editor2.apply()
                            val editor3: SharedPreferences.Editor = userlogin.edit()
                            editor3.putString("passworduser", rs1.getString(3))

                            editor3.apply()
                            val editor4: SharedPreferences.Editor = userlogin.edit()
                            editor4.putString("ltcadresi", rs1.getString(4))

                            editor4.apply()


                            val editor5: SharedPreferences.Editor = userlogin.edit()
                            editor5.putString("dogrulamakod", rs1.getString(5))

                            editor5.apply()
                            val editor1: SharedPreferences.Editor = userlogin.edit()

                            editor1.putString("mailal", rs1.getString(1))
                            editor1.apply()
                            val editor7: SharedPreferences.Editor = userlogin.edit()

                            editor7.putInt("idKullanici", rs1.getInt(6))
                            editor7.apply()
                        }
                        test++

                        if (ogren!="1")
                        {
                            val intent = Intent(this, Starter::class.java)
                            startActivity(intent)

                            finish()}
                        else if (ogren=="1")
                        {

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                            finish()
                        }

                    }


                }
                if(test==0){

                    Toast.makeText(
                        this,
                        "Lütfen bilgilerini doğru gir.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
        val sifremiunuttum=findViewById<TextView>(R.id.textView5)
        sifremiunuttum.setOnClickListener {
            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}