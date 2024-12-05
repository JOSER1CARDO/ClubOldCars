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

import Adapter.DenunciaAnuncioAdapter;
import Adapter.EventoAdapter;
import DAO.DAO;
import Entity.DenunciaAnuncio;
import Entity.Evento;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DenunciasAnunciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DenunciasAnunciosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private DenunciaAnuncioAdapter denunciaAnuncioAdapter;

    public DenunciasAnunciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DenunciasAnunciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DenunciasAnunciosFragment newInstance(String param1, String param2) {
        DenunciasAnunciosFragment fragment = new DenunciasAnunciosFragment();
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
        View view = inflater.inflate(R.layout.fragment_denuncias_anuncios, container, false);

        listView = view.findViewById(R.id.recycler_view);

        DAO dao = new DAO(getContext());

        List<DenunciaAnuncio> denuncias = dao.denuncias();



        denunciaAnuncioAdapter = new DenunciaAnuncioAdapter(getContext(), denuncias);
        listView.setAdapter(denunciaAnuncioAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DenunciaAnuncio denuncia = (DenunciaAnuncio) parent.getItemAtPosition(position);

                // Passar o ID do anúncio para DetalheAnuncioFragment
                DetalheDenunciaAnuncioFragment detalheFragment = DetalheDenunciaAnuncioFragment.newInstance(denuncia.getId());

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