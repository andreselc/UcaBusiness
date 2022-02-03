package edu.example.pruebadosproyectoandres;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import logica.ficheros.GuardarDatos;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.usuario.Cliente;
import logica.usuario.Empresa;
import logica.usuario.Encrypt;
import org.json.JSONException;

public class InterfazModContrasena extends AppCompatActivity {

    private String userID="n/a", oldPass;
    private Empresa emp;
    private Cliente cl;
    private EditText oldPassword, newPassword, verifyPassword;
    private Encrypt encriptar= new Encrypt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_mod_contrasena);
        //esto permite obtener los datos del primer activity
        Bundle b= getIntent().getExtras();
        userID=b.getString("userID");
        //cargando los valores del usuario
        emp = ListaUsuariosEmpresas.buscarUsuarioEmpresa(userID);
        cl = ListaUsuariosClientes.buscarUsuarioClientes(userID);
        //
        obtenerContrasena();
        //asignar valor de oldpassword, newpassword y verifypassword aqui
        oldPassword=findViewById(R.id.editTextTextPassword4);
        newPassword=findViewById(R.id.editTextTextPassword5);
        verifyPassword=findViewById(R.id.editTextTextPassword6);
        //
        System.out.println(oldPass);


    }

    private void obtenerContrasena(){
        if (emp!=null){
            //es empresa
            oldPass=emp.getPassword();
        }
        else if (cl!=null){
            //es cliente
            oldPass=cl.getPassword();

        }
    }

    public void buttonPressModificar(View view){
        if (validarClave()) alerta("Contraseña modificada exitosamente.");

    }

    private void alerta(String mensaje){

        AlertDialog.Builder builder = new AlertDialog.Builder(InterfazModContrasena.this);

        //mensaje de la alerta
        builder.setMessage(mensaje);
        //título de la alerta
        builder.setTitle("Alerta");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", (dialog, which) -> {//datos aceptados
            dialog.cancel();
            try {
                //aqui se modifica el usuario
                cambiarContrasena();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            finish();

        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void cambiarContrasena() throws JSONException {
        //para agregar mas datos, sencillamente
        //se deben agregar los campos
        //y obtener los datos del objeto correspondiente.



        if (emp!=null){
            //el usuario es empresa, se cambian los datos
            //agregar los datos nuevos:
            emp.setPassword(newPassword.getText().toString());

            //1: se elimina el anterior
            GuardarDatos.eliminarEmpresa(userID);
            //2: se agrega el nuevo
            ListaUsuariosEmpresas.getListaUsuariosEmpresas().add(emp);
            //3: se guarda en el archivo
            GuardarDatos.procesoModEmpresas(InterfazModContrasena.this,emp);

        }
        else if (cl!=null){
            //el usuario es cliente, se cambian los datos
            //agregar los datos nuevos:
            cl.setPassword(newPassword.getText().toString());

            //1: se elimina el anterior
            GuardarDatos.eliminarCliente(userID);
            //2: se agrega el nuevo
            ListaUsuariosClientes.getListaUsuariosClientes().add(cl);
            //3: se guarda en el archivo
            GuardarDatos.procesoModClientes(InterfazModContrasena.this, cl);



        }}

    private boolean validarClave(){
        String temp=oldPassword.getText().toString();
        if (temp.equals(oldPass)){
            //la contraseña vieja coincide con la guardada
            if (newPassword.getText().toString().equals(verifyPassword.getText().toString())){
                //ambas nuevas coinciden
                //aqui se procede
                return true;
            }
            else Toast.makeText(InterfazModContrasena.this,"Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(InterfazModContrasena.this,"La contraseña anterior es incorrecta", Toast.LENGTH_SHORT).show();
        return false;}


}