package com.vris.vlikeit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class Dogrula extends AppCompatActivity {

    public EditText mail;
    public SharedPreferences userlogin;
    public String logdogrulanma;
    public String maildogrulama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogrula);

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE);
      logdogrulanma =userlogin.getString("dogrulamakod","");
      maildogrulama=userlogin.getString("mailal","");


        mail=(EditText)findViewById(R.id.editTextTextPersonName11);
        mail.setText(maildogrulama);
       // Button button=(Button) findViewById(R.id.button16);
     //   button.setOnClickListener(view -> {


sendMail();




            Intent don=new Intent(getApplicationContext(), Pofile.class);
            startActivity(don);

     //   });

    }
    private void sendMail()
    {

        String message="VRIS";
        JavaMailApi javaMailApi=new JavaMailApi(this,maildogrulama,message,"Doğrulama kodu:"+logdogrulanma);
        javaMailApi.execute();
    }
}