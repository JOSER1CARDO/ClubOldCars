package com.example.cluboldcars.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.util.Log;

import com.example.cluboldcars.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.AnuncioAdapter;
import DAO.DAO;
import Entity.Anuncio;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeusAnunciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeusAnunciosFragment extends Fragment {

    private static final String ARG_ID_USUARIO = "id_usuario";

    private int usuarioId;

    private ListView listView;
    private AnuncioAdapter anuncioAdapter;

    public MeusAnunciosFragment() {
        // Required empty public constructor
    }

    public static MeusAnunciosFragment newInstance(int id) {
        MeusAnunciosFragment fragment = new MeusAnunciosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_USUARIO, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuarioId = getArguments().getInt(ARG_ID_USUARIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meus_anuncios, container, false);

        listView = view.findViewById(R.id.recycler_view);

        Button btnAtivos = view.findViewById(R.id.btnAtivos);
        Button btnVendidos = view.findViewById(R.id.btnVendidos);
        Button btnBanidos = view.findViewById(R.id.btnBanidos);
        ImageView imgNenhumEvento = view.findViewById(R.id.imgNenhumEvento);

        DAO dao = new DAO(getContext());
        List<Anuncio> anuncios = dao.anunciosUsuario(usuarioId);
        if (anuncios == null) {
            anuncios = new ArrayList<>();
        }

        List<Anuncio> ListAtivos = new ArrayList<>();
        List<Anuncio> ListVendidos = new ArrayList<>();
        List<Anuncio> ListBanidos = new ArrayList<>();

        for (Anuncio e : anuncios) {
            if (e.getAtivo() == 0) {
                ListBanidos.add(e);
            } else if (e.getAtivo() == 1) {
                ListAtivos.add(e);
            } else if (e.getAtivo() == 2) {
                ListVendidos.add(e);
            }
        }

        if (!ListAtivos.isEmpty()) {
            imgNenhumEvento.setAlpha(0.0f);
        } else {
            imgNenhumEvento.setAlpha(1.0f);
        }

        anuncioAdapter = new AnuncioAdapter(getContext(), ListAtivos);
        listView.setAdapter(anuncioAdapter);

        btnAtivos.setOnClickListener(view1 -> {
            anuncioAdapter = new AnuncioAdapter(getContext(), ListAtivos);
            listView.setAdapter(anuncioAdapter);
            imgNenhumEvento.setAlpha(ListAtivos.isEmpty() ? 1.0f : 0.0f);
        });

        btnVendidos.setOnClickListener(view12 -> {
            anuncioAdapter = new AnuncioAdapter(getContext(), ListVendidos);
            listView.setAdapter(anuncioAdapter);
            imgNenhumEvento.setAlpha(ListVendidos.isEmpty() ? 1.0f : 0.0f);
        });

        btnBanidos.setOnClickListener(view13 -> {
            anuncioAdapter = new AnuncioAdapter(getContext(), ListBanidos);
            listView.setAdapter(anuncioAdapter);
            imgNenhumEvento.setAlpha(ListBanidos.isEmpty() ? 1.0f : 0.0f);
        });

        listView.setOnItemClickListener((parent, view14, position, id) -> {
            Anuncio evento = (Anuncio) parent.getItemAtPosition(position);

            // Passar o ID do anúncio para DetalheAnuncioFragment
            DetalheAnuncioFragment detalheFragment = DetalheAnuncioFragment.newInstance(evento.getId(), usuarioId);

            // Iniciar a transição para o DetalheAnuncioFragment
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, detalheFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
