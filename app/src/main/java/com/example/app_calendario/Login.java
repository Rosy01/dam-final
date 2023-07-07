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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText CorreoLogin, PassLogin;
    Button Btn_Logeo;
    TextView UsuarioNuevo;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    //validar datos
    String correo = "", password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        //crear flecha hacia atras (troceder a la actividad anterior)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        //inicializar
        CorreoLogin = findViewById(R.id.CorreoLogin);
        PassLogin = findViewById(R.id.PassLogin);
        Btn_Logeo = findViewById(R.id.Btn_Logeo);
        UsuarioNuevo = findViewById(R.id.UsuarioNuevo);
        
        firebaseAuth = FirebaseAuth.getInstance();
        
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Espere por favor");/**/
        progressDialog.setCanceledOnTouchOutside(false);
        
        Btn_Logeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarDatos();
            }
        });

        UsuarioNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registro.class));

            }
        });


    }

    private void ValidarDatos() {
        //obtener datos del edit text correo y pass
        correo = CorreoLogin.getText().toString();
        password = PassLogin.getText().toString();


       //validar correo (escribir correo valido)
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Correo Invalido", Toast.LENGTH_SHORT).show();
        }

        //si el campo contraseña esta vacio
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese Contraseña", Toast.LENGTH_SHORT).show();
        }
        else {
            LoginDeUsuario();
        }
    }

    private void LoginDeUsuario() {
        progressDialog.setMessage("Iniciando Sesion ...");
        progressDialog.show();

        //iniciar sesion con un correo y contraseña
        firebaseAuth.signInWithEmailAndPassword(correo, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        startActivity(new Intent(Login.this, MenuPrincipal.class));

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