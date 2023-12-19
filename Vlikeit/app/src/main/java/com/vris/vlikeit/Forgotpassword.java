package com.vris.vlikeit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class Forgotpassword extends AppCompatActivity {

    public SharedPreferences userlogin;
    public String logdogrulanma;
    public String password;
    public String maildogrulama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        userlogin=getSharedPreferences("userlog", Context.MODE_PRIVATE);
        logdogrulanma =userlogin.getString("dogrulamakod","");
        maildogrulama=userlogin.getString("resetemanil","");
password=userlogin.getString("passwordreset","");


     //   mail=(EditText)findViewById(R.id.editTextTextPersonName11);
      //--  mail.setText(maildogrulama);
       // Toast.makeText(this, "-"+maildogrulama,
            //    Toast.LENGTH_LONG).show();
        ProgressDialog progressDialog =new  ProgressDialog(this);
        progressDialog.setMessage("uploading file..");
        progressDialog.setCancelable(false);
        progressDialog.show();

            sendMail();

            if (progressDialog.isShowing())
            {progressDialog.dismiss();


                Toast.makeText(this,
                        "Girdiğiniz mail adresi kayıtlıysa şifreniz gönderilecek.", Toast.LENGTH_LONG).show();

        Intent don=new Intent(getApplicationContext(),LogIn.class);
        startActivity(don);}


    }
    private void sendMail()
    {

        String message="VRIS";
        JavaMailApi javaMailApi=new JavaMailApi(this,maildogrulama,message,"Şifreniz:"+password.toString()+" Giriş yaptıktan sonra değiştirmeyi unutmayın.");
        javaMailApi.execute();
    }

}