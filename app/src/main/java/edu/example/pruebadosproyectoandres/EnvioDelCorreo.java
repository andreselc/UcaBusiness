package edu.example.pruebadosproyectoandres;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import logica.usuario.EnviarCorreo;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvioDelCorreo extends Activity implements View.OnClickListener  {

    private static final String TAG = "olaaaaaa";
    public String rec,subject,textMessage;
    public Session session=null;
    public ProgressDialog progressDialog =null;
    public Context context =null;
    public int codigo;
    public EnviarCorreo correo;

    private int getCodigo(){
        return CodigoAleatoreo();
    }

    protected int CodigoAleatoreo(){
        double codigo = 10000 + Math.random() * 90000;
        return (int) Math.round(codigo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_del_correo);
        Intent intent= getIntent();
        rec=recibirDatosCorreo();
        context = this;
        Button login = (Button) findViewById(R.id.button4);

        login.setOnClickListener((View.OnClickListener) this);
    }
    @Override
    public void onClick(View v) {
        subject="Código de verificación de Ucabussines.";
        codigo = getCodigo();
        textMessage="Su código de verificación es: "+codigo;
        correo=new EnviarCorreo(rec,subject,textMessage);
        correo.execute("");
        Intent newIntent =new Intent(EnvioDelCorreo.this, VerificacionDelCodigoParaRegistro.class);
        newIntent.putExtra("codigo",Integer.toString(codigo));
        startActivity(newIntent);
    }

    private String recibirDatosCorreo(){
        Bundle extra = getIntent().getExtras();
        String rec = extra.getString("reci");
        return rec;
    }


}