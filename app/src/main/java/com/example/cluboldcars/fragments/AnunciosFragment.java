package com.example.cluboldcars.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cluboldcars.R;

import java.util.List;

import Adapter.AnuncioAdapter;
import DAO.DAO;
import Entity.Anuncio;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnunciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnunciosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    private AnuncioAdapter anuncioAdapter;
    private int Id;

    private static final String ARG_ID = "id";



    public AnunciosFragment() {
        // Required empty public constructor
    }

    public static AnunciosFragment newInstance(int id) {
        AnunciosFragment fragment = new AnunciosFragment();
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
        View view = inflater.inflate(R.layout.fragment_anuncios, container, false);

        listView = view.findViewById(R.id.recycler_view);

         DAO dao = new DAO(getContext());


         List<Anuncio> anuncios = dao.anuncios();

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