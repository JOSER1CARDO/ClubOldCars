package com.example.cluboldcars.fragments;

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

import com.example.cluboldcars.R;

import java.io.InputStream;

import DAO.DAO;
import Entity.Destaque;
import Entity.Evento;
import Metodos.ImagemBase64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NovoDestaqueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NovoDestaqueFragment extends Fragment {


    private static final String ARG_ID = "id";

    private int Id;

    private ImageView imagem;
    private String base64 = "";
    private ActivityResultLauncher<Intent> resultLauncher;


    public NovoDestaqueFragment() {
        // Required empty public constructor
    }


    public static NovoDestaqueFragment newInstance(int id ) {
        NovoDestaqueFragment fragment = new NovoDestaqueFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Id = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novo_destaque, container, false);


        DAO dao = new DAO(getContext());
        EditText titulo = view.findViewById(R.id.txtTitulo);
        EditText descricao = view.findViewById(R.id.txtDescricao);
        EditText premio = view.findViewById(R.id.txtPremio);
        EditText posicao = view.findViewById(R.id.txtPosicao);

        Evento evento = new Evento();
        evento.setId(Id);

        ImageView criar = view.findViewById(R.id.img_criar);

        imagem = view.findViewById(R.id.btnImagen);

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
                                    Bitmap resizedBitmap = ImagemBase64.resizeBitmap(bitmap, 800, 800);
                                    String base64Image = ImagemBase64.bitmapToBase64(resizedBitmap);
                                    base64 = base64Image;

                                    // Definir a imagem selecionada no ImageView
                                    imagem.setImageBitmap(resizedBitmap);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

        imagem.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(intent);
        });

        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Destaque destaque = new Destaque();
                destaque.setTitulo(titulo.getText().toString());
                destaque.setPremio(premio.getText().toString());
                destaque.setPosicao(posicao.getText().toString());
                destaque.setDescricao(descricao.getText().toString());
                destaque.setFoto(base64);
                destaque.setEvento(evento);

                dao.inserirDestaque(destaque);

                // Voltar para a tela anterior
                getActivity().onBackPressed();


            }
        });








        return view;
    }
}