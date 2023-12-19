package com.vris.vlikeit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class VersionUpdate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_update)


        val update:Button=findViewById<Button>(R.id.button4)
        update.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.vris.vlikeit&hl=tr&gl=US")
                )
            )
        }

    }
    override fun onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }
}