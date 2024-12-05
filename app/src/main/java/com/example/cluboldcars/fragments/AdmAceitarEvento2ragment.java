package com.example.cluboldcars.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluboldcars.R;

import DAO.DAO;
import Entity.Evento;
import Metodos.ImagemBase64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdmAceitarEvento2ragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdmAceitarEvento2ragment extends Fragment {

    private static final String ARG_EVENTO_ID = "evento_id";

    private Evento evento;
    private int eventoId;



    public AdmAceitarEvento2ragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AdmAceitarEvento2ragment newInstance(int evento_id) {
        AdmAceitarEvento2ragment fragment = new AdmAceitarEvento2ragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EVENTO_ID, evento_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventoId = getArguments().getInt(ARG_EVENTO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adm_aceitar_evento2ragment, container, false);

        DAO dao = new DAO(getContext());
        evento = dao.eventoId(eventoId);


        TextView titulo = view.findViewById(R.id.txtTitulo);
        TextView local = view.findViewById(R.id.txtLocal);
        TextView data = view.findViewById(R.id.txtData);
        TextView detalhes = view.findViewById(R.id.txtDetalhes);
        ImageView btnImagem = view.findViewById(R.id.btnImagen);

        ImageView imgAceitar = view.findViewById(R.id.imgAceitar);
        ImageView imgRecusar = view.findViewById(R.id.imgRecusar);

        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(evento.getFoto());
        btnImagem.setImageBitmap(bitmap);
        local.setText(evento.getLocal());
        data.setText(evento.getData());
        detalhes.setText(evento.getDescricao());
        titulo.setText(evento.getTitulo());


        imgAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento.setAtivo(1);
                dao.atualizarEvento(evento);
                Toast.makeText(getContext(), "Evento aceito ", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        imgRecusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento.setAtivo(3);
                dao.atualizarEvento(evento);
                Toast.makeText(getContext(), "Evento nao aceito ", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });


        return view;
    }
}