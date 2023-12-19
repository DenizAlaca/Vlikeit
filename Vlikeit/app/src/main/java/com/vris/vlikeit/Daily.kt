package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import java.util.*

class Daily : AppCompatActivity() {
    private var connectSql = ConnectSql()
    lateinit var userlogin: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)

        val dailyanimation:LottieAnimationView=findViewById(R.id.dailyfreeView)
        dailyanimation.visibility= View.INVISIBLE
        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
      var  id= userlogin.getInt("idKullanici", 0).toString()
        val daily: Button =findViewById(R.id.button22)
        daily.setOnClickListener {

            val dailych: PreparedStatement =connectSql.dbConn()
                ?.prepareStatement("select dogrulama_kontrol from kullanici where id="+id+"")!!
            val kontoroldaily=dailych.executeQuery()
            while (kontoroldaily.next()) {
                if (kontoroldaily.getBoolean(1) == true) {
                    var insertchk=false
                    val dailychk: PreparedStatement =connectSql.dbConn()
                        ?.prepareStatement("select daily_date from daily where id="+userlogin.getInt("idKullanici",0).toInt()+" ")!!
                    val readdate=dailychk.executeQuery()
                    while (readdate.next())
                    {
                        insertchk=true
                        if (readdate.getString(1)!=null)
                        {
//select DATEDIFF(HOUR,
//(SELECT CONVERT(datetime, (select create_date from Kullanici where id=28 ))),GETDATE() )

                            try {
                                val dailychk: PreparedStatement =connectSql.dbConn()
                                    ?.prepareStatement("select DATEDIFF(HOUR,(SELECT CONVERT(datetime, (select daily_date from daily where id="+userlogin.getInt("idKullanici",0).toInt()+" ))),GETDATE() )")!!
                                val kontoroldaily=dailychk.executeQuery()
                                while (kontoroldaily.next())
                                {
                                    if (kontoroldaily.getInt(1)>=24)
                                    {
                                        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                                        val currentDate = sdf.format(Date())
                                        val dailychk: PreparedStatement =connectSql.dbConn()
                                            ?.prepareStatement("update daily set daily_date='"+currentDate.toString()+"',kont=1   where id="+userlogin.getInt("idKullanici",0).toInt()+"")!!
                                        dailychk.executeUpdate()
                                        Toast.makeText(this,"Gün içerisinde takas teklifi gönderilecek.",
                                            Toast.LENGTH_SHORT).show()
                                        //val text:TextView=findViewById(R.id.textView90)
                                        //text.visibility=View.INVISIBLE
                                        dailyanimation.visibility=View.VISIBLE
                                        dailyanimation.playAnimation()
                                        Handler().postDelayed({
                                                              dailyanimation.cancelAnimation()
                                            dailyanimation.visibility=View.INVISIBLE
                                        },5000)
                                    }
                                    else
                                    {
                                        Toast.makeText(this,"Henüz 24 saat dolmadı.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            catch (e:Exception)
                            {
                                Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show()
                            }


                        }

                    }
                    if (!readdate.next() and insertchk==false) {
                        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                        val currentDate = sdf.format(Date())
                        val dailychk1: PreparedStatement = connectSql.dbConn()
                            ?.prepareStatement(
                                "insert into daily values(" + userlogin.getInt("idKullanici", 0)
                                    .toInt() + ",0,0,'" + currentDate.toString() + "')"
                            )!!
                        dailychk1.executeUpdate()
                        Toast.makeText(this,"Gün içerisinde takas teklifi gönderilecek.", Toast.LENGTH_SHORT).show()
                        //val text:TextView=findViewById(R.id.textView90)
                        //text.visibility=View.INVISIBLE
                    }
                }
                else
                {
                    Toast.makeText(this,"Hesabını profilden doğrula.", Toast.LENGTH_SHORT).show()
                }
            }



        }
        val  geridon=findViewById<Button>(R.id.geridonButton)
        geridon.setOnClickListener {

            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()

        }
    }
}