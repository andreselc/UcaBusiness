package edu.example.pruebadosproyectoandres;

import static android.content.ContentValues.TAG;
import static java.lang.String.valueOf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerificacionDelCodigoParaRegistro extends MainActivity  {

    private String codigoAct;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion_del_codigo_para_registro);
        Intent myIntent = getIntent();
        if(myIntent.hasExtra("codigo"))
            codigoAct = myIntent.getStringExtra("codigo");
    }

    public void buttonPressVerificar(View view){
        EditText codigo= findViewById(R.id.Codigo);
        String codigoIngresado = codigo.getText().toString();

        Log.d(TAG, "codigo enviado: " + codigoAct + " -- codigo ingresado: " + codigoIngresado);
        if(codigoIngresado.equals(codigoAct)){
            TextView advertencia= findViewById(R.id.advertencia);
            advertencia.setText("Codigo correcto");
            Intent intent= getIntent();
            String correo=intent.getStringExtra("correo");
            Intent myIntent = new Intent(this, MostrarDatosDespuesDeRegistro.class);
            myIntent.putExtra("correo",correo);
            startActivity(myIntent);
        }
        else{
            Toast.makeText(VerificacionDelCodigoParaRegistro.this, "NO ES IGUAL :< ", Toast.LENGTH_SHORT).show();
            TextView advertencia= findViewById(R.id.advertencia);
        }
    }
}