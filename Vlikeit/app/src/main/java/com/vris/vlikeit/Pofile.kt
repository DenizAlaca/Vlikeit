package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.sql.PreparedStatement
import kotlin.reflect.typeOf

class Pofile : AppCompatActivity() {

    private var connectSql = ConnectSql()
    lateinit var userlogin: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pofile)

        // vris main yapıldığında hesabın doğrulanma bilgisi oradan gelecek

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
        var log=userlogin.getString("ulog","")
        var kontroldeger=userlogin.getString("dogruladurum","")
        val kapat:Button=findViewById(R.id.button18)
        val uyari:EditText=findViewById(R.id.editTextTextPersonName12)
        val idtext:TextView=findViewById(R.id.textView80)
        idtext.setText(userlogin.getInt("idKullanici",0).toString())

        if (kontroldeger=="1")
        {
            uyari.setText("Hesabın doğrulandı.")
            kapat.isEnabled=false
            uyari.isEnabled=false
        }

        val save: Button =findViewById<Button>(R.id.button6)
        val mail:TextView=findViewById<TextView>(R.id.mail)
        val name:TextView=findViewById<TextView>(R.id.kullaniciadi)
        val password:EditText=findViewById<EditText>(R.id.editTextTextPersonName8)
        val ltcaddress:EditText=findViewById<EditText>(R.id.editTextTextPersonName9)
        val show: Button=findViewById(R.id.button34)
        val bizeulas:Button=findViewById(R.id.button49)
        val steamurlgonder:Button=findViewById(R.id.button50)

        steamurlgonder.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://steamcommunity.com/my/tradeoffers/privacy"))
            startActivity(browserIntent);
        }

        bizeulas.setOnClickListener {
          /*  val emailIntent=Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","kimappinfo@gmail.com",null)
            )
            startActivity(Intent.createChooser(emailIntent,"Send Email..."))*/
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:kimappinfo@gmail.com"))
            startActivity(intent)
            finish()
        }



        mail.setText(userlogin.getString("mailal", ""))
        name.setText(userlogin.getString("nameuser1", ""))
        password.setText(userlogin.getString("passworduser", ""))
        ltcaddress.setText(userlogin.getString("ltcadresi", ""))


        var kntshow=0
        show.setOnClickListener {
            if(kntshow==0)
            {
                password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                kntshow++
            }
            else if(kntshow==1)
            {   password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            kntshow--}
        }





        save.setOnClickListener {

            var ltc = ltcaddress.text
            var pass = password.text

            val chkuser: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("update Kullanici set ltc_address='" + ltc + "'  where email_address='" + mail.text + "'")!!

            val chkuser2: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("update Kullanici set password='" + pass + "'  where email_address='" + mail.text + "'")!!

            chkuser.executeUpdate()
            chkuser2.executeUpdate()
            val editor3: SharedPreferences.Editor = userlogin.edit()
            editor3.putString("passworduser", pass.toString())

            editor3.apply()
            val editor4: SharedPreferences.Editor = userlogin.edit()
            editor4.putString("ltcadresi", ltc.toString())

            editor4.apply()
            Toast.makeText(this, "Değişiklikler kaydedildi", Toast.LENGTH_SHORT).show()

        }
        //val save: Button =findViewById<Button>(R.id.button10)
        val main: ConstraintLayout =findViewById<ConstraintLayout>(R.id.constraintLayout21)
        val odul: ConstraintLayout =findViewById<ConstraintLayout>(R.id.constraintLayout20)
        val dogru:Button=findViewById(R.id.button18)
        dogru.setOnClickListener {
            val login: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select email_address from Kullanici where dogrulama_kodu='"+uyari.text.toString()+"'")!!


            val rs = login.executeQuery()
            if (rs.next())
            {
var gelenkod=uyari.text.toString()
                    val chkuser2: PreparedStatement = connectSql.dbConn()
                        ?.prepareStatement("update Kullanici set dogrulama_kontrol=" + 1 + "  where email_address='" + mail.text.toString() + "' and dogrulama_kodu='" + gelenkod + "'")!!

                    chkuser2.executeUpdate()
                    val editor: SharedPreferences.Editor = userlogin.edit()
                    editor.putString("dogruladurum", "1")
                    editor.apply()
                uyari.setText("Hesabınız doğrulandı.")
                kapat.isEnabled=false
                Toast.makeText(this, "Hesabın doğrulandı.", Toast.LENGTH_SHORT).show()

            }        else { Toast.makeText(this, "Hatalı giriş yaptın.", Toast.LENGTH_SHORT).show()}


        }

        main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        odul.setOnClickListener {
            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()
        }
        val dogrla:Button=findViewById(R.id.button17)
        dogrla.setOnClickListener {
            val login: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select dogrulama_kontrol from Kullanici where email_address='"+log.toString()+"'")!!


            val rs = login.executeQuery()
            while (rs.next())
            {
            if (rs.getBoolean(1)==true)
            {
                Toast.makeText(this, "Hesabın doğrulanmış durumda.", Toast.LENGTH_LONG).show()
            }
                else
            {
                val intent = Intent(this, Dogrula::class.java)
                startActivity(intent)
                finish()
            }
            }

        }

    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}