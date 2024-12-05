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

import com.example.cluboldcars.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.EventoAdapter;
import DAO.DAO;
import Entity.Evento;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeusEventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeusEventosFragment extends Fragment {

    private static final String ARG_ID = "id";

    private int Id;

    private ListView listView;
    private EventoAdapter eventoAdapter;




    public MeusEventosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MeusEventosFragment newInstance(int id) {
        MeusEventosFragment fragment = new MeusEventosFragment();
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

        View view = inflater.inflate(R.layout.fragment_meus_eventos, container, false);
        // Inflate the layout for this fragment
        listView = view.findViewById(R.id.recycler_view);

        Button btnAtivos = view.findViewById(R.id.btnAtivos);
        Button btnEmAnalise = view.findViewById(R.id.btnAnlise);
        Button btnEncerrados = view.findViewById(R.id.btnEncerrados);
        Button btnBanidos = view.findViewById(R.id.btnBanidos);

        ImageView imgNenhumEvento =view.findViewById(R.id.imgNenhumEvento);

        DAO dao = new DAO(getContext());

        List<Evento> eventos = dao.eventosUsuario(Id);
        List<Evento> ListAtivos = new ArrayList<Evento>();
        List<Evento> ListAnalise = new ArrayList<Evento>();
        List<Evento> ListEncerrados = new ArrayList<Evento>();
        List<Evento> ListBanidos = new ArrayList<Evento>();
        if(eventos.stream().count()>0){
            imgNenhumEvento.setAlpha(0.0f);
        }


        for(Evento e : eventos){
            if(e.getAtivo()==0){
                ListEncerrados.add(e);
            }else if(e.getAtivo()==1){
                ListAtivos.add(e);
            }else if(e.getAtivo()==2){
                ListAnalise.add(e);
            }else if(e.getAtivo()==3){
                ListBanidos.add(e);
            }
        }

        eventoAdapter = new EventoAdapter(getContext(), ListAtivos);
        listView.setAdapter(eventoAdapter);



        btnAtivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventoAdapter = new EventoAdapter(getContext(), ListAtivos);
                listView.setAdapter(eventoAdapter);
                if(ListAtivos.stream().count()>0){
                    imgNenhumEvento.setAlpha(0.0f);
                }else{
                    imgNenhumEvento.setAlpha(1.0f);
                }
            }
        });
        btnEncerrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventoAdapter = new EventoAdapter(getContext(), ListEncerrados);
                listView.setAdapter(eventoAdapter);
                if(ListEncerrados.stream().count()>0){
                    imgNenhumEvento.setAlpha(0.0f);
                }else{
                    imgNenhumEvento.setAlpha(1.0f);
                }
            }
        });
        btnEmAnalise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventoAdapter = new EventoAdapter(getContext(), ListAnalise);
                listView.setAdapter(eventoAdapter);
                if(ListAnalise.stream().count()>0){
                    imgNenhumEvento.setAlpha(0.0f);
                }else{
                    imgNenhumEvento.setAlpha(1.0f);
                }
            }
        });
        btnBanidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventoAdapter = new EventoAdapter(getContext(), ListBanidos);
                listView.setAdapter(eventoAdapter);
                if(ListBanidos.stream().count()>0){
                    imgNenhumEvento.setAlpha(0.0f);
                }else{
                    imgNenhumEvento.setAlpha(1.0f);
                }
            }
        });











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