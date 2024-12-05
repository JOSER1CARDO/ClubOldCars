package com.example.cluboldcars.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cluboldcars.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.AnuncioAdapter;
import DAO.DAO;
import Entity.Anuncio;
import Entity.Favorito;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {



    private ListView listView;
    private AnuncioAdapter anuncioAdapter;
    private int Id;

    private static final String ARG_ID = "id";




    public FavoritosFragment() {
        // Required empty public constructor
    }

    public static FavoritosFragment newInstance(int id) {
        FavoritosFragment fragment = new FavoritosFragment();
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
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        listView = view.findViewById(R.id.recycler_view);

        DAO dao = new DAO(getContext());

        List<Favorito> favoritosadad = dao.todosFavoritos();
        List<Favorito> favoritos = dao.favoritosDoUsuario(Id);
        List<Anuncio> anuncios = new ArrayList<Anuncio>();
        for(Favorito f: favoritos){
            anuncios.add(f.getAnuncio());
        }




        anuncioAdapter = new AnuncioAdapter(getContext(), anuncios);
        listView.setAdapter(anuncioAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Anuncio anuncio = (Anuncio) parent.getItemAtPosition(position);
                int anuncioId = anuncio.getId();

                // Passar o ID do anúncio para DetalheAnuncioFragment
                DetalheAnuncioFragment detalheFragment = DetalheAnuncioFragment.newInstance(anuncioId, Id);

                // Iniciar a transição para o DetalheAnuncioFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detalheFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });





        return view;
    }
}