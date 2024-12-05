package com.example.cluboldcars.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.cluboldcars.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.AnuncioAdapter;
import DAO.DAO;
import Entity.Anuncio;
import Entity.DenunciaAnuncio;
import Entity.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NovoDenunciaAnuncioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NovoDenunciaAnuncioFragment extends Fragment {


    private static final String ARG_ANUNCIO_ID = "anuncio_id";
    private static final String ARG_USUARIO_ID = "usuario_id";

    private int id_usuario;
    private int id_anuncio;
    private ListView listView;
    private AnuncioAdapter anuncioAdapter;



    public NovoDenunciaAnuncioFragment() {
        // Required empty public constructor
    }


    public static NovoDenunciaAnuncioFragment newInstance(int anuncioId,int usuarioId) {
        NovoDenunciaAnuncioFragment fragment = new NovoDenunciaAnuncioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ANUNCIO_ID, anuncioId);
        args.putInt(ARG_USUARIO_ID, usuarioId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_anuncio = getArguments().getInt(ARG_ANUNCIO_ID);
            id_usuario = getArguments().getInt(ARG_USUARIO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novo_denuncia_anuncio, container, false);

        EditText descricao = view.findViewById(R.id.txtDescricao);
        ImageView denunciar = view.findViewById(R.id.imgDenunciar);


        DAO dao = new DAO(getContext());
        Anuncio anuncio = dao.anuncioId(id_anuncio);
        Usuario usuario = dao.usuarioId(id_usuario);

        listView = view.findViewById(R.id.recycler_view);

        List<Anuncio> anuncios = new ArrayList<Anuncio>();
        anuncios.add(anuncio);

        anuncioAdapter = new AnuncioAdapter(getContext(), anuncios);
        listView.setAdapter(anuncioAdapter);


        DenunciaAnuncio denuncia = new DenunciaAnuncio();


        denunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(denuncia.getDescricao()!=""){
                    denuncia.setDescricao("");
                    denuncia.setUsuario(usuario);
                    denuncia.setAnuncio(anuncio);
                    denuncia.setAtivo(1);
                    denuncia.setDescricao(descricao.getText().toString());
                    long id = dao.inserirDenunciaAnuncio(denuncia.getUsuario().getId(), denuncia.getAnuncio().getId(), denuncia.getDescricao());

                    getActivity().onBackPressed();


                }
            }
        });

















        return view;
    }
}