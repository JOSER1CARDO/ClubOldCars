package com.example.cluboldcars.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluboldcars.R;
import java.util.ArrayList;
import java.util.List;

import Adapter.ImageAdapter;
import DAO.DAO;
import Entity.Anuncio;
import Entity.DenunciaAnuncio;
import Entity.Foto;
import Metodos.ImagemBase64;

public class DetalheAnuncioFragment extends Fragment {

    private static final String ARG_ANUNCIO_ID = "anuncio_id";
    private static final String ARG_USUARIO_ID = "usuario_id";
    private static final String TAG = "DetalheAnuncioFragment";

    private Anuncio anuncio;
    private int anuncioId;
    private ViewPager2 viewPager;
    private int id_usuario;

    private boolean salvoFavorito;

    public DetalheAnuncioFragment() {
        // Required empty public constructor
    }

    public static DetalheAnuncioFragment newInstance(int anuncioId,int usuarioId) {
        DetalheAnuncioFragment fragment = new DetalheAnuncioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ANUNCIO_ID, anuncioId);
        args.putInt(ARG_USUARIO_ID, usuarioId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            anuncioId = getArguments().getInt(ARG_ANUNCIO_ID);
            id_usuario = getArguments().getInt(ARG_USUARIO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_anuncio, container, false);

        DAO dao = new DAO(getContext());
        anuncio = dao.anuncioId(anuncioId);

        // Exibir o ID do anúncio (ou carregar detalhes do anúncio do banco de dados)
        TextView textView = view.findViewById(R.id.textViewAnuncioId);
        textView.setText("Anúncio: " + anuncio.getTitulo());

        TextView ano = view.findViewById(R.id.txtAno);
        TextView cor = view.findViewById(R.id.txtCor);
        TextView cambio = view.findViewById(R.id.txtCambio);
        TextView combustivel = view.findViewById(R.id.txtCombustivel);
        TextView troca = view.findViewById(R.id.txtTroca);
        TextView titulo = view.findViewById(R.id.txtTitulo);
        TextView km = view.findViewById(R.id.txtKm);
        TextView nome = view.findViewById(R.id.txtNome);
        TextView detalhe = view.findViewById(R.id.txtDetalhes);
        TextView telefone = view.findViewById(R.id.txtTelefone);
        TextView preco =view.findViewById(R.id.txtPreco);
        ImageView imgUsuario = view.findViewById(R.id.imgVendedor);
        ImageView iconChat = view.findViewById(R.id.imgChat);
        ImageView iconSalvar = view.findViewById(R.id.img_salvar);
        ImageView iconDenucia = view.findViewById(R.id.img_denuncia);
        ImageView imgStatus = view.findViewById(R.id.img_status);
        ImageView vender = view.findViewById(R.id.img_vender);


        if (id_usuario == 0) {
            iconSalvar.setVisibility(View.GONE);
            iconDenucia.setVisibility(View.GONE);
        }


        if(anuncio.getAtivo() == 1){
            imgStatus.setImageResource(R.drawable.img_disponivel);
        }else if(anuncio.getAtivo() == 0){
            imgStatus.setImageResource(R.drawable.img_banido);
        }else {
            imgStatus.setImageResource(R.drawable.img_vendido);
        }


        if(!(id_usuario == anuncio.getUsuario().getId()) && (anuncio.getAtivo() == 1))
        {
            vender.setVisibility(View.GONE);
        }

        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anuncio.setAtivo(2) ;
                dao.atualizarAnucio(anuncio);

                Toast.makeText(getContext(), "Anuncio Vendido ", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });




        salvoFavorito = dao.itemSalvo(id_usuario, anuncioId);

        if(salvoFavorito == true){
            iconSalvar.setImageResource(R.drawable.icon_salvo);
        }


        iconSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(salvoFavorito == false){
                    dao.inserirfavorito(anuncioId, id_usuario);
                    iconSalvar.setImageResource(R.drawable.icon_salvo);
                    Toast.makeText(getContext(), "Anuncio Salvo em Favoritos", Toast.LENGTH_SHORT).show();
                    salvoFavorito = true;

                }else{
                    dao.deletarFavorito(id_usuario,anuncioId);
                    iconSalvar.setImageResource(R.drawable.icon_salvar);
                    Toast.makeText(getContext(), "Anuncio Removido de Favoritos", Toast.LENGTH_SHORT).show();
                    salvoFavorito = false;
                }

            }
        });

        iconDenucia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NovoDenunciaAnuncioFragment detalheFragment = NovoDenunciaAnuncioFragment.newInstance(anuncioId,id_usuario);

                // Iniciar a transição para o DetalheAnuncioFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detalheFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        ano.setText(anuncio.getAnoFabricacao() + "/" + anuncio.getAnoModelo());
        cor.setText(anuncio.getCor());
        cambio.setText(anuncio.getCambio());
        combustivel.setText(anuncio.getCombustivel());
        troca.setText(anuncio.getTroca());
        preco.setText(anuncio.getPreco());
        km.setText(anuncio.getKm());
        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(anuncio.getUsuario().getFoto());
        nome.setText("Nome: " + anuncio.getUsuario().getNome());
        telefone.setText("Telefone: " + anuncio.getUsuario().getTelefone());
        detalhe.setText("Detalhes: \n       " + anuncio.getDescricao());

        imgUsuario.setImageBitmap(bitmap);

        titulo.setText(anuncio.getTitulo());

        List<Foto> fotos = dao.fotosAnuncios(anuncioId);

        // Exemplo de lista de imagens base64
        List<String> base64Images = new ArrayList<>();
        for (Foto f : fotos) {
            base64Images.add(f.getFoto());
        }

        iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String rawNumber = anuncio.getUsuario().getTelefone();
                    Log.d(TAG, "Raw phone number: " + rawNumber);

                    // Formatar o número de telefone no formato internacional
                    String formattedNumber = rawNumber.replace("+", "").replace(" ", "").replace("-", "");

                    if (!formattedNumber.startsWith("55")) {
                        formattedNumber = "55" + formattedNumber; // Adding country code for Brazil if not present
                    }
                    Log.d(TAG, "Formatted phone number: " + formattedNumber);

                    // Cria a URI com o número e a mensagem
                    Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + formattedNumber + "&text=" + Uri.encode("Olá, tenho interesse no veículo: " + anuncio.getModelo().getMarca().getNome() + "/" +  anuncio.getModelo().getNome()));
                    Log.d(TAG, "WhatsApp URI: " + uri.toString());

                    // Cria o Intent
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    // Verifica se o WhatsApp está instalado
                        startActivity(intent);

                } catch (Exception e) {
                    Log.e(TAG, "Error opening WhatsApp", e);
                    Toast.makeText(getContext(), "Erro ao abrir o WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewPager = view.findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(base64Images);
        viewPager.setAdapter(adapter);

        return view;
    }

    private boolean isPackageInstalled(String packageName) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
