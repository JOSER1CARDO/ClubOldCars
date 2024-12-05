package com.example.cluboldcars.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cluboldcars.R;

import java.io.IOException;
import java.util.List;

import Adapter.DestaqueAdapter;
import Adapter.EventoAdapter;
import DAO.DAO;
import Entity.Anuncio;
import Entity.Destaque;
import Entity.Evento;
import Metodos.ImagemBase64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalheEventoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalheEventoFragment extends Fragment {

    private static final String ARG_EVENTO_ID = "evento_id";

    private Evento evento;
    private int eventoId;
    private boolean isImageChanged = false;

    private ListView listView;
    private DestaqueAdapter destaqueAdapter;

    private int Id;
    private static final String ARG_ID = "id";




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalheEventoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetalheEventoFragment newInstance(int evento_id,int id ) {
        DetalheEventoFragment fragment = new DetalheEventoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EVENTO_ID, evento_id);
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventoId = getArguments().getInt(ARG_EVENTO_ID);
            Id = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_evento, container, false);


        DAO dao = new DAO(getContext());
        evento = dao.eventoId(eventoId);


        TextView titulo = view.findViewById(R.id.txtTitulo);
        TextView local = view.findViewById(R.id.txtLocal);
        TextView data = view.findViewById(R.id.txtData);
        TextView detalhes = view.findViewById(R.id.txtDetalhes);
        ImageView btnImagem = view.findViewById(R.id.btnImagen);
        ImageView btnPresenca = view.findViewById(R.id.btnPresenca);
        ImageView btnAdd = view.findViewById(R.id.img_add);

        ImageView img_nenhum_carro = view.findViewById(R.id.img_nenhum_carro);


        if(Id != evento.getUsuario().getId())
        {
            btnAdd.setAlpha(0.0f);
        }

        listView = view.findViewById(R.id.recycler_view);
        List<Destaque> destaques = dao.destaqueEventoId(eventoId);
        destaqueAdapter = new DestaqueAdapter(getContext(), destaques);
        listView.setAdapter(destaqueAdapter);

        if(destaques.stream().count()>0)
        {
            img_nenhum_carro.setAlpha(0.0f);
        }







        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NovoDestaqueFragment detalheFragment = NovoDestaqueFragment.newInstance(evento.getId());

                // Iniciar a transição para o DetalheAnuncioFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detalheFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Destaque evento = (Destaque) parent.getItemAtPosition(position);

                // Passar o ID do anúncio para DetalheAnuncioFragment
                DetalhedestaqueFragment detalheFragment = DetalhedestaqueFragment.newInstance(evento.getId(), 0);

                // Iniciar a transição para o DetalheAnuncioFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detalheFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(evento.getFoto());
        btnImagem.setImageBitmap(bitmap);
        local.setText(evento.getLocal());
        data.setText(evento.getData());
        detalhes.setText(evento.getDescricao());
        titulo.setText(evento.getTitulo());

        btnPresenca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageChanged) {
                    btnPresenca.setImageResource(R.drawable.img_estarei_presente_preto);
                } else {
                    btnPresenca.setImageResource(R.drawable.img_estarei_presente_verde);
                }
                isImageChanged = !isImageChanged;
            }
        });








        return view;
    }
}