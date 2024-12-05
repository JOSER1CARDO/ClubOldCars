package com.example.cluboldcars.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluboldcars.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.AnuncioAdapter;
import Adapter.DenunciaAnuncioAdapter;
import DAO.DAO;
import Entity.Anuncio;
import Entity.DenunciaAnuncio;
import Entity.Usuario;
import Metodos.ImagemBase64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalheDenunciaAnuncioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalheDenunciaAnuncioFragment extends Fragment {


    private static final String ARG_DENUNCIA_ID = "denuncia_id";

    private int id_denuncia;
    private ListView listView;
    private AnuncioAdapter anuncioAdapter;


    public DetalheDenunciaAnuncioFragment() {
        // Required empty public constructor
    }


    public static DetalheDenunciaAnuncioFragment newInstance(int id) {
        DetalheDenunciaAnuncioFragment fragment = new DetalheDenunciaAnuncioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DENUNCIA_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_denuncia = getArguments().getInt(ARG_DENUNCIA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_denuncia_anuncio, container, false);

        TextView descricao = view.findViewById(R.id.txtDescricao);
        TextView nome = view.findViewById(R.id.txtNome);
        TextView email = view.findViewById(R.id.txtEmail);
        ImageView imagemUsuario = view.findViewById(R.id.img_usuario);

        ImageView banir = view.findViewById(R.id.imgBanir);
        ImageView manter = view.findViewById(R.id.imgManter);









        DAO dao = new DAO(getContext());
        DenunciaAnuncio denuncia = dao.denunciasId(id_denuncia);

        nome.setText(denuncia.getUsuario().getNome());
        email.setText(denuncia.getUsuario().getEmail());
        descricao.setText(denuncia.getDescricao());

        listView = view.findViewById(R.id.recycler_view);

        List<Anuncio> anuncios = new ArrayList<Anuncio>();
        anuncios.add(denuncia.getAnuncio());

        anuncioAdapter = new AnuncioAdapter(getContext(), anuncios);
        listView.setAdapter(anuncioAdapter);

        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(denuncia.getUsuario().getFoto());
        imagemUsuario.setImageBitmap(bitmap);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Anuncio anuncio = (Anuncio) parent.getItemAtPosition(position);
                int anuncioId = anuncio.getId();

                // Passar o ID do anúncio para DetalheAnuncioFragment
                DetalheAnuncioFragment detalheFragment = DetalheAnuncioFragment.newInstance(anuncioId, 0);

                // Iniciar a transição para o DetalheAnuncioFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detalheFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        banir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denuncia.setAtivo(0);
                dao.atualizarDenuncia(denuncia);
                denuncia.getAnuncio().setAtivo(0);
                dao.atualizarAnucio(denuncia.getAnuncio());
                Toast.makeText(getContext(), "ANUNCIO BANIDO: ", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }
        });

        manter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denuncia.setAtivo(0);
                dao.atualizarDenuncia(denuncia);
                Toast.makeText(getContext(), "MANTER O ANUNCIO: ", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });








        return view;
    }
}