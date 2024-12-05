package com.example.cluboldcars.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cluboldcars.R;

import DAO.DAO;
import Entity.Destaque;
import Metodos.ImagemBase64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalhedestaqueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalhedestaqueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    public DetalhedestaqueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalhedestaqueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalhedestaqueFragment newInstance(int param1, int param2) {
        DetalhedestaqueFragment fragment = new DetalhedestaqueFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhedestaque, container, false);

         DAO dao = new DAO(getContext());

        Destaque destaque = dao.destaqueId(mParam1);
        TextView tudo = view.findViewById(R.id.txtTudo);
        ImageView img = view.findViewById(R.id.btnImagen);
        ImageView remover = view.findViewById(R.id.btn_remover);



        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(destaque.getFoto());
        img.setImageBitmap(bitmap);

        tudo.setText("Posicao: " + destaque.getPosicao()+ "\n\n" +
                "Titulo: " + destaque.getTitulo()+ "\n\n" +
                "Premio: " + destaque.getPremio()+ "\n\n" +
                "Descricao: " + destaque.getDescricao()+ "\n\n");


        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.removerDestaque(destaque.getId());
                getActivity().onBackPressed();
            }
        });








        return view;
    }
}