package edu.example.pruebadosproyectoandres;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import logica.ficheros.GuardarDatos;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.usuario.*;
import androidx.appcompat.app.AppCompatActivity;


import static edu.example.pruebadosproyectoandres.InterfazNuevaPublicacion.esconderTeclado;

import java.io.Serializable;


public class InterfazRegistro extends AppCompatActivity {

    public Empresa empresa; public Cliente cliente;
    public RadioButton userEmpresa, userCliente;

    //NUEVOOOOOOOOOOOOO
    public boolean validarDatosContacto(String datos){
        if((datos.length() ==10) && (datos.matches("[+-]?\\d*(\\.\\d+)?"))){
            return true;
        }
        else{
            return false;
        }
    }


    public boolean validarDatosRegistro(String password1, String password2,
                                        String correo, RadioButton cliente,
                                        RadioButton empresa){



        Password contrasena1 = new Password(password1);
        Password contrasena2 =new Password (password2);
        Correo email= new Correo(correo);
        TextView advertencia = findViewById(R.id.textView6);
        EditText datosContacto= findViewById(R.id.datosContacto);

        if(!ListaUsuariosEmpresas.correoExisteEnEmpresasJSON(email.getAddress()) && !ListaUsuariosClientes.correoExisteEnClientesJSON(email.getAddress()))
        if (email.read(email.getAddress())) {
            if (contrasena1.ValidarPassword(contrasena1.getPassword())) {
                if (contrasena1.getPassword().compareTo(contrasena2.getPassword()) == 0) {
                    if ((empresa.isChecked()) || (cliente.isChecked())) {
                        if((empresa.isChecked()) && (validarDatosContacto(datosContacto.getText().toString()))){
                            return true;
                        }
                        else {
                            if((empresa.isChecked()) && !(validarDatosContacto(datosContacto.getText().toString()))){
                                advertencia.setText("Datos de contacto invalidos");
                            }
                            else {
                                return true;
                            }
                        }
                    } else {
                        advertencia.setText("Debe seleccionar un tipo de usuario");
                    }
                } else {

                    advertencia.setText("Las contraseñas no coinciden");
                }
            } else {
                advertencia.setText("La contraseña debe cumplir con las indicaciones");
            }
        } else {
            advertencia.setText("Correo no valido");
        }
        else
      {
          advertencia.setText("¡Usuario ya registrado! Intente con un nuevo usuario");
      }
        return false;
    }

    //TODO: Además de agregar lo comentado abajo, se quitaron aspectos irrelevantes
    public void buttonPress(View view) {
        EditText correoIngresado = findViewById(R.id.editTextTextEmailAddress2);
        EditText passwordIngresado = findViewById(R.id.editTextTextPassword2);
        EditText passwordIngresado2 = findViewById(R.id.editTextTextPassword3);
        String correo, password, passwordConfirmacion;
        correo = correoIngresado.getText().toString();
        password = passwordIngresado.getText().toString();
        passwordConfirmacion = passwordIngresado2.getText().toString();

        userEmpresa = findViewById(R.id.radioButton);
        userCliente = findViewById(R.id.radioButton2);

        Spinner edad = findViewById(R.id.edadSpinner);
        if ((userCliente.isChecked()) && (edad.getSelectedItem().toString().equals("-18"))) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(InterfazRegistro.this);
            alerta.setMessage("Lo sentimos: sólo mayores de 18 años pueden usar esta aplicación.");
            alerta.setCancelable(false);
            alerta.setPositiveButton("Aceptar", (dialog, which) -> {//datos aceptados
                dialog.cancel();
                finish();
            });
            AlertDialog alertaf = alerta.create();
            alertaf.show();
        }
        else{
            if (validarDatosRegistro(password, passwordConfirmacion, correo, userCliente, userEmpresa)) {
                if (userEmpresa.isChecked()) {
                    //TODO: Aquí se le asigna la dirección que se escribe en el textbox si eres empresa
                    EditText direccionIngresada = findViewById(R.id.editTextTextPostalAddress2);
                    EditText datosContactoIngresado = findViewById(R.id.datosContacto);
                    String direccion = direccionIngresada.getText().toString();
                    String datosContacto = datosContactoIngresado.getText().toString();
                    RadioButton publico = findViewById(R.id.prueba2);
                    RadioButton privado = findViewById(R.id.prueba);
                    if (publico.isChecked()) {
                        empresa = new Empresa(password, correo, "e", direccionIngresada.getText().toString(), datosContactoIngresado.getText().toString(), "public");
                        UserName(empresa);
                        String aver=empresa.getEmail();
                        Intent newIntent =new Intent(InterfazRegistro.this, EnvioDelCorreo.class);
                        newIntent.putExtra("reci",aver);
                        startActivity(newIntent);
                    } else {
                        if (privado.isChecked()) {
                            empresa = new Empresa(password, correo, "e", direccionIngresada.getText().toString(), datosContactoIngresado.getText().toString(), "private");
                            UserName(empresa);
                            String aver=empresa.getEmail();
                            Intent newIntent =new Intent(InterfazRegistro.this, EnvioDelCorreo.class);
                            newIntent.putExtra("reci",aver);
                            startActivity(newIntent);

                        } else {
                            TextView advertencia = findViewById(R.id.textView4);
                            advertencia.setText("Debe seleccionar una opción");
                        }
                    }
                }
                if (userCliente.isChecked()) {
                    cliente = new Cliente(password, correo, "c", Integer.parseInt(String.valueOf(edad.getSelectedItem())));
                    UserName(cliente);
                    String aver=cliente.getEmail();
                    Intent newIntent =new Intent(InterfazRegistro.this, EnvioDelCorreo.class);
                    newIntent.putExtra("reci",aver);
                    startActivity(newIntent);
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_registro);
        findViewById(R.id.edadSpinner).setEnabled(false);
        findViewById(R.id.datosContacto).setEnabled(false);
        findViewById(R.id.editTextTextPostalAddress2).setEnabled(false);
        findViewById(R.id.radioGroup2).setEnabled(false);
        findViewById(R.id.prueba).setEnabled(false);
        findViewById(R.id.prueba2).setEnabled(false);
        //para esconder el teclado
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                esconderTeclado(InterfazRegistro.this);
                return false;
            }
        });
        findViewById(R.id.radioButton2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //cliente
                findViewById(R.id.datosContacto).setEnabled(false);
                findViewById(R.id.editTextTextPostalAddress2).setEnabled(false);
                findViewById(R.id.radioGroup2).setEnabled(false);
                findViewById(R.id.prueba).setEnabled(false);
                findViewById(R.id.prueba2).setEnabled(false);
                findViewById(R.id.edadSpinner).setEnabled(true);
                return false;
            }
        });
        findViewById(R.id.radioButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //cliente
                findViewById(R.id.edadSpinner).setEnabled(false);
                findViewById(R.id.datosContacto).setEnabled(true);
                findViewById(R.id.editTextTextPostalAddress2).setEnabled(true);
                findViewById(R.id.radioGroup2).setEnabled(true);
                findViewById(R.id.prueba).setEnabled(true);
                findViewById(R.id.prueba2).setEnabled(true);

                return false;
            }


        });

        userEmpresa = findViewById(R.id.radioButton);
        userCliente = findViewById(R.id.prueba2);
    }

    public void UserName(Usuario usuario) {
        String subCadena = usuario.getEmailNoAt();
        subCadena = subCadena.substring(1, 5);
        usuario.setUserName(subCadena + "ucauser");
    }
}