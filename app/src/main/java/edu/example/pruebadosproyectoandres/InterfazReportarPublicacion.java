package edu.example.pruebadosproyectoandres;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class InterfazReportarPublicacion extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_reportar_publicacion);

        Spinner currSpinner = findViewById(R.id.spinner2);
        TextView currText = findViewById(R.id.motivoReporteTitle);

        currText.setText("Introduzca el motivo de su reporte ");
        if(currSpinner.getSelectedItem().toString().equals("Otro")){
            EditText currEditText = findViewById(R.id.motivoReporte);
            currEditText.setVisibility(View.VISIBLE);
        }
    }

    public void onReporteClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //mensaje de la alerta
        builder.setMessage("Gracias por su reporte, UcaBusiness se encargará de estudiar la situación");
        //título de la alerta
        builder.setTitle("Alerta");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialog, which) -> {//datos aceptados
            dialog.cancel();
            finish();
        });
        builder.show();
    }
}
