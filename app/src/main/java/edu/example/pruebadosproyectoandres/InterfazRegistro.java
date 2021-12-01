package edu.example.pruebadosproyectoandres;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import logica.ficheros.GuardarDatos;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.usuario.*;

import androidx.appcompat.app.AppCompatActivity;


public class InterfazRegistro extends AppCompatActivity {

    Empresa empresa; Cliente cliente;
    RadioButton userEmpresa, userCliente;

    public boolean validarDatosRegistro(String password1, String password2,
                                        String correo, RadioButton cliente,
                                        RadioButton empresa){

        Password contrasena1 = new Password(password1);
        Password contrasena2 =new Password (password2);
        Correo email= new Correo(correo);
        TextView advertencia = findViewById(R.id.textView6);
        if(email.read(email.getAddress())){
            if(contrasena1.ValidarPassword(contrasena1.getPassword())){
                if(contrasena1.getPassword().compareTo(contrasena2.getPassword())==0){
                    if((empresa.isChecked()) || (cliente.isChecked())){
                        return true;
                    }
                    else{
                        advertencia.setText("Debe seleccionar un tipo de empresa");
                    }
                }
                else{
                    advertencia.setText("Las contraseñas no coinciden");
                }
            }
            else{
                advertencia.setText("La contraseña debe cumplir con las indicaciones");
            }
        }
        else{
            advertencia.setText("Correo no valido");
        }
        return false;
    }

     public void buttonPress(View view) {
        EditText correoIngresado = findViewById(R.id.editTextTextEmailAddress2);
        EditText passwordIngresado = findViewById (R.id.editTextTextPassword2);
        EditText passwordIngresado2 = findViewById (R.id.editTextTextPassword3);
        String correo, password, passwordConfirmacion;
        correo= correoIngresado.getText().toString();
        password= passwordIngresado.getText().toString();
        passwordConfirmacion= passwordIngresado2.getText().toString();

        userEmpresa= findViewById(R.id.radioButton);
        userCliente= findViewById(R.id.radioButton2);

        if (validarDatosRegistro(password, passwordConfirmacion,correo, userCliente, userEmpresa)) {
            if (userEmpresa.isChecked()) {
                empresa = new Empresa(correo, password, 'e');
                ListaUsuariosEmpresas.getListaUsuariosEmpresas().add(empresa);
                System.out.println("Empresa Llena");
                GuardarDatos.procesoGuardadoEmpresas();
                Toast.makeText(getApplicationContext(), "¡usuario empresa registrado existosamente!", Toast.LENGTH_SHORT).show();
            }
            if (userCliente.isChecked()) {
                cliente = new Cliente(password, correo, 'c');
                ListaUsuariosClientes.getListaUsuariosClientes().add(cliente);
                /*if (ListaUsuariosClientes.getListaUsuariosClientes().isEmpty())
                    Toast.makeText(getApplicationContext(), "La lista de clientes está vacía", Toast.LENGTH_SHORT).show();
                else{*/
                System.out.println("Cliente Lleno");
                GuardarDatos.procesoGuardadoClientes();
                Toast.makeText(getApplicationContext(), "¡usuario cliente registrado existosamente!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_registro);
    }

}
