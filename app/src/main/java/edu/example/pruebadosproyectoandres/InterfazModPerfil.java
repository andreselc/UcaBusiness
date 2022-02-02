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
import logica.ficheros.GuardarDatos;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.usuario.Cliente;
import logica.usuario.Empresa;
import org.json.simple.JSONObject;

import java.security.Guard;

public class InterfazModPerfil extends AppCompatActivity {
    private String userID="n/a";
    private TextView  direccionLabel, whatsapp,
            contactoLabel, visibleLabel, edadLabel;
    private EditText direccion, contacto;
    private Switch visible;
    private Spinner edad;
    private Empresa emp;
    private Cliente cl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getIntent().getExtras();
        userID=b.getString("userID");
        System.out.println("UserID en modificar: "+userID);
        setContentView(R.layout.activity_interfaz_mod_perfil);

        //cargando los valores del usuario
        emp = ListaUsuariosEmpresas.buscarUsuarioEmpresa(userID);
        cl = ListaUsuariosClientes.buscarUsuarioClientes(userID);


        //estableciendo los objetos
        direccion = findViewById(R.id.editTextTextPostalAddress);
        direccionLabel = findViewById(R.id.textView24);
        contacto=findViewById(R.id.editTextPhone);
        visible=findViewById(R.id.switch2);
        edad=findViewById(R.id.spinner);
        contactoLabel=findViewById(R.id.textView26);
        visibleLabel=findViewById(R.id.textView27);
        edadLabel=findViewById(R.id.textView28);

        //cargando lo visible
        if (emp!=null){
            //el usuario es empresa, se cambia dir, telefono y visib
            edad.setVisibility(View.INVISIBLE);
            edadLabel.setVisibility(View.INVISIBLE);
        }
        else if (cl!=null){
            //el usuario es cliente, se cambian la edad
            direccion.setVisibility(View.GONE);
            direccionLabel.setVisibility(View.GONE);
            contacto.setVisibility(View.GONE);
            visible.setVisibility(View.GONE);
            contactoLabel.setVisibility(View.GONE);
            visibleLabel.setVisibility(View.GONE);
     }


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
        //para agregar mas datos, sencillamente
        //se deben agregar los campos
        //y obtener los datos del objeto correspondiente.

        if (emp!=null){
            //el usuario es empresa, se cambian los datos
            //agregar los datos nuevos:
            emp.setDireccion(direccion.getText().toString());
            emp.setDatosContacto(contacto.getText().toString());
            emp.setLinkWhattsApp(emp.generarLink());
            if (visible.isChecked()){//es publico
                emp.setPrivacidad("public");
            }
            else emp.setPrivacidad("private");

            //1: se elimina el anterior
            GuardarDatos.eliminarEmpresa(userID);
            //2: se agrega el nuevo
            ListaUsuariosEmpresas.getListaUsuariosEmpresas().add(emp);
            //3: se guarda en el archivo
            GuardarDatos.procesoModEmpresas(InterfazModPerfil.this);

        }
        else if (cl!=null){
            //el usuario es cliente, se cambian los datos
            //agregar los datos nuevos:
            if (edad.getSelectedItem().equals("-18")){
                cl.setEdad(18);
            }
            else cl.setEdad(Long.parseLong(edad.getSelectedItem().toString()));
            //1: se elimina el anterior y se crea el nuevo json
            GuardarDatos.eliminarCliente(userID);
            //2: se agrega el nuevo
            ListaUsuariosClientes.getListaUsuariosClientes().add(cl);
            //3: se guarda en el archivo
            GuardarDatos.procesoModClientes(InterfazModPerfil.this);



        }


    }

}