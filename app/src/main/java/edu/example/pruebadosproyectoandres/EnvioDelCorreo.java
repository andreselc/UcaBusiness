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

public class EnvioDelCorreo extends Activity implements View.OnClickListener {

    private static final String TAG = "olaaaaaa";
    public String rec,subject,textMessage;
    public Session session=null;
    public ProgressDialog progressDialog =null;
    public Context context =null;
    public int codigo;

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
        rec=intent.getStringExtra("correo");
        context = this;
        Button login = (Button) findViewById(R.id.button4);

        login.setOnClickListener((View.OnClickListener) this);
    }
    @Override
    public void onClick(View v) {
        subject="Código de verificación de Ucabussines.";
        codigo = getCodigo();
        textMessage="Su código de verificación es: "+codigo;
        Properties properties =new Properties();
        try {
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port","465");
            properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.port","465");
        }catch (Exception e){
            Log.d(TAG, "onClick: ");
        }

        session = Session.getDefaultInstance(properties, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("ucabussines@gmail.com","ucabussines123!");
            }
        });

        progressDialog= ProgressDialog.show(context,"","Correo enviandose...",true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }
    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("ucabussines@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Codigo enviado", Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(EnvioDelCorreo.this, VerificacionDelCodigoParaRegistro.class);
            newIntent.putExtra("codigo", String.valueOf(codigo));
            startActivity(newIntent);
            newIntent.putExtra("correo",rec);
        }
    }
    public void buttonPress(View v){
        Intent newIntent =new Intent(EnvioDelCorreo.this, VerificacionDelCodigoParaRegistro.class);
        startActivity(newIntent);
    }
}