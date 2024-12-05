package com.example.cluboldcars.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cluboldcars.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter.ImageAdapter;
import DAO.DAO;
import Entity.Anuncio;
import Entity.Foto;
import Entity.Modelo;
import Entity.Usuario;
import Metodos.ImagemBase64;
import Metodos.MoneyTextWatcher;

public class NovoAnuncio2Fragment extends Fragment {

    // Constants for parameter arguments
    private static final String ARG_ID_MARCA = "idMarca";
    private static final String ARG_ID_MODELO = "idModelo";
    private static final String ARG_ANO_FAB = "anoFab";
    private static final String ARG_ANO_MOD = "anoMod";
    private static final String ARG_COMBUSTIVEL = "combustivel";
    private static final String ARG_KILOMETRAGEM = "kilometragem";
    private static final String ARG_COR = "cor";
    private static final String ARG_CAMBIO = "cambio";
    private static final String ARG_TROCA = "troca";
    private static final String ARG_ID = "id";

    private Usuario usuario = new Usuario();
    private ActivityResultLauncher<Intent> resultLauncher;
    private List<String> base64ImageList;
    private ImageAdapter imageAdapter; // Declare the adapter here
    private ViewPager2 viewPager;

    // Parameters
    private int idMarca;
    private int idModelo;
    private int anoFab;
    private int anoMod;
    private String combustivel;
    private String kilometragem;
    private String cor;
    private String cambio;
    private String troca;
    private int id;

    public NovoAnuncio2Fragment() {
        // Required empty public constructor
    }

    public static NovoAnuncio2Fragment newInstance(int idMarca, int idModelo, int anoFab, int anoMod,
                                                   String combustivel, String kilometragem, String cor,
                                                   String cambio, String troca, int id) {
        NovoAnuncio2Fragment fragment = new NovoAnuncio2Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_MARCA, idMarca);
        args.putInt(ARG_ID_MODELO, idModelo);
        args.putInt(ARG_ANO_FAB, anoFab);
        args.putInt(ARG_ANO_MOD, anoMod);
        args.putString(ARG_COMBUSTIVEL, combustivel);
        args.putString(ARG_KILOMETRAGEM, kilometragem);
        args.putString(ARG_COR, cor);
        args.putString(ARG_CAMBIO, cambio);
        args.putString(ARG_TROCA, troca);
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idMarca = getArguments().getInt(ARG_ID_MARCA);
            idModelo = getArguments().getInt(ARG_ID_MODELO);
            anoFab = getArguments().getInt(ARG_ANO_FAB);
            anoMod = getArguments().getInt(ARG_ANO_MOD);
            combustivel = getArguments().getString(ARG_COMBUSTIVEL);
            kilometragem = getArguments().getString(ARG_KILOMETRAGEM);
            cor = getArguments().getString(ARG_COR);
            cambio = getArguments().getString(ARG_CAMBIO);
            troca = getArguments().getString(ARG_TROCA);
            id = getArguments().getInt(ARG_ID);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novo_anuncio2, container, false);

        DAO dao = new DAO(getContext());
        VideoView videoView = view.findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.anuncio_veicular);
        videoView.setVideoURI(videoUri);

        ImageView buttonSelectImage = view.findViewById(R.id.btnImagem);
        base64ImageList = new ArrayList<>();

        EditText titulo = view.findViewById(R.id.txtTitulo);
        EditText descricao = view.findViewById(R.id.txtDescricao);
        ImageView btnAnunciar = view.findViewById(R.id.imgAnunciar);
        EditText preco = view.findViewById(R.id.txtPreco);
        preco.addTextChangedListener(new MoneyTextWatcher(preco));

        viewPager = view.findViewById(R.id.viewPager);
        imageAdapter = new ImageAdapter(base64ImageList); // Initialize the adapter
        viewPager.setAdapter(imageAdapter);

        videoView.setOnCompletionListener(mp -> {
            // Congelar no último frame
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(requireActivity(), videoUri);
            Bitmap lastFrame = retriever.getFrameAtTime(videoView.getDuration() * 1000);
            videoView.setBackground(new BitmapDrawable(getResources(), lastFrame));
            try {
                retriever.release();
            } catch (IOException e) {
                e.printStackTrace();
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
                                    base64ImageList.add(base64Image);

                                    imageAdapter.notifyItemInserted(base64ImageList.size() - 1); // Notificar que uma nova imagem foi adicionada
                                    viewPager.setCurrentItem(base64ImageList.size() - 1); // Ir para a nova imagem

                                    // Reiniciar o VideoView de forma correta
                                    videoView.seekTo(1); // Use seekTo em vez de redefinir o URI
                                    videoView.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

        buttonSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(intent);
        });

        btnAnunciar.setOnClickListener(view1 -> {
            Anuncio anuncio = new Anuncio();
            Modelo modelo = dao.modeloID(idModelo);
            usuario.setId(id);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            String data = sdf.format(new Date());

            anuncio.setModelo(modelo);
            anuncio.setCor(cor);
            anuncio.setCambio(cambio);
            anuncio.setAnoFabricacao(anoFab);
            anuncio.setCombustivel(combustivel);
            anuncio.setAnoModelo(anoMod);
            anuncio.setTroca(troca);
            anuncio.setKm(kilometragem);

            anuncio.setAtivo(1);
            anuncio.setData(data);
            anuncio.setProduto(1);
            anuncio.setTitulo(String.valueOf(titulo.getText()));
            anuncio.setPreco(String.valueOf(preco.getText()));
            anuncio.setDescricao(String.valueOf(descricao.getText()));
            anuncio.setUsuario(usuario);
            anuncio.setFoto(base64ImageList.isEmpty() ? null : base64ImageList.get(0)); // Verifique se a lista não está vazia

            if (base64ImageList.isEmpty()) {
                Toast.makeText(getContext(), "Insira uma foto", Toast.LENGTH_SHORT).show();
            } else {
                long idAnuncio = dao.inserirAnuncio(anuncio);
                if (idAnuncio != -1) {
                    for (String base64Image : base64ImageList) {
                        Foto foto = new Foto();
                        foto.setFoto(base64Image);
                        long idFoto = dao.inserirFoto(foto);
                        dao.inserirFotoAnuncio(idFoto, idAnuncio);
                    }
                    Toast.makeText(getContext(), "Anúncio salvo " + idAnuncio, Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getContext(), "Falha ao inserir anúncio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageAdapter.setOnImageClickListener(position -> {
            base64ImageList.remove(position); // Remover a imagem da lista
            imageAdapter.notifyItemRemoved(position); // Notificar o adapter
            if (!base64ImageList.isEmpty()) {
                viewPager.setCurrentItem(Math.min(position, base64ImageList.size() - 1)); // Atualizar o ViewPager
            }
        });


        return view;
    }
}
