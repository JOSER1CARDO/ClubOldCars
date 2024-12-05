package com.example.cluboldcars.fragments;

import static Metodos.ImagemBase64.convertImageToBase64;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cluboldcars.CadastroUsuario;
import com.example.cluboldcars.R;

import java.io.InputStream;

import DAO.DAO;
import Entity.Usuario;
import Metodos.ImagemBase64;
import Metodos.UsuarioValidator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdmFragment extends Fragment {

    private int id;

    private static final String ARG_ID = "id";
    private ActivityResultLauncher<Intent> resultLauncher;
    private String base64 = "";


    public HomeAdmFragment() {
        // Required empty public constructor
    }


    public static HomeAdmFragment newInstance(int id) {
        HomeAdmFragment fragment = new HomeAdmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home_adm, container, false);


        DAO dao = new DAO(getContext());
        Usuario usuario = new Usuario();

        usuario = dao.usuarioId(id);


        ImageView foto  =view.findViewById(R.id.imgFoto);
        ImageView btnFoto  =view.findViewById(R.id.btnFoto);
        EditText nome = view.findViewById(R.id.txtNome);
        EditText cpf = view.findViewById(R.id.txtCpf);
        EditText email = view.findViewById(R.id.txtEmail);
        EditText telefone = view.findViewById(R.id.txtTelefone);
        EditText senha = view.findViewById(R.id.txtSenha);
        EditText confirmasenha = view.findViewById(R.id.txtSenha2);
        ImageView btnSalvar = view.findViewById(R.id.btnSalvar);



        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(usuario.getFoto());
        foto.setImageBitmap(bitmap);

        nome.setText(usuario.getNome());
        cpf.setText(usuario.getCpf());
        email.setText(usuario.getEmail());
        telefone.setText(usuario.getTelefone());
        senha.setText(usuario.getSenha());
        confirmasenha.setText(usuario.getSenha());

        Usuario finalUsuario = usuario;
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aviso = UsuarioValidator.alteraDados(finalUsuario, getContext(),nome,cpf,telefone, email, senha, confirmasenha, true);
                if(aviso.equals("ok")){
                    try {

                        String base64Image =  base64;
                        Usuario usuario1 = new Usuario();
                        DAO dao = new DAO(getContext());
                        usuario1.setId(finalUsuario.getId());
                        usuario1.setNome(nome.getText().toString());
                        usuario1.setCpf(cpf.getText().toString());
                        usuario1.setTelefone(telefone.getText().toString());
                        usuario1.setEmail(email.getText().toString());
                        usuario1.setSenha(senha.getText().toString());
                        usuario1.setAdm(finalUsuario.getAdm());
                        usuario1.setAtivo(1);
                        usuario1.setFoto(base64Image);

                        dao.atualizarPessoa(usuario1);

                        Toast.makeText(getContext(), "Perfil salvo", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();

                    }catch (Exception e){
                        aviso = e.toString();
                    }
                }
            }
        });





        btnFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(intent);
        });







        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                Uri selectedImageUri = data.getData();
                                try {
                                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    // Reduzir a resolução da imagem
                                    Bitmap resizedBitmap = ImagemBase64.resizeBitmap(bitmap, 400, 400);
                                    String base64Image = ImagemBase64.bitmapToBase64(resizedBitmap);
                                    base64 = base64Image;


                                    // Definir a imagem selecionada no ImageView
                                    foto.setImageBitmap(resizedBitmap);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

        return view;
    }
}