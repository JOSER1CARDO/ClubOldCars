package com.example.cluboldcars.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cluboldcars.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import DAO.DAO;
import Entity.Evento;
import Entity.Usuario;
import Metodos.ImagemBase64;

public class NovoEventoFragment extends Fragment {

    private static final String ARG_ID = "id";

    private int Id;

    private EditText editTextDate;
    private String base64 = "";
    private ActivityResultLauncher<Intent> resultLauncher;
    Usuario usuario = new Usuario();
    private ImageView imagem;

    public NovoEventoFragment() {
        // Required empty public constructor
    }

    public static NovoEventoFragment newInstance(int id) {
        NovoEventoFragment fragment = new NovoEventoFragment();
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
        View view = inflater.inflate(R.layout.fragment_novo_evento, container, false);

        DAO dao = new DAO(getContext());
        editTextDate = view.findViewById(R.id.editTextDate);
        EditText titulo = view.findViewById(R.id.txtTitulo);
        EditText descricao = view.findViewById(R.id.txtDescricao);
        EditText local = view.findViewById(R.id.txtLocal);
        imagem = view.findViewById(R.id.btnImagen);
        ImageView criarEvento = view.findViewById(R.id.imgEvento);

        if(Id != 0 ){
            usuario = dao.usuarioId(Id);
        }

        VideoView videoView = view.findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.anuncio_evento);
        videoView.setVideoURI(videoUri);

        videoView.setOnCompletionListener(mp -> {
            // Congelar no último frame
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(requireActivity(), videoUri);
            Bitmap lastFrame = retriever.getFrameAtTime(-400); // Pega o último frame
            videoView.setBackground(new BitmapDrawable(getResources(), lastFrame));
            try {
                retriever.release(); // Chama o release no retriever
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        videoView.start();



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

                                    videoView.seekTo(1); // Use seekTo em vez de redefinir o URI
                                    videoView.start();

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

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        criarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Evento evento = new Evento();
                if(titulo.getText().length() > 40){
                    Toast.makeText(getContext(), "Titulo maximo 25 caracteres", Toast.LENGTH_SHORT).show();
                }else{
                    evento.setTitulo(titulo.getText().toString());
                    evento.setDescricao(descricao.getText().toString());
                    evento.setLocal(local.getText().toString());
                    evento.setData(editTextDate.getText().toString());
                    evento.setFoto(base64);
                    evento.setAtivo(2);
                    evento.setUsuario(usuario);

                    dao.inserirEvento(evento);

                    getActivity().onBackPressed();
                }


            }
        });










        return view;
    }

    private void showDatePickerDialog() {
        // Obter a data atual
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Criar uma nova instância de DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Mês é 0-based, então adicionar 1
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                },
                year, month, day);

        // Mostrar o DatePickerDialog
        datePickerDialog.show();
    }
}
