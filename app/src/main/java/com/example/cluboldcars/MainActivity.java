package com.example.cluboldcars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import Entity.Usuario;
import Metodos.UsuarioValidator;

public class MainActivity extends AppCompatActivity {


    TextView cadastrar,entrar;

    EditText email,senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("LOGIN");
        getSupportActionBar().hide();

        email = findViewById(R.id.txtEmail);
        senha = findViewById(R.id.txtSenha);
        cadastrar = findViewById(R.id.txtCadastrar);
        entrar = findViewById(R.id.txtEntrar);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario pessoa = UsuarioValidator.logar(getApplicationContext(), email,senha);

                if(pessoa.getEmail() == null){
                    Toast.makeText(getApplicationContext(), "EMAIL ou SENHA invalidos",Toast.LENGTH_SHORT).show();
                }else{
                    if(pessoa.getAtivo() == 1){
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        intent.putExtra("usuario", pessoa);
                        startActivity(intent);
                    }else{
                       //Intent intent = new Intent(Login.this, Desativado.class);
                       // intent.putExtra("pessoa", pessoa);
                       // startActivity(intent);
                    }

                }
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroUsuario.class);
                startActivity(intent);
            }
        });



    }
}