package com.vris.vlikeit

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAds.FinishState
import com.unity3d.ads.UnityAds.UnityAdsError
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import java.util.*

class Oduller : AppCompatActivity() {

    lateinit var  frecashdrm:String
    private var connectSql = ConnectSql()
    lateinit var userlogin: SharedPreferences
    lateinit var id :String
    private val GameID = "3716735"
    private val bannerPlacement = "Banner_Android"
    private val interPlacement = "interstitial"
    private val rewardedPlacement = "Rewarded_Ad"
    private val testMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oduller)

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)


        val progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Yükleniyor...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        //val bottomSheetFragment = BottomSheetFragment()
        val detay:Button=findViewById(R.id.button52)
        val detayokx:Button=findViewById(R.id.button54)

        detay.setOnClickListener {
        val bottomSheetDialog = BottomSheetDialog(
            this@Oduller, R.style.BottomSheetDialogTheme
        )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottomsheet_fragment,
                findViewById<ConstraintLayout>(R.id.background)
            )

            bottomSheetDialog.dismiss()
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetDialog.show()


        //bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }
        detayokx.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this@Oduller, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.bottomsheet_fragment,
                findViewById<ConstraintLayout>(R.id.background)
            )

            bottomSheetDialog.dismiss()
            bottomSheetDialog.setContentView(bottomSheetView)

            bottomSheetDialog.show()


            //bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }
        val daily: ImageView =findViewById(R.id.dailyView)
        daily.setOnClickListener {
            var durum=true
            val dailychk:PreparedStatement=connectSql.dbConn()
                ?.prepareStatement("select kontrol from dailykont")!!
            val kontoroldaily=dailychk.executeQuery()
            while (kontoroldaily.next())
            {
                durum=kontoroldaily.getBoolean(1)
            }
            if (durum==true) {

                val intent = Intent(this, Daily::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this,"Geçici Olarak Bakımda.",Toast.LENGTH_SHORT).show()
            }


        }


        UnityAds.initialize(this, GameID, testMode)
        if (  UnityAds.initialize(this, GameID, testMode)!=null )
        {
            UnityAds.initialize(this, GameID, testMode)

            UnityAds.show(this@Oduller, rewardedPlacement)
        }
        val interListner: IUnityAdsListener = object : IUnityAdsListener {
            override fun onUnityAdsReady(s: String) {}
            override fun onUnityAdsStart(s: String) {}
            override fun onUnityAdsFinish(s: String, finishState: FinishState) {}
            override fun onUnityAdsError(unityAdsError: UnityAdsError, s: String) {}
        }
        UnityAds.setListener(interListner)
        UnityAds.load(interPlacement)
        UnityAds.initialize(this, GameID, testMode)

        val cons:ConstraintLayout=findViewById(R.id.constraintLayout25)

        cons.setOnClickListener{
            Toast.makeText(this, "Nabersin", Toast.LENGTH_SHORT)
        }


        val twitter:ImageView=findViewById(R.id.imageView14)

        twitter.setOnClickListener {

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/vlike_it"))
            startActivity(browserIntent)
                    /*  try {

               if (UnityAds.isReady(interPlacement)) {

                   UnityAds.show(this@Oduller, interPlacement)
               }
           } catch (e:Exception)
           {Toast.makeText(this
           ,e.toString(),Toast.LENGTH_SHORT).show()}*/
        }





            countcek("GiftFreacash",1)
           // countcek("GiftBitpanda",2)
            countcek("GiftOkx",3)
            userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)

                id= userlogin.getInt("idKullanici", 0).toString()
      //  notification(id.toInt())

        try {

                notification(id.toInt())


        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }

        Handler().postDelayed({
            progressDialog.dismiss()
        },800)


       /* val countfrea: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select count(*) from GiftFreacash ")!!

        val countf = countfrea.executeQuery()

        val progres1:ProgressBar=findViewById(R.id.progressBar)
        progres1.max=10
        while (countf.next())
        {
            progres1.setProgress(countf.getInt(1))
            val textView: TextView = findViewById(R.id.textView2)
            textView.setText(countf.getInt(1).toString()+"/10")
            break
        }*/




        val teklif1:Button=findViewById(R.id.button10)
        teklif1.setOnClickListener {

            var onaykont: Int = 0
            var knt = false
            var log = userlogin.getString("ulog", "")
            val idchk: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select dogrulama_kontrol from Kullanici where email_address='" + log + "'")!!

            val rs = idchk.executeQuery()
            while (rs.next()) {
                knt = rs.getBoolean(1)
                break
            }
            if (knt == true) {



            val odulchkfrea: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select kontrol from GiftFreacash where uid=0")!!

            val odulfr = odulchkfrea.executeQuery()
            while (odulfr.next()) {
                var durumfrea = odulfr.getBoolean(1)
                if (durumfrea != true) {
                    Toast.makeText(this, "Çekiliş aktif durumda değil.", Toast.LENGTH_SHORT).show()
                } else {
                    val odulchk1: PreparedStatement = connectSql.dbConn()
                        ?.prepareStatement("select uid from GiftFreacash where uid=" + id + "")!!

                    val odul = odulchk1.executeQuery()
                    if (odul.next()) {
                        try {
                            UnityAds.initialize(this, GameID, testMode)
                            if (UnityAds.isReady(interPlacement)) {

                                UnityAds.show(this@Oduller, interPlacement)
                            }
                        } catch (e:Exception)
                        {Toast.makeText(this
                            ,e.toString(),Toast.LENGTH_SHORT).show()}
                        Toast.makeText(this, "Çekilişe katılmış durumdasın.", Toast.LENGTH_SHORT)
                            .show()
                        /*if (odul.getInt(1)==null)
                        {

                        }*/


                    } else {
                        val freacashchk: PreparedStatement = connectSql.dbConn()
                            ?.prepareStatement("select durum from Freacash where uid=" + id.toInt() + "")!!

                        val rs1 = freacashchk.executeQuery()
                        while (rs1.next()) {
                            if (rs1.getBoolean(1) == false) {
                                try {
                                    UnityAds.initialize(this, GameID, testMode)
                                    if (UnityAds.isReady(interPlacement)) {

                                        UnityAds.show(this@Oduller, interPlacement)
                                    }
                                } catch (e:Exception)
                                {Toast.makeText(this
                                    ,e.toString(),Toast.LENGTH_SHORT).show()}
                                Toast.makeText(
                                    this,
                                    "Çekilişe katılmak için onaylanmamışsın.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onaykont++
                            } else if (rs1.getBoolean(1) == true) {
                                val freacashchkekle: PreparedStatement = connectSql.dbConn()
                                    ?.prepareStatement("insert into GiftFreacash values(" + id.toInt() + ",0)")!!

                                freacashchkekle.executeUpdate()
                                try {
                                    UnityAds.initialize(this, GameID, testMode)
                                    if (UnityAds.isReady(interPlacement)) {

                                        UnityAds.show(this@Oduller, interPlacement)
                                    }
                                } catch (e:Exception)
                                {Toast.makeText(this
                                    ,e.toString(),Toast.LENGTH_SHORT).show()}
                                Toast.makeText(this, "Çekilişe katıldın.", Toast.LENGTH_SHORT)
                                    .show()
                                onaykont=3
                            }
                        }
                        if (!rs1.next() && onaykont == 0) {
                            try {
                                UnityAds.initialize(this, GameID, testMode)
                                if (UnityAds.isReady(interPlacement)) {

                                    UnityAds.show(this@Oduller, interPlacement)
                                }
                            } catch (e:Exception)
                            {Toast.makeText(this
                                ,e.toString(),Toast.LENGTH_SHORT).show()}
                            Toast.makeText(this, "Onay başvurusu yapmalısın.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }

        }
            else
            {
                Toast.makeText(this,"Hesabını profil sayfasından doğrulamalısın.",Toast.LENGTH_SHORT).show()
            }

        }

      /*  val teklif2:Button=findViewById(R.id.button9)
        teklif2.setOnClickListener {
            var onaykont1:Int=0

            var knt = false
            var log = userlogin.getString("ulog", "")
            val idchk: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select dogrulama_kontrol from Kullanici where email_address='" + log + "'")!!

            val rs = idchk.executeQuery()
            while (rs.next()) {
                knt = rs.getBoolean(1)
                break
            }
            if (knt == true) {
                val odulchkfrea: PreparedStatement = connectSql.dbConn()
                    ?.prepareStatement("select kontrol from GiftBitpanda where uid=0")!!

                val odulfr = odulchkfrea.executeQuery()
                while (odulfr.next()) {
                    var durumfrea = odulfr.getBoolean(1)
                    if (durumfrea != true) {
                        Toast.makeText(this, "Çekiliş aktif durumda değil.", Toast.LENGTH_SHORT)
                            .show()
                    } else {


                        val odulchk1: PreparedStatement = connectSql.dbConn()
                            ?.prepareStatement("select uid from GiftBitpanda where uid=" + id + "")!!

                        val odul = odulchk1.executeQuery()
                        if (odul.next()) {
                            Toast.makeText(
                                this,
                                "Çekilişe katılmış durumdasın.",
                                Toast.LENGTH_SHORT
                            ).show()
                            /*if (odul.getInt(1)==null)
                        {

                        }*/


                        } else {

                            val freacashchk: PreparedStatement = connectSql.dbConn()
                                ?.prepareStatement("select durum from Bitpanda where uid=" + id.toInt() + "")!!

                            val rs1 = freacashchk.executeQuery()
                            while (rs1.next()) {
                                if (rs1.getBoolean(1) == false) {
                                    Toast.makeText(
                                        this,
                                        "Çekilişe katılmak için onaylanmamış",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onaykont1++
                                } else if (rs1.getBoolean(1) == true) {


                                    val freacashchkekle: PreparedStatement = connectSql.dbConn()
                                        ?.prepareStatement("insert into GiftBitpanda values(" + id.toInt() + ",0)")!!

                                    freacashchkekle.executeUpdate()
                                    Toast.makeText(this, "Çekilişe katıldın.", Toast.LENGTH_SHORT)
                                        .show()
                                    onaykont1 += 8
                                }

                            }
                            if (!rs1.next() && onaykont1 == 0) {
                                Toast.makeText(
                                    this,
                                    "Onay başvurusu yapmalısın.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                }
            }
            else
            {
                Toast.makeText(this,"Hesabını Profil Sayfasından Doğrula",Toast.LENGTH_SHORT).show()
            }



        }*/

        val teklif3:Button=findViewById(R.id.button29)
        teklif3.setOnClickListener {

            var onaykont1:Int=0
            var knt = false
            var log = userlogin.getString("ulog", "")
            val idchk: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select dogrulama_kontrol from Kullanici where email_address='" + log + "'")!!

            val rs = idchk.executeQuery()
            while (rs.next()) {
                knt = rs.getBoolean(1)
                break
            }
            if (knt == true) {
                val odulchkfrea: PreparedStatement = connectSql.dbConn()
                    ?.prepareStatement("select kontrol from GiftOkx where uid=0")!!

                val odulfr = odulchkfrea.executeQuery()
                while (odulfr.next()) {
                    var durumfrea = odulfr.getBoolean(1)
                    if (durumfrea != true) {
                        Toast.makeText(this, "Çekiliş aktif durumda değil.", Toast.LENGTH_SHORT)
                            .show()
                    } else {


                        val odulchk1: PreparedStatement = connectSql.dbConn()
                            ?.prepareStatement("select uid from Giftokx where uid=" + id + "")!!

                        val odul = odulchk1.executeQuery()
                        if (odul.next()) {
                            try {
                                UnityAds.initialize(this, GameID, testMode)
                                if (UnityAds.isReady(interPlacement)) {

                                    UnityAds.show(this@Oduller, interPlacement)
                                }
                            } catch (e:Exception)
                            {Toast.makeText(this
                                ,e.toString(),Toast.LENGTH_SHORT).show()}
                            Toast.makeText(
                                this,
                                "Çekilişe katılmış durumdasın.",
                                Toast.LENGTH_SHORT
                            ).show()
                            /*if (odul.getInt(1)==null)
                        {

                        }*/


                        } else {

                            val freacashchk: PreparedStatement = connectSql.dbConn()
                                ?.prepareStatement("select durum from Okx where uid=" + id.toInt() + "")!!

                            val rs1 = freacashchk.executeQuery()
                            while (rs1.next()) {
                                if (rs1.getBoolean(1) == false) {
                                    try {
                                        UnityAds.initialize(this, GameID, testMode)
                                        if (UnityAds.isReady(interPlacement)) {

                                            UnityAds.show(this@Oduller, interPlacement)
                                        }
                                    } catch (e:Exception)
                                    {Toast.makeText(this
                                        ,e.toString(),Toast.LENGTH_SHORT).show()}
                                    Toast.makeText(
                                        this,
                                        "Çekilişe katılmak için onaylanmamışsın.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onaykont1++
                                } else if (rs1.getBoolean(1) == true) {


                                    val freacashchkekle: PreparedStatement = connectSql.dbConn()
                                        ?.prepareStatement("insert into GiftOkx values(" + id.toInt() + ",0)")!!

                                    freacashchkekle.executeUpdate()
                                    try {
                                        UnityAds.initialize(this, GameID, testMode)
                                        if (UnityAds.isReady(interPlacement)) {

                                            UnityAds.show(this@Oduller, interPlacement)
                                        }
                                    } catch (e:Exception)
                                    {Toast.makeText(this
                                        ,e.toString(),Toast.LENGTH_SHORT).show()}
                                    Toast.makeText(this, "Çekilişe katıldın.", Toast.LENGTH_SHORT)
                                        .show()
                                    onaykont1 += 8
                                }

                            }
                            if (!rs1.next() && onaykont1 == 0) {
                                try {
                                    UnityAds.initialize(this, GameID, testMode)
                                    if (UnityAds.isReady(interPlacement)) {

                                        UnityAds.show(this@Oduller, interPlacement)
                                    }
                                } catch (e:Exception)
                                {Toast.makeText(this
                                    ,e.toString(),Toast.LENGTH_SHORT).show()}
                                Toast.makeText(
                                    this,
                                    "Onay başvurusu yapmalısın.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                }
            }
            else
            {
                Toast.makeText(this,"Hesabını profil sayfasından doğrulamalısın.",Toast.LENGTH_SHORT).show()
            }


        }

        val main:ConstraintLayout=findViewById(R.id.constraintLayout26)
        val profile:ConstraintLayout=findViewById(R.id.constraintLayout27)
        main.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        profile.setOnClickListener {
            val intent = Intent(this, Pofile::class.java)
            startActivity(intent)
            finish()
        }
        /*val  binding=findViewById<Button>(R.id.button30)
        val  binding1=findViewById<Button>(R.id.button31)
        binding.setOnClickListener {
            val intent = Intent(this, Oduller2::class.java)
            startActivity(intent)
            finish()
        }
        binding1.setOnClickListener {
            val intent = Intent(this, Oduller2::class.java)
            startActivity(intent)
            finish()
        }
        */
        val freecashgoturu=findViewById<Button>(R.id.button42)
        val bitpandagoturu=findViewById<Button>(R.id.button43)
        val okxGoturu=findViewById<Button>(R.id.button28)
        okxGoturu.setOnClickListener {
            val intent = Intent(this, OkxGoturu::class.java)
            startActivity(intent)
            finish()
        }
        freecashgoturu.setOnClickListener {
            val intent = Intent(this, FreecashGoturu::class.java)
            startActivity(intent)
            finish()
        }
      /*  bitpandagoturu.setOnClickListener {
            val intent = Intent(this, BitpandaGoturu::class.java)
            startActivity(intent)
            finish()
        }*/
        val refresh:SwipeRefreshLayout=findViewById(R.id.constraintLayout98)

        refresh.setOnRefreshListener {
            countcek("GiftFreacash",1)
            // countcek("GiftBitpanda",2)
            countcek("GiftOkx",3)
            id= userlogin.getInt("idKullanici", 0).toString()
            notification(id.toInt())
            refresh.isRefreshing=false
        }



    }
    // create kapanış
    public fun countcek( gel:String,sec:Int) {

        var p1=0
        var p2=0
        val countfrea: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select count(*) from "+gel.toString()+" ")!!

        val countf = countfrea.executeQuery()
   val progres1:ProgressBar=findViewById(R.id.progressBar)
        val progres2:ProgressBar=findViewById(R.id.progressBar4)
        val progres3:ProgressBar=findViewById(R.id.progressBar3)

        val cekilisyonet:PreparedStatement=connectSql.dbConn()
            ?.prepareStatement("select katılımcısayisi from progress where teklifadi='"+gel.toString()+"'")!!
        val progrescount=cekilisyonet.executeQuery()
        while (progrescount.next())
        {
            if (sec==1)
            {progres1.max=progrescount.getInt(1)
                p1=progrescount.getInt(1)
            }
           else if(sec==3)
            {progres3.max=progrescount.getInt(1)
                p2=progrescount.getInt(1)
           }

        }

        while (countf.next())
        {

            if (sec==1)
            {
                progres1.setProgress(countf.getInt(1))
                if(countf.getInt(1)==p1)
                {
                    val teklif1:Button=findViewById(R.id.button10)
                    teklif1.isEnabled=false

                    val textView: TextView = findViewById(R.id.textView2)
                    textView.setText(countf.getInt(1).toString() + "/" + p1.toString())
                }
                else
                {
                    val textView: TextView = findViewById(R.id.textView2)
                    textView.setText(countf.getInt(1).toString() + "/" + p1.toString())
                }
            }
            if (sec==2)
            {
                progres2.setProgress(countf.getInt(1))
                val textView: TextView = findViewById(R.id.textView18)
                textView.setText(countf.getInt(1).toString()+"/100")
            }
            if (sec==3)
            {

                val teklif3:Button=findViewById(R.id.button29)
                progres3.setProgress(countf.getInt(1))
                if(countf.getInt(1)==p2)
                {

                    val textView: TextView = findViewById(R.id.textView75)
                    textView.setText(countf.getInt(1).toString()+"/"+p2.toString())
                    teklif3.isEnabled=false
                }
                val textView: TextView = findViewById(R.id.textView75)
                textView.setText(countf.getInt(1).toString()+"/"+p2.toString())

            }


            break
        }
    }
public fun notification(id:Int)
{



var knt=0
            try {
                val dailychk:PreparedStatement=connectSql.dbConn()
                    ?.prepareStatement("select DATEDIFF(HOUR,(SELECT CONVERT(datetime, (select daily_date from daily where id="+id+" ))),GETDATE() )")!!
                val kontoroldaily=dailychk.executeQuery()
                while (kontoroldaily.next())
                {



                    if (kontoroldaily.getInt(1)>=24)

                    {

                        val red:LottieAnimationView=findViewById(R.id.redViewFreecash3)
                        red.visibility=View.VISIBLE

                        knt=1

                    }
                    else if(kontoroldaily.getInt(1)<24)
                    {
                        val red:LottieAnimationView=findViewById(R.id.redViewFreecash3)
                        red.visibility=View.INVISIBLE
                        knt=1
                    }

                   else if(kontoroldaily.getInt(1)==null||!kontoroldaily.next()&&knt==0)
                    {

                        val red:LottieAnimationView=findViewById(R.id.redViewFreecash3)
                        red.visibility=View.VISIBLE

                    }
                }

            }
            catch (e:Exception)
            {
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
            }






}
    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }

}