package com.vris.vlikeit

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import java.util.*


class Starter : AppCompatActivity() {

    lateinit var  userlogin: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)

        val ilerle:Button=findViewById(R.id.button51)
        val anim1:LottieAnimationView=findViewById(R.id.anim1)
        val anim2:LottieAnimationView=findViewById(R.id.anim2)
        val anim3:LottieAnimationView=findViewById(R.id.anim3)
        val anim4:LottieAnimationView=findViewById(R.id.anim4)
        val text1:TextView=findViewById(R.id.anim1Text)
        val text2:TextView=findViewById(R.id.anim2Text)
        val text3:TextView=findViewById(R.id.anim3Text)
        val text4:TextView=findViewById(R.id.anim4Text)
        val text5:TextView=findViewById(R.id.anim5Text)
        anim1.visibility= View.VISIBLE
        text1.visibility=View.VISIBLE
        var ilerleme=0
        val progressBar:ProgressBar=findViewById(R.id.progressBar2)
        progressBar.max=100

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE)





        ilerle.setOnClickListener {


            if (ilerleme==0)
            {  ilerleme++}

            if (ilerleme==1)
            {
                anim2.visibility= View.VISIBLE
                text2.visibility=View.VISIBLE
                anim1.visibility= View.INVISIBLE
                text1.visibility=View.INVISIBLE
                ilerleme++
                progressBar.progress = 25
            }
            else if (ilerleme==2)
            {
                anim2.visibility= View.INVISIBLE
                text2.visibility=View.INVISIBLE
                anim3.visibility= View.VISIBLE
                text3.visibility=View.VISIBLE
                ilerleme++
                progressBar.progress = 50
            }
            else if (ilerleme==3)
            {
                anim3.visibility= View.INVISIBLE
                text3.visibility=View.INVISIBLE
                anim4.visibility= View.VISIBLE
                text4.visibility=View.VISIBLE
                ilerleme++
                progressBar.progress = 75
            }
            else if (ilerleme==4)
            {
                anim4.visibility= View.INVISIBLE
                text4.visibility=View.INVISIBLE

                text5.visibility=View.VISIBLE
                ilerleme++
                progressBar.progress = 100
            }
            else if (ilerleme==5)
            {


                val editor2: SharedPreferences.Editor = userlogin.edit()
                editor2.putString("ogren", "1")

                editor2.apply()
                val editor21: SharedPreferences.Editor = userlogin.edit()
                editor21.putString("ogren1", "1")

                editor21.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

}