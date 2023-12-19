package com.vris.vlikeit

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import java.sql.PreparedStatement

class Oduller2 : AppCompatActivity() {

    lateinit var  frecashdrm:String
    private var connectSql = ConnectSql()
    lateinit var userlogin: SharedPreferences
    lateinit var id :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oduller2)


        val progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Yükleniyor...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        Handler().postDelayed({
            countcek("GiftOKX",1)
            //countcek("GiftBitpanda",2)
            userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
            var log=userlogin.getString("ulog","")
            val idchk: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select id from Kullanici where email_address='" + log + "'")!!

            val rs = idchk.executeQuery()
            while (rs.next())
            {
                id=rs.getInt(1).toString()
                break
            }

            progressDialog.dismiss()

        },20)

        val odul3:Button=findViewById(R.id.button10)
        odul3.setOnClickListener {
            var onaykont1:Int=0
            val odulchkfrea: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select kontrol from GiftOKX where uid=0")!!

            val odulfr = odulchkfrea.executeQuery()
            while (odulfr.next())
            {
                var durumfrea=odulfr.getBoolean(1)
                if (durumfrea!=true)
                {
                    Toast.makeText(this,"Çekiliş aktif durumda değil.", Toast.LENGTH_SHORT).show()
                }
                else{
                    val odulchk1: PreparedStatement = connectSql.dbConn()
                        ?.prepareStatement("select uid from GiftOKX where uid=" + id + "")!!

                    val odul = odulchk1.executeQuery()
                    if (odul.next())
                    { Toast.makeText(this,"Çekilişe katılmış durumdasın.", Toast.LENGTH_SHORT).show()
                        /*if (odul.getInt(1)==null)
                        {

                        }*/


                    } else
                    {

                        val freacashchkekle: PreparedStatement = connectSql.dbConn()
                            ?.prepareStatement("insert into GiftOKX values(" + id.toInt() + ",0)")!!

                        freacashchkekle.executeUpdate()
                        Toast.makeText(this,"Çekilişe katıldın.", Toast.LENGTH_SHORT).show()
                        onaykont1+=8

                    }

                }

            }
        }


        val  binding=findViewById<Button>(R.id.button32)
        val  binding1=findViewById<Button>(R.id.button33)
        val  profil=findViewById<ConstraintLayout>(R.id.constraintLayout26)
        val  main=findViewById<ConstraintLayout>(R.id.constraintLayout27)
        binding.setOnClickListener {
            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()
        }
        binding1.setOnClickListener {
            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()
        }
        profil.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        main.setOnClickListener {
            val intent = Intent(this, Pofile::class.java)
            startActivity(intent)
            finish()
        }

        val okxgoturu=findViewById<Button>(R.id.button44)

        okxgoturu.setOnClickListener {
            val intent = Intent(this, OkxGoturu::class.java)
            startActivity(intent)
            finish()
        }



    }
    public fun countcek( gel:String,sec:Int) {
        val countfrea: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select count(*) from "+gel.toString()+" ")!!

        val countf = countfrea.executeQuery()
        val progres1: ProgressBar =findViewById(R.id.progressBar)
        val progres2: ProgressBar =findViewById(R.id.progressBar4)
        progres1.max=10
        progres2.max=10
        while (countf.next())
        {

            if (sec==1)
            {
                progres1.setProgress(countf.getInt(1))
                val textView: TextView = findViewById(R.id.textView2)
                textView.setText(countf.getInt(1).toString()+"/10")
            }
            if (sec==2)
            {
                progres2.setProgress(countf.getInt(1))
                val textView: TextView = findViewById(R.id.textView18)
                textView.setText(countf.getInt(1).toString()+"/10")
            }


            break
        }
    }
}