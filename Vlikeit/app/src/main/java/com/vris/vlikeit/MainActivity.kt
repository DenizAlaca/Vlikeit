package com.vris.vlikeit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vris.vlikeit.databinding.ActivityMainBinding
import java.sql.PreparedStatement

class MainActivity : AppCompatActivity() {

    lateinit var  userlogin: SharedPreferences
    private var connectSql = ConnectSql()
    lateinit var  binding: ActivityMainBinding
    lateinit var frelog:String
    lateinit var bitlog:String
    lateinit var okxlog:String
    lateinit var log:String
     var id:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)
         log=userlogin.getString("ulog","").toString()
        frelog=userlogin.getString("FREAPOPUP","").toString()
        bitlog=userlogin.getString("BITEXENPOPUP","").toString()
        okxlog=userlogin.getString("OKXPOPUP","").toString()
        id=userlogin.getInt("idKullanici",0)


        Durumkont("Okx",2)
        Durumkont("Freacash",1)
       /* var frekont=userlogin.getString("imageupload","").toString()
        var okxkont=userlogin.getString("mailupload","").toString()

        if (frekont=="") {
            binding.whiteViewFreecash.visibility = View.VISIBLE
            binding.onaydurumFreecash.setText("Başlanmadı")
        }
        if (okxkont=="")
        {
            binding.whiteViewOkx.visibility = View.VISIBLE
            binding.onaydurumOkx.setText("Başlanmadı")
        }*/

     binding.button20.setOnClickListener {
         val intent = Intent(this, FreecashDetay::class.java)
         startActivity(intent)
         finish()
     }

        binding.button31.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/vlike_it"))
            startActivity(browserIntent);
        }
       /* binding.button19.setOnClickListener {
            val intent = Intent(this, BitexenDetay::class.java)
            startActivity(intent)
            finish()
        }*/

        binding.button35.setOnClickListener {
            val intent = Intent(this, FreecashGetiri::class.java)
            startActivity(intent)
            finish()
        }

     /*   binding.button36.setOnClickListener {
            val intent = Intent(this, BitpandaGetiri::class.java)
            startActivity(intent)
            finish()
        }*/

        binding.button39.setOnClickListener {
            val intent = Intent(this, OkxGetiri::class.java)
            startActivity(intent)
            finish()
        }
        val refresh: SwipeRefreshLayout =findViewById(R.id.constraintLayout165)

        refresh.setOnRefreshListener {
            Durumkont("Okx",2)
            Durumkont("Freacash",1)
            refresh.isRefreshing=false
        }






        val versionName = BuildConfig.VERSION_NAME
       // Toast.makeText(this,versionName, Toast.LENGTH_SHORT).show()

        Handler().postDelayed({

        val login: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select version from Version")!!

        val rs = login.executeQuery()
        while (rs.next())
        {
if (rs.getString(1)!=versionName.toString())
{
   /* val intent = Intent(this, VersionUpdate::class.java)
    startActivity(intent)
    finish()*/
}

        }
        },20)




      /*  val window = PopupWindow(this)
        val view = layoutInflater.inflate(R.layout.activity_freecash_detay, null)
      //  window.contentView=view
      val butoniki = view.findViewById<Button>(R.id.button12)
       butoniki.setOnClickListener { window.dismiss()
        }

        window.showAsDropDown(binding.teklif3)*/


        /*  binding.button4.setOnClickListener {
              val progressDialog=ProgressDialog(this)
              progressDialog.setMessage("...")
              progressDialog.setCancelable(false)


              val imageName=binding.editTextTextPersonName7.text.toString()
              val storageRef=FirebaseStorage.getInstance().reference.child("freecashimage/$imageName.jpg")
              val localfile= File.createTempFile("tempimage","jpg")
              storageRef.getFile(localfile).addOnSuccessListener {
  val bitmap=BitmapFactory.decodeFile(localfile.absolutePath)
                  binding.imageView.setImageBitmap(bitmap)
              }.addOnFailureListener{

              }

          }*/

        //kapalı kısım admin paneline eklenecek

        binding.constraintLayout23.setOnClickListener {
            val intent = Intent(this, Pofile::class.java)
            startActivity(intent)
            finish()
        }



      /*  val chkuser: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("insert into deneme1 values(?)")!!
        chkuser.setString(1,"bpl")
        chkuser.executeUpdate()*/

       /* val BT:Button=findViewById<Button>(R.id.button8)


       BT.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
        val BT1:Button=findViewById<Button>(R.id.button9)
        */

        binding.constraintLayout22.setOnClickListener {
            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()
        }
        binding.button5.setOnClickListener {
            val freekont: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select kontrol  from freekont")!!
            val rs1= freekont.executeQuery()
            while (rs1.next())
            {
if(rs1.getBoolean(1)==true)
{ if (userlogin.getString("FREAPOPUP", "") == "") {
    val intent = Intent(this, FreecashDetay::class.java)
    startActivity(intent)
    finish()

} else {
    val intent = Intent(this, ImageUpload::class.java)
    startActivity(intent)
    finish()
}}
                else
{
    Toast.makeText(this,"Bu teklif geçici olarak kapalı.",Toast.LENGTH_SHORT).show()
}



                break
            }


        }
      /*  binding.button7.setOnClickListener {
            if (userlogin.getString("BITEXENPOPUP", "") == "") {
                val intent = Intent(this, BitexenDetay::class.java)
                startActivity(intent)
                finish()

            } else {
                val intent = Intent(this, MailUpload::class.java)
                startActivity(intent)
                finish()
            }
        }*/
        binding.button24.setOnClickListener {

            val freekont: PreparedStatement = connectSql.dbConn()
                ?.prepareStatement("select kontrol  from oxkkont")!!
            val rs1= freekont.executeQuery()
            while (rs1.next()) {
                if (rs1.getBoolean(1)==true) {
                    if (userlogin.getString("OKXPOPUP", "") == "") {
                        val intent = Intent(this, OkxDetay::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        val intent = Intent(this, MailUpload::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else
                {
                    Toast.makeText(this,"Bu teklif geçici olarak kapalı.",Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.button25.setOnClickListener {
            val intent = Intent(this, OkxDetay::class.java)
            startActivity(intent)
            finish()
        }



    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
    fun Durumkont(teklif:String,teklifsay:Int)
    {
        var i:Int=0
        var x:Int=0

        val login1: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select  durum,bekleme,red from "+teklif.toString()+" where uid="+id+"")!!

        val rs1 = login1.executeQuery()

        while (rs1.next())
        {



    if (rs1.getBoolean(1) == true && teklifsay == 1) {
        binding.greenViewFreecash.visibility = View.VISIBLE
        binding.redViewFreecash.visibility=View.INVISIBLE
        binding.orangeViewFreecash.visibility=View.INVISIBLE
        binding.whiteViewFreecash.visibility=View.INVISIBLE
        binding.onaydurumFreecash.setText("Onaylandı")
        i++
    } else if (rs1.getBoolean(2) == true && teklifsay == 1) {
        binding.onaydurumFreecash.setText("Onay Bekliyor")
        binding.greenViewFreecash.visibility = View.INVISIBLE
        binding.redViewFreecash.visibility=View.INVISIBLE

        binding.whiteViewFreecash.visibility=View.INVISIBLE
        binding.orangeViewFreecash.visibility = View.VISIBLE
        i++
    } else if (rs1.getBoolean(3) == true && teklifsay == 1) {
        binding.greenViewFreecash.visibility = View.INVISIBLE

        binding.orangeViewFreecash.visibility=View.INVISIBLE
        binding.whiteViewFreecash.visibility=View.INVISIBLE
        binding.redViewFreecash.visibility = View.VISIBLE
        binding.onaydurumFreecash.setText("Onaylanmadı")
        i++
    } else
        if (rs1.getBoolean(1) == true && teklifsay == 2) {
            binding.onaydurumOkx.setText("Onaylandı")
            binding.redViewOkx.visibility=View.INVISIBLE
            binding.orangeViewOkx.visibility=View.INVISIBLE
            binding.whiteViewOkx.visibility=View.INVISIBLE
            x++
            binding.greenViewOkx.visibility = View.VISIBLE
        } else if (rs1.getBoolean(2) == true && teklifsay == 2) {
            binding.onaydurumOkx.setText("Onay Bekliyor")
            binding.redViewOkx.visibility=View.INVISIBLE
            binding.greenViewOkx.visibility=View.INVISIBLE
            binding.whiteViewOkx.visibility=View.INVISIBLE
            x++
            binding.orangeViewOkx.visibility = View.VISIBLE
        } else if (rs1.getBoolean(3) == true && teklifsay == 2) {
            binding.onaydurumOkx.setText("Onaylanmadı")
            x++
            binding.orangeViewOkx.visibility = View.INVISIBLE
            binding.greenViewOkx.visibility=View.INVISIBLE
            binding.whiteViewOkx.visibility=View.INVISIBLE
            binding.redViewOkx.visibility = View.VISIBLE
        }

    break


        }
        if (!rs1.next())
        {
            if (teklifsay==1&&i==0) {
                binding.whiteViewFreecash.visibility = View.VISIBLE

                binding.onaydurumFreecash.setText("Başlanmadı")
            }
            if (teklifsay==2&&x==0)
            {


                binding.whiteViewOkx.visibility = View.VISIBLE
                binding.onaydurumOkx.setText("Başlanmadı")
            }
        }
    }
}