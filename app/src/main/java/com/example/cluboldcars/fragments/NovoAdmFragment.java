package com.example.cluboldcars.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cluboldcars.R;

import java.util.List;

import Adapter.UsuarioAdapter;
import DAO.DAO;
import Entity.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NovoAdmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NovoAdmFragment extends Fragment {

    private ListView listView;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios;

    private Usuario usuario1;

    public NovoAdmFragment() {
        // Required empty public constructor
    }

    public static NovoAdmFragment newInstance() {
        NovoAdmFragment fragment = new NovoAdmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novo_adm, container, false);

        DAO dao = new DAO(getContext());
        EditText txtEmail = view.findViewById(R.id.txtEmail);
        ImageView imgAdd = view.findViewById(R.id.img_add);
        ImageView imgLixeira = view.findViewById(R.id.img_lixeira);

        listView = view.findViewById(R.id.recycler_view);

        usuarios = dao.adms();
        usuarioAdapter = new UsuarioAdapter(getContext(), usuarios);
        listView.setAdapter(usuarioAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = (Usuario) parent.getItemAtPosition(position);
                usuario1 = usuario;
                txtEmail.setText(usuario1.getEmail());
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario1 = dao.usuarioEmail(txtEmail.getText().toString());
                if (usuario1 != null) {
                    usuario1.setAdm(1);
                    dao.atualizarPessoa(usuario1);
                    Toast.makeText(getContext(), "Administrador inserido", Toast.LENGTH_SHORT).show();
                    atualizarLista();
                } else {
                    Toast.makeText(getContext(), "Email não encontrado", Toast.LENGTH_SHORT).show();
                }
                usuario1 = null;
            }
        });

        imgLixeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuario1 != null) {
                    usuario1.setAdm(2);
                    dao.atualizarPessoa(usuario1);
                    Toast.makeText(getContext(), "Administrador Excluído", Toast.LENGTH_SHORT).show();
                    atualizarLista();
                    txtEmail.setText("");
                } else {
                    Toast.makeText(getContext(), "Nenhum administrador selecionado", Toast.LENGTH_SHORT).show();
                }
                usuario1 = null;
            }
        });

        return view;
    }

    private void atualizarLista() {
        DAO dao = new DAO(getContext());
        usuarios.clear();
        usuarios.addAll(dao.adms());
        usuarioAdapter.notifyDataSetChanged();
    }
}
