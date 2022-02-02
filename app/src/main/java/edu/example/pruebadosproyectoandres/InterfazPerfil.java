package edu.example.pruebadosproyectoandres;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.usuario.Cliente;
import logica.usuario.Empresa;

public class InterfazPerfil extends AppCompatActivity {

    private String userID="n/a";
    TextView correo, direccion, direccionLabel, contacto, whatsapp, visible,
            contactoLabel, whatsappLabel, visibleLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //esto permite obtener los datos del primer activity
        Bundle b= getIntent().getExtras();
        userID=b.getString("userID");
        System.out.println("User ID en On Create - perfil: "+userID);
        setContentView(R.layout.activity_interfaz_perfil);
        //cargando los textviews
        correo = findViewById(R.id.textView14);
        direccion = findViewById(R.id.textView16);
        direccionLabel = findViewById(R.id.textView15);
        contacto=findViewById(R.id.textView20);
        whatsapp=findViewById(R.id.textView18);
        visible=findViewById(R.id.textView22);
        contactoLabel=findViewById(R.id.textView19);
        whatsappLabel=findViewById(R.id.textView17);
        visibleLabel=findViewById(R.id.textView21);
        cargarPerfil();
    }

    private void cargarPerfil(){
        Empresa emp = ListaUsuariosEmpresas.buscarUsuarioEmpresa(userID);
        Cliente cl = ListaUsuariosClientes.buscarUsuarioClientes(userID);

        //para agregar mas datos, sencillamente
        //se deben agregar los campos
        //y obtener los datos del objeto correspondiente.

        if (emp!=null){
            //el usuario es empresa, se muestra la direccion
            //empresa utiliza lo siguiente:
            //datosContacto,linkWhattsApp,direccion,privacidad;
            correo.setText(emp.getEmail());
            direccion.setText(emp.getDireccion());
            contacto.setText(emp.getDatosContacto());
            whatsapp.setText(emp.getLinkWhattsApp());
            visible.setText(emp.getprivacidad());

        }
        else if (cl!=null){
            //el usuario es cliente, se esconde la direccion
            correo.setText(cl.getEmail());
            direccion.setText(Math.toIntExact(cl.getEdad()));
            direccionLabel.setText("Edad");
            contacto.setVisibility(View.INVISIBLE);
            whatsapp.setVisibility(View.INVISIBLE);
            visible.setVisibility(View.INVISIBLE);
            contactoLabel.setVisibility(View.INVISIBLE);
            whatsappLabel.setVisibility(View.INVISIBLE);
            visibleLabel.setVisibility(View.INVISIBLE);

        }

    }
}