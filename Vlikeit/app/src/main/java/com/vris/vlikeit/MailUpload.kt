package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.set
import androidx.core.widget.doAfterTextChanged
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import java.util.*

class MailUpload : AppCompatActivity() {

    private var connectSql = ConnectSql()
    lateinit var userlogin: SharedPreferences
    lateinit var iduser:String

    private val GameID = "3716735"
    private val bannerPlacement = "Banner_Android"
    private val interPlacement = "interstitial"
    private val rewardedPlacement = "Rewarded_Ad"
    private val testMode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail_upload)


        UnityAds.initialize(this, GameID, testMode)
        if (  UnityAds.initialize(this, GameID, testMode)!=null )
        {
            UnityAds.initialize(this, GameID, testMode)

            UnityAds.show(this@MailUpload, rewardedPlacement)
        }
        val interListner: IUnityAdsListener = object : IUnityAdsListener {
            override fun onUnityAdsReady(s: String) {}
            override fun onUnityAdsStart(s: String) {}
            override fun onUnityAdsFinish(s: String, finishState: UnityAds.FinishState) {}
            override fun onUnityAdsError(unityAdsError: UnityAds.UnityAdsError, s: String) {}
        }
        UnityAds.setListener(interListner)
        UnityAds.load(interPlacement)

        UnityAds.initialize(this, GameID, testMode)

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
        var log=userlogin.getString("ulog","")
        val idchk: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select id from Kullanici where email_address='" + log + "'")!!

        val rs = idchk.executeQuery()
        while (rs.next())
        {
            iduser=rs.getInt(1).toString()
            break
        }


        /* val onaylama1: PreparedStatement = connectSql.dbConn()
             ?.prepareStatement("insert into bitexen values(17,0,'2022-12-09 11:58:04.000',1,'dadaweawedaw')")!!


         onaylama1.executeUpdate()*/


        val mailaddress:EditText=findViewById<EditText>(R.id.editTextTextPersonName7)
        val onayagonder:Button=findViewById<Button>(R.id.button)
        val geridon:Button=findViewById<Button>(R.id.button3)




        geridon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        onayagonder.setOnClickListener {


            val checkbox=findViewById<CheckBox>(R.id.checkBox2)
            if (checkbox.isChecked==true)
            {
                if (mailaddress.text.toString() == "") {
                    Toast.makeText(this, "Kayıt tarihi girmelisin.", Toast.LENGTH_LONG).show()
                } else {



                        // mail kontrol
                        try {


                            val sdf = SimpleDateFormat("yyyy/dd/M hh:mm:ss")
                            val currentDate = sdf.format(Date())



                            val onaylama1: PreparedStatement = connectSql.dbConn()
                                ?.prepareStatement("insert into okx values(0,1,'" + mailaddress.text + "'," + iduser + ",0)")!!
                            onaylama1.executeUpdate()
                            val editor2: SharedPreferences.Editor = userlogin.edit()
                            editor2.putString("mailupload","1")

                            editor2.apply()

                            Toast.makeText(this, "En kısa sürede onaylayacağız.", Toast.LENGTH_SHORT)
                                .show()
                            UnityAds.initialize(this, GameID, testMode)
                            if (UnityAds.isReady(interPlacement)) {

                                UnityAds.show(this@MailUpload, interPlacement)
                            }


                        } catch (e: Exception) {

                            val login: PreparedStatement = connectSql.dbConn()
                                ?.prepareStatement("select durum,bekleme from okx where uid="+iduser+"")!!

                            val rs = login.executeQuery()
                            while (rs.next())
                            {

                                if (rs.getBoolean(1)==true)
                                {    Toast.makeText(
                                    this,  "İşlemin onaylandı.",

                                    Toast.LENGTH_SHORT
                                ).show()

                                }
                                else if (rs.getBoolean(2)==true)
                                {


                                    Toast.makeText(
                                        this,  "İşlemin onay bekliyor.",

                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        }



                }
            }
            else
            {   Toast.makeText(this, "Şartları kabul etmelisin.", Toast.LENGTH_SHORT)
                .show()}

        }

    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}