package com.vris.vlikeit

import android.R.drawable
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.vris.vlikeit.databinding.ActivityImageUploadBinding
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import java.util.*


class ImageUpload : AppCompatActivity() {

    lateinit var imageUri:Uri
    private var connectSql = ConnectSql()
    lateinit var userlogin:SharedPreferences
    lateinit var binding: ActivityImageUploadBinding
    lateinit var iduser:String
    private val GameID = "3716735"
    private val bannerPlacement = "Banner_Android"
    private val interPlacement = "interstitial"
    private val rewardedPlacement = "Rewarded_Ad"
    private val testMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UnityAds.initialize(this, GameID, testMode)
        if (  UnityAds.initialize(this, GameID, testMode)!=null )
        {
            UnityAds.initialize(this, GameID, testMode)

            UnityAds.show(this@ImageUpload, rewardedPlacement)
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

        binding.imageView2.visibility=View.INVISIBLE




        binding.selectimage.setOnClickListener {
            try {
                UnityAds.initialize(this, GameID, testMode)

            } catch (e:Exception)
            {Toast.makeText(this
                ,e.toString(),Toast.LENGTH_SHORT).show()}
            val intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,100)



        }
        val geridon:Button=findViewById(R.id.button15)
        geridon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        var kontrol=1
        val kapatac:Button=findViewById(R.id.button23)
        kapatac.setOnClickListener {

            if(binding.imageView2.drawable==null)
            {
                Toast.makeText(this, "Bir görsel seçmelisin.", Toast.LENGTH_SHORT).show()
            }

            else  if (kontrol==1)
            {

                kontrol--
                binding.imageView2.visibility=View.VISIBLE

                binding.textView11.visibility=View.INVISIBLE
                binding.imageView11.visibility=View.INVISIBLE
                //kapatac.setText("Resmi Kapat")
            }
            else if (kontrol==0)
            {
                binding.textView11.visibility=View.VISIBLE
                kontrol++
                binding.imageView11.visibility=View.VISIBLE
                binding.imageView2.visibility=View.INVISIBLE
                //kapatac.setText("Resmi Göster")

            }


        }

        binding.uploadimage.setOnClickListener {
val chkcbox=findViewById<CheckBox>(R.id.checkBox)
if (chkcbox.isChecked==true)
{

    var chk:Int=0
    if (binding.imageView2.drawable == null) {
        Toast.makeText(this@ImageUpload, "Yüklendi", Toast.LENGTH_SHORT).show()
        chk++

    } else if (binding.imageView2.drawable!=null)
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("uploading file..")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val idchk: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select id from Kullanici where email_address='" + log + "'")!!

        val rs = idchk.executeQuery()
        while (rs.next()) {
            iduser = rs.getInt(1).toString()
        }
        val filename = iduser
        val storageReference =
            FirebaseStorage.getInstance().getReference("freecashimage/$filename.jpg")


        storageReference.putFile(imageUri).addOnSuccessListener {
            binding.imageView2.setImageURI(null)
            if (chk==0) {
                try {
                    UnityAds.initialize(this, GameID, testMode)
                    if (UnityAds.isReady(interPlacement)) {

                        UnityAds.show(this@ImageUpload, interPlacement)
                    }
                } catch (e:Exception)
                {Toast.makeText(this
                    ,e.toString(),Toast.LENGTH_SHORT).show()}
                Toast.makeText(this@ImageUpload, "Yüklendi", Toast.LENGTH_SHORT).show()
            }// onaylama bekliyora gönder
            try {

                val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                var redchk: Boolean=false

                val redkontrol: PreparedStatement=connectSql.dbConn()
                    ?.prepareStatement("select red from Freacash where uid="+iduser+"")!!
                val redknt=redkontrol.executeQuery()
                while(redknt.next()){
                    if(redknt.getBoolean(1)==true)
                    {
                        redchk=true
                    }
                    break
                }
                if(redchk==true){
                    val durumguncelle:PreparedStatement=connectSql.dbConn()
                        ?.prepareStatement("update Freacash set durum=0, bekleme=1, red=0 where uid="+iduser+"")!!
                    durumguncelle.executeUpdate()
                }
                else{
                    val onaylama: PreparedStatement = connectSql.dbConn()
                        ?.prepareStatement("insert into Freacash values(" + iduser +",0,1,'"+currentDate.toString()+"',0)")!!

                    onaylama.executeUpdate()
                }





                val editor2: SharedPreferences.Editor = userlogin.edit()
                editor2.putString("imageupload","1")

                editor2.apply()
                Toast.makeText(this@ImageUpload, "Onay bekliyor.", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                val login: PreparedStatement = connectSql.dbConn()
                    ?.prepareStatement("select durum,bekleme from freacash where uid="+iduser+"")!!

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



            if (progressDialog.isShowing) progressDialog.dismiss()
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@ImageUpload, "Hata oluştu", Toast.LENGTH_SHORT).show()

        }

    }

} else
{ Toast.makeText(this, "Şartları kabul etmelisin.", Toast.LENGTH_SHORT).show()}




        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100&&resultCode== RESULT_OK)
        {
            imageUri=data?.data!!
            binding.imageView2.setImageURI(imageUri)
        }
    }

    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }

}
