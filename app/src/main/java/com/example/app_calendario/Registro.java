package com.example.app_calendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText NombreEt, CorreoEt,ContraseñaEt, ConfirmarContraseña;
    Button RegistaraUsuario;
    TextView TengounaCuenta;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    //

    String nombre = "", correo= "", password = "", confirmarpasword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        //crear flecha hacia atras (troceder a la actividad anterior)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        NombreEt = findViewById(R.id.NombreEt);
        CorreoEt = findViewById(R.id.CorreoEt);
        ContraseñaEt = findViewById(R.id.ContraseñaEt);
        ConfirmarContraseña = findViewById(R.id.ConfirmarContraseña);
        RegistaraUsuario = findViewById(R.id.RegistaraUsuario);
        TengounaCuenta = findViewById(R.id.TengounaCuenta);


        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        RegistaraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                validarDatos();
            }
        });

        TengounaCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                startActivity(new Intent(Registro.this, Login.class));
            }
        });

    }

    private void validarDatos(){
        nombre = NombreEt.getText().toString();
        correo = CorreoEt.getText().toString();
        password = ContraseñaEt.getText().toString();
        confirmarpasword = ConfirmarContraseña.getText().toString();

        //comprara si el campo nombre esta vacio
        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this,"Ingrese Nombre", Toast.LENGTH_SHORT).show();
        }
        //valida el @ y el .com
        else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this,"Ingrese Correo", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(confirmarpasword)){
            Toast.makeText(this,"Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        }
        //validad si las contraseñas son iguales
        else if (password.equals(confirmarpasword)) {
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();

        } else {
            CrearCuenta();
        }
    }

    private void CrearCuenta() {

        //mostrar mensaje

        progressDialog.setMessage("Creando Cuenta ...");
        progressDialog.show();

        //Crear un usuario en Firebase
        firebaseAuth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    //on success nos permite gestionar el codigo necesario para realizar el registro
                    public void onSuccess(AuthResult authResult) {
                        //metodo que permira el registro
                        GuardarInformacion();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    //onFailure : detecta si el registro no es exitoso
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });


    }

    private void GuardarInformacion() {

        progressDialog.setMessage("Guardando su inofmracion");
        progressDialog.dismiss();

        //obtener la identificacion de usuario actual
        String uid = firebaseAuth.getUid();

        //Desisgnar claves unicas
        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("correo",correo);
        Datos.put("nombres",nombre);
        Datos.put("password",password);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Registro.this, "Cuenta Creada con Exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this,MenuPrincipal.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        //mandara a la actividad anterior
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}