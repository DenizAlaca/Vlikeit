package com.vris.vlikeit

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.sql.PreparedStatement

class FreecashGoturu : AppCompatActivity() {
    private var connectSql = ConnectSql()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freecash_goturu)


        resimgel("teklif1")

        val anladim=findViewById<Button>(R.id.button41)
        val  gider=findViewById<TextView>(R.id.textView53)

        val idchk: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select gider from GelirGider where teklif_adı='Freecash'")!!

        val rs = idchk.executeQuery()
        while (rs.next())
        {
            gider.setText(rs.getString(1).toString())
            break
        }

        anladim.setOnClickListener {
            val intent = Intent(this, Oduller::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun resimgel(ad:String)
    {
        val idchk: PreparedStatement = connectSql.dbConn()
            ?.prepareStatement("select skinDetay from Skins where teklif='Freecash'")!!

        val rs = idchk.executeQuery()
        while (rs.next())
        {
           val skin:TextView=findViewById(R.id.skinAdi2)
            skin.setText(rs.getString(1))
            break
        }

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Veriler alınıyor..")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val imageName=ad
        val storageReference=FirebaseStorage.getInstance().reference.child("skins/$imageName.png")
        val localfile= File.createTempFile("tempImage","jpg")
        storageReference.getFile(localfile).addOnSuccessListener {
            if (progressDialog.isShowing)
                progressDialog.dismiss()

            val bitmap=BitmapFactory.decodeFile(localfile.absolutePath)
            val imageView12:ImageView=findViewById(R.id.freecashodul)
            imageView12.setImageBitmap(bitmap)
        }.addOnFailureListener{
            if (progressDialog.isShowing)
                progressDialog.dismiss()
            Toast.makeText(this,"Fail",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}