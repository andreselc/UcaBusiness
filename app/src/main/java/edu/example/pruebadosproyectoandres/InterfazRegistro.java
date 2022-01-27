package edu.example.pruebadosproyectoandres;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import logica.ficheros.GuardarDatos;
import logica.ficheros.ListaUsuarios;
import logica.ficheros.ListaUsuariosClientes;
import logica.ficheros.ListaUsuariosEmpresas;
import logica.usuario.*;
import androidx.appcompat.app.AppCompatActivity;


import static edu.example.pruebadosproyectoandres.InterfazNuevaPublicacion.esconderTeclado;


public class InterfazRegistro extends AppCompatActivity {

    public Empresa empresa; public Cliente cliente;
    public RadioButton userEmpresa, userCliente;

    public boolean validarDatosRegistro(String password1, String password2,
                                        String correo, RadioButton cliente,
                                        RadioButton empresa){

        Password contrasena1 = new Password(password1);
        Password contrasena2 =new Password (password2);
        Correo email= new Correo(correo);
        TextView advertencia = findViewById(R.id.textView6);
        if(email.read(email.getAddress())){
            if (!ListaUsuarios.correoExiste(email.getAddress())) {
                if (contrasena1.ValidarPassword(contrasena1.getPassword())) {
                    if (contrasena1.getPassword().compareTo(contrasena2.getPassword()) == 0) {
                        if ((empresa.isChecked()) || (cliente.isChecked())) {
                            return true;
                        } else {
                            advertencia.setText("Debe seleccionar un tipo de cuenta");
                        }
                    } else {
                        advertencia.setText("Las contraseñas no coinciden");
                    }
                } else {
                    advertencia.setText("La contraseña debe cumplir con las indicaciones");
                    Toast.makeText(InterfazRegistro.this, "Contraseña: mínimo 8 caract., 1 núm., 1 letra mayús y minus, 1 caract. especial", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(InterfazRegistro.this, "El Correo ya existe, intente con otro", Toast.LENGTH_LONG).show();
            }

        }
        else{
            advertencia.setText("Correo no valido");
        }
        return false;
    }

    /*public void escribirArchivo (org.json.simple.JSONArray listaUsuario, Activity a, String nombreFichero) {

        File file =new File(a.getFilesDir(), "files");
        if (!file.exists()){
            file.mkdir();
        }
        try {
            File f =new File (file, nombreFichero);
            FileWriter fileW= new FileWriter(f);
            fileW.append(listaUsuario.toString());
            fileW.flush();
            fileW.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/

    //TODO: Además de agregar lo comentado abajo, se quitaron aspectos irrelevantes
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

        Spinner edad=findViewById(R.id.edadSpinner);
        if ((userCliente.isChecked())&&(edad.getSelectedItem().toString().equals("-18"))) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(InterfazRegistro.this);
            alerta.setMessage("Lo sentimos: sólo mayores de 18 años pueden usar esta aplicación.");
            alerta.setCancelable(false);
            alerta.setPositiveButton("Aceptar",(dialog, which) -> {//datos aceptados
                dialog.cancel();
                finish();
            });
            AlertDialog alertaf = alerta.create();
            alertaf.show();
        }

        if (validarDatosRegistro(password, passwordConfirmacion,correo, userCliente, userEmpresa)) {
            if (userEmpresa.isChecked()) {
                //TODO: Aquí se le asigna la dirección que se escribe en el textbox si eres empresa
                EditText direccionIngresada = findViewById(R.id.editTextTextPostalAddress);
                String direccion=direccionIngresada.getText().toString();
                System.out.println (correo);
                //TODO: Como se cambió el orden del constructos, se cambia aquí también al instanciar
                empresa = new Empresa(password, correo, "e", direccion);
                UserName(empresa);
                ListaUsuariosEmpresas.getListaUsuariosEmpresas().add(empresa);
                    GuardarDatos.procesoGuardadoEmpresas(InterfazRegistro.this);
                    //escribirArchivo(ListaUsuariosEmpresas.getListaUsuariosEmpresasJSON(), InterfazRegistro.this, "usuariosEmpresas.json");
                    Toast.makeText(getApplicationContext(), "¡usuario empresa registrado existosamente!", Toast.LENGTH_SHORT).show();

            }
                if (userCliente.isChecked()) {
                    cliente = new Cliente(password, correo, "c");
                    UserName(cliente);
                    ListaUsuariosClientes.getListaUsuariosClientes().add(cliente);
                        GuardarDatos.procesoGuardadoClientes(InterfazRegistro.this);
                        //escribirArchivo(ListaUsuariosClientes.getListaUsuariosClientesJSON(), InterfazRegistro.this, "usuariosClientes.json");
                        Toast.makeText(getApplicationContext(), "¡usuario cliente registrado existosamente!", Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_registro);
        findViewById(R.id.edadSpinner).setEnabled(false);
        findViewById(R.id.editTextTextPostalAddress).setEnabled(false);


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
                findViewById(R.id.edadSpinner).setEnabled(true);
                findViewById(R.id.editTextTextPostalAddress).setEnabled(false);
                return false;
            }
        });
        findViewById(R.id.radioButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //cliente
                findViewById(R.id.edadSpinner).setEnabled(false);
                findViewById(R.id.editTextTextPostalAddress).setEnabled(true);
                return false;
            }


        });

        userEmpresa = findViewById(R.id.radioButton);
        userCliente = findViewById(R.id.radioButton2);
        /*Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDatosRegistro(passwordIngresado.getText().toString(),
                        passwordIngresado2.getText().toString(), correoIngresado.getText().toString(),
                        userCliente, userEmpresa)) {
                    Intent intent = new Intent(InterfazRegistro.this, EnvioDelCorreo.class);
                    EditText correoIngresado = findViewById(R.id.editTextTextEmailAddress2);
                    String correo = correoIngresado.getText().toString();
                    intent.putExtra("correo", correo);
                    startActivity(intent);
                } else {
                    Toast.makeText(InterfazRegistro.this, "Debe llenar los campos correctamente", Toast.LENGTH_SHORT).show();

                }

            }
        });*/
    }

    public void UserName(Usuario usuario){
        String subCadena = usuario.getEmailNoAt();
        subCadena = subCadena.substring(1,5);
        usuario.setUserName(subCadena+"ucauser");
    }
}
