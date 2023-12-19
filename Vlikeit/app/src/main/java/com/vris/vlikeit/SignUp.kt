package com.vris.vlikeit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import java.util.*

class SignUp : AppCompatActivity() {


    private var connectSql = ConnectSql()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

       val btn=findViewById<Button>(R.id.signupbtn)

        val email=findViewById<EditText>(R.id.editTextTextPersonName)
        val uname=findViewById<EditText>(R.id.editTextTextPersonName2)
        val password=findViewById<EditText>(R.id.editTextTextPersonName3)
        val ltclink=findViewById<EditText>(R.id.editTextTextPersonName4)

        val layout:ConstraintLayout=findViewById(R.id.constraintLayout119)
        val airdrop=findViewById<View>(R.id.airdropView5)
        val ptext:TextView=findViewById(R.id.textView14)
        val txt7=findViewById<TextView>(R.id.textView7)
        val steam:Button=findViewById(R.id.steamButton)





        steam.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://steamcommunity.com/my/tradeoffers/privacy"))
            startActivity(browserIntent);
        }


        ptext.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@SignUp, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottomsheet_fragment2,
                findViewById<ConstraintLayout>(R.id.background)
            )
            bottomSheetDialog.dismiss()
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetDialog.show()


            //bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")

          /*  val dialogBuilder:MaterialAlertDialogBuilder
            val dialog :AlertDialog
            dialogBuilder= AlertDialog.Builder(this) as MaterialAlertDialogBuilder
            dialogBuilder.setView(view)
            dialog= dialogBuilder.create()
            dialog.show()*/


        }
        val privacy:CheckBox=findViewById(R.id.checkBox3)



        val geridon:Button=findViewById(R.id.button13)
        geridon.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        }

btn.setOnClickListener {
    if (email.text.toString()==""|| uname.text.toString()==""||password.text.toString()==""||ltclink.text.toString()=="")
    {
        Toast.makeText(this, "Tüm bilgilerinizi doldurup tekrar deneyin.", Toast.LENGTH_LONG).show()
    }


    else if (ltclink==null || password==null|| uname==null|| email==null)
    {
//uyarı çıkart
    }

    else if (password.text.count()<8)
    {
        Toast.makeText(this, "Minimum 8 karakterli bir şifre olmalı.", Toast.LENGTH_LONG).show()
    }
    else if(privacy.isChecked==false)
    {
        Toast.makeText(this, "Kullanıcı sözleşmesini kabul etmelisiniz.", Toast.LENGTH_LONG).show()
    }
    else {
        var chkUsr:Boolean=false
        var startvalue = 0
        var counter = 1
        var specialchar = 0
        var dot = 0
        var allspecialchar = 0
        var trchar = 0
        var endvalue = email.length()
        var str = email.text.toString()
        while (startvalue != endvalue) {


            if (str.substring(startvalue, counter) == "@") {
                specialchar++
            }
            if (str.substring(startvalue, counter) == ".") {
                dot++
            }
            if (str.substring(startvalue, counter) == "İ" || str.substring(
                    startvalue,
                    counter
                ) == "ç" || str.substring(startvalue, counter) == "Ç" || str.substring(
                    startvalue,
                    counter
                ) == "ı" || str.substring(startvalue, counter) == "Ö" || str.substring(
                    startvalue,
                    counter
                ) == "ğ" || str.substring(startvalue, counter) == "Ğ" || str.substring(
                    startvalue,
                    counter
                ) == "Ö" || str.substring(startvalue, counter) == "ö" || str.substring(
                    startvalue,
                    counter
                ) == "Ü" || str.substring(startvalue, counter) == "ü" || str.substring(
                    startvalue,
                    counter
                ) == "Ş" || str.substring(startvalue, counter) == "ş"
            ) {
                trchar++; }
            if (str.substring(startvalue, counter) == "*" || str.substring(
                    startvalue,
                    counter
                ) == "/" || str.substring(startvalue, counter) == "-" || str.substring(
                    startvalue,
                    counter
                ) == "+" || str.substring(startvalue, counter) == "!" || str.substring(
                    startvalue,
                    counter
                ) == "'" || str.substring(startvalue, counter) == "$" || str.substring(
                    startvalue,
                    counter
                ) == "%" || str.substring(startvalue, counter) == "#" || str.substring(
                    startvalue,
                    counter
                ) == "^" || str.substring(startvalue, counter) == "&" || str.substring(
                    startvalue,
                    counter
                ) == "(" || str.substring(startvalue, counter) == ")"
            ) {
                allspecialchar++; }
            startvalue = counter
            counter++
        }
        if (trchar == 0 && allspecialchar == 0 && dot >= 1 && specialchar == 1)
        {
        val chkuser: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select email_address from Kullanici where email_address='" + email.text.toString() + "'")!!

        val rs = chkuser.executeQuery()
        while (rs.next()) {
           if (rs.getString(1) !=email.text.toString())
           {
               chkUsr = false
           }
            else{
                chkUsr = true
            }
        }
            if (chkUsr==true)
            {
                Toast.makeText(this, "Bu Mail Adresi ile kayıt bulunmaktadır", Toast.LENGTH_LONG).show()

            }


        if (chkUsr == false) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val usuario: PreparedStatement =
                connectSql.dbConn()?.prepareStatement("insert into Kullanici values (?,?,?,?,?,?,?)")!!
            usuario.setString(1, email.text.toString())
            usuario.setString(2, uname.text.toString())
            usuario.setString(3, password.text.toString())
            usuario.setString(4, ltclink.text.toString())
            usuario.setString(5, currentDate.toString())
            usuario.setString(6,(10000 until 1000000).random().toString())
            usuario.setBoolean(7,false)

            usuario.executeUpdate()
            val logindate1:PreparedStatement=connectSql.dbConn()?.prepareStatement("declare @id int set @id=(select id from Kullanici where email_address='"+email.text+"') insert into lastlogin values(@id,'"+currentDate.toString()+"') ")!!
            logindate1.executeUpdate()

            Toast.makeText(this, "Aramıza hoşgeldin!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()

        }
    }
        else
        {Toast.makeText(this, "Geçerli bir mail adresi girin.", Toast.LENGTH_LONG).show()}
    }

}
    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }

}