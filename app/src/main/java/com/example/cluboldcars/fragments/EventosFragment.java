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

import java.util.List;

import Adapter.AnuncioAdapter;
import Adapter.EventoAdapter;
import DAO.DAO;
import Entity.Anuncio;
import Entity.Evento;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventosFragment extends Fragment {


    private ListView listView;
    private EventoAdapter eventoAdapter;

    private int Id;

    private static final String ARG_ID = "id";




    public EventosFragment() {
        // Required empty public constructor
    }


    public static EventosFragment newInstance(int id) {
        EventosFragment fragment = new EventosFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        listView = view.findViewById(R.id.recycler_view);

        DAO dao = new DAO(getContext());

        List<Evento> eventos = dao.eventosAtivos();


        
        eventoAdapter = new EventoAdapter(getContext(), eventos);
        listView.setAdapter(eventoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento evento = (Evento) parent.getItemAtPosition(position);

                // Passar o ID do anúncio para DetalheAnuncioFragment
                DetalheEventoFragment detalheFragment = DetalheEventoFragment.newInstance(evento.getId(), Id);

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