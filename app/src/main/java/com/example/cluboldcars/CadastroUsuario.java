package com.example.cluboldcars;

import static Metodos.ImagemBase64.convertImageToBase64;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import DAO.DAO;
import Entity.Usuario;
import Metodos.ImagemBase64;
import Metodos.ModalDialogFragment;
import Metodos.UsuarioValidator;

public class CadastroUsuario extends AppCompatActivity {

    EditText txtNome, txtCpf, txtTelefone, txtEmail, txtSenha, txtSenhaConfirma;
    CheckBox box;
    Boolean check;
    private Calendar calendar;
    Button btnTermos, btnCadastro, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_usuario);

        getSupportActionBar().hide();

        btnTermos = findViewById(R.id.btnTermos);
        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtCpf = findViewById(R.id.txtCpf);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtSenhaConfirma = findViewById(R.id.txtSenha2);
        btnCadastro = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);
        box = findViewById(R.id.checkBox);
        check = false;

        btnVoltar.setOnClickListener(v -> onBackPressed());
        btnTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalDialogFragment modalDialog = new ModalDialogFragment();
                modalDialog.show(getSupportFragmentManager(), "modal");
            }
        });

        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check = true;
                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aviso = UsuarioValidator.validaDados(getApplicationContext(), txtNome, txtCpf, txtTelefone, txtEmail, txtSenha, txtSenhaConfirma, check);
                if (aviso.equals("ok")) {
                    try {
                        Bitmap bitmap = ImagemBase64.getBitmapFromDrawable(CadastroUsuario.this, R.drawable.img_1);
                        String base64Image = convertImageToBase64(getResources(), R.drawable.icon_usuario);
                        Usuario usuario = new Usuario();
                        DAO dao = new DAO(getApplicationContext());
                        usuario.setNome(txtNome.getText().toString());
                        usuario.setCpf(txtCpf.getText().toString());
                        usuario.setTelefone(txtTelefone.getText().toString());
                        usuario.setEmail(txtEmail.getText().toString());
                        usuario.setSenha(txtSenha.getText().toString());
                        usuario.setAdm(2);
                        usuario.setAtivo(1);
                        usuario.setFoto(base64Image);

                        dao.inserirUsuario(usuario);

                        aviso = "CADASTRO REALIZADO COM SUCESSO";

                        // Mostrar mensagem de sucesso e finalizar a atividade atual
                        Toast.makeText(getApplicationContext(), aviso, Toast.LENGTH_SHORT).show();
                        finish();

                    } catch (Exception e) {
                        aviso = e.toString();
                        Toast.makeText(getApplicationContext(), aviso, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), aviso, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
