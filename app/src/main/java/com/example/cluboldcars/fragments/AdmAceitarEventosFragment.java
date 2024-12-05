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

import Adapter.EventoAdapter;
import DAO.DAO;
import Entity.Evento;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdmAceitarEventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdmAceitarEventosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private ListView listView;
    private EventoAdapter eventoAdapter;





    public AdmAceitarEventosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdmAceitarEventosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdmAceitarEventosFragment newInstance(String param1, String param2) {
        AdmAceitarEventosFragment fragment = new AdmAceitarEventosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adm_aceitar_eventos, container, false);

        listView = view.findViewById(R.id.recycler_view);

        DAO dao = new DAO(getContext());

        List<Evento> eventos = dao.eventosEmAnalise();


        eventoAdapter = new EventoAdapter(getContext(), eventos);
        listView.setAdapter(eventoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento evento = (Evento) parent.getItemAtPosition(position);

                // Passar o ID do anúncio para DetalheAnuncioFragment
                AdmAceitarEvento2ragment detalheFragment = AdmAceitarEvento2ragment.newInstance(evento.getId());

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