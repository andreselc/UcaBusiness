package edu.example.pruebadosproyectoandres;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class InterfazModPerfil extends AppCompatActivity {
    private String userID="n/a";
    TextView  direccionLabel, whatsapp,
            contactoLabel, visibleLabel, edadLabel;
    EditText direccion, contacto;
    Switch visible;
    Spinner edad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getIntent().getExtras();
        userID=b.getString("userID");
        setContentView(R.layout.activity_interfaz_mod_perfil);

        //estableciendo los objetos
        direccion = findViewById(R.id.editTextTextPostalAddress);
        direccionLabel = findViewById(R.id.textView24);
        contacto=findViewById(R.id.editTextPhone);
        visible=findViewById(R.id.switch2);
        edad=findViewById(R.id.spinner);
        contactoLabel=findViewById(R.id.textView26);
        visibleLabel=findViewById(R.id.textView27);
        edadLabel=findViewById(R.id.textView28);


    }

    public void regresar(View view){
        alerta("Datos guardados");
    }

    //con esto vuelves al perfil
    private void alerta(String mensaje){

        AlertDialog.Builder builder = new AlertDialog.Builder(InterfazModPerfil.this);

        //mensaje de la alerta
        builder.setMessage(mensaje);
        //título de la alerta
        builder.setTitle("Alerta");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialog, which) -> {//datos aceptados
            dialog.cancel();
            //aqui modificar el usuario
            modificarPerfil();
            finish();

        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }


    private void modificarPerfil(){

        //llenar esto con el reemplazo y guardado

    }

}