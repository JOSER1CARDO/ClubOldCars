package com.example.cluboldcars.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cluboldcars.R;

import java.io.IOException;
import java.util.List;

import DAO.DAO;
import Entity.Marca;
import Entity.Modelo;
import Entity.Usuario;
import Metodos.ImagemBase64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Usuario usuario;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(Usuario mpessoa) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable("usuario", mpessoa);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable("usuario");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        DAO dao = new DAO(getContext());
        Button marca = view.findViewById(R.id.btnMarca);
        Button modelo = view.findViewById(R.id.btnModelo);
        Button arruma = view.findViewById(R.id.btnArruma);
        TextView marcs = view.findViewById(R.id.textView2);
        TextView model = view.findViewById(R.id.textView3);
        ImageView imgFavoritos = view.findViewById(R.id.img_salvos);



        TextView nome = view.findViewById(R.id.txtNome);
        TextView email = view.findViewById(R.id.txtEmail);
        TextView telefone = view.findViewById(R.id.txtTelefone);
        ImageView imgUsuario = view.findViewById(R.id.img_usuario);

        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(usuario.getFoto());

        nome.setText(usuario.getNome());
        telefone.setText(usuario.getTelefone());
        email.setText(usuario.getEmail());
        imgUsuario.setImageBitmap(bitmap);



        imgFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritosFragment detalheFragment = FavoritosFragment.newInstance(usuario.getId());

                // Iniciar a transição para o DetalheAnuncioFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detalheFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });



        VideoView videoView1 = view.findViewById(R.id.videoView);
        Uri videoUri1 = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.anuncio_veicular);
        videoView1.setVideoURI(videoUri1);

        VideoView videoView2 = view.findViewById(R.id.videoView2);
        Uri videoUri2 = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.anuncio_evento);
        videoView2.setVideoURI(videoUri2);


        videoView1.setOnCompletionListener(mp -> {
            // Congelar no último frame
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(requireActivity(), videoUri1);
            Bitmap lastFrame = retriever.getFrameAtTime(videoView1.getDuration() * 1000);
            videoView1.setBackground(new BitmapDrawable(getResources(), lastFrame));
            try {
                retriever.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        videoView1.start();

        videoView2.setOnCompletionListener(mp -> {
            // Congelar no último frame
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(requireActivity(), videoUri2);
            Bitmap lastFrame = retriever.getFrameAtTime(-400); // Pega o último frame
            videoView2.setBackground(new BitmapDrawable(getResources(), lastFrame));
            try {
                retriever.release(); // Chama o release no retriever
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        videoView2.start();










        /*
        marcs.setText("");
        List<Marca> nomeMarcas = dao.marcas();
        for(Marca m:nomeMarcas){
            marcs.setText(marcs.getText() + ", " + m.getNome());
        }

        model.setText("");
        List<Modelo> nomeModelos = dao.Modelos();
        for(Modelo m:nomeModelos){
            model.setText(model.getText() + ", " + m.getNome());
        }*/


        marca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] marcas = {
                        "Toyota", "Ford", "Chevrolet", "Honda", "BMW", "Mercedes-Benz", "Audi", "Volkswagen", "Nissan", "Hyundai",
                        "Kia", "Subaru", "Mazda", "Lexus", "Jeep", "Dodge", "Chrysler", "Buick", "Cadillac", "GMC",
                        "Ram", "Volvo", "Tesla", "Land Rover", "Jaguar", "Infiniti", "Acura", "Lincoln", "Mitsubishi", "Alfa Romeo",
                        "Fiat", "Maserati", "Ferrari", "Lamborghini", "Porsche", "Aston Martin", "Bentley", "Rolls-Royce", "Mini", "Genesis",
                        "Peugeot", "Citroën", "Renault", "Skoda", "SEAT", "Saab", "Opel", "Suzuki", "Isuzu", "McLaren"
                };



                for (String m : marcas) {
                    Marca marca = new Marca();
                    marca.setNome(m);
                   dao.inserirMarca(marca);
                }
            }
        });

        arruma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.excluirModelosComMarca8();

                String[] modelosVW = {
                        "Gol", "Voyage", "Polo", "Virtus", "Golf", "Jetta", "Passat", "Fusca", "Nivus", "T-Cross",
                        "Tiguan", "Taos", "Touareg", "Fox", "Up", "Saveiro", "Amarok", "Kombi", "ID.3", "ID.4", "Santana", "Brasilia", "Parati"
                };
                salvar(8, modelosVW);

                
            }
        });

        modelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String[] modelos = {
                        "Charger", "Challenger", "Durango", "Journey", "Grand Caravan", "Viper", "Dart", "Avenger", "Nitro", "Magnum",
                        "Neon", "Caliber", "Stratus", "Intrepid", "Shadow", "Spirit", "Stealth", "Daytona", "Omni", "Lancer"
                };
                salvar(16, modelos);


                String[] modelosBMW = {
                        "Series 1", "Series 2", "Series 3", "Series 4", "Series 5", "Series 6", "Series 7", "Series 8", "X1", "X2",
                        "X3", "X4", "X5", "X6", "X7", "Z4", "i3", "i4", "i8", "iX"
                };
                salvar(5, modelosBMW);



                String[] modelosFORD = {
                        "Fiesta", "Focus", "Fusion", "Mustang", "EcoSport", "Escape", "Edge", "Explorer", "Expedition", "Bronco",
                        "Ranger", "F-150", "Super Duty", "Maverick", "Taurus", "Flex", "C-Max", "Transit", "Galaxy", "Mondeo"
                };
                salvar(2, modelosFORD);



                String[] modelosHONDA = {
                        "Civic", "Accord", "Fit", "City", "HR-V", "CR-V", "Pilot", "Passport", "Odyssey", "Ridgeline",
                        "Insight", "Clarity", "S2000", "Prelude", "Element", "CR-Z", "Crosstour", "Brio", "Jazz", "Integra"
                };
                salvar(4, modelosHONDA);



                String[] modelosGM  = {
                        "Onix", "Prisma", "Cobalt", "Cruze", "Malibu", "Impala", "Camaro", "Corvette", "Spark", "Sonic",
                        "Opala", "Equinox", "Blazer", "Traverse", "Tahoe", "Suburban", "Colorado", "Silverado", "S10", "Montana","Chevette", "C10"
                };
                salvar(3, modelosGM);



                String[] modelosAUDI = {
                        "A1", "A3", "A4", "A5", "A6", "A7", "A8", "Q2", "Q3", "Q5",
                        "Q7", "Q8", "TT", "TTS", "TT RS", "S3", "S4", "S5", "S6", "S7",
                        "RS3", "RS4", "RS5", "RS6", "RS7", "e-tron", "e-tron GT", "R8", "SQ5", "SQ7"
                };
                salvar(7, modelosAUDI);



                String[] modelosJEEP = {
                        "Wrangler", "Grand Cherokee", "Cherokee", "Compass", "Renegade", "Gladiator", "Wagoneer", "Grand Wagoneer", "Patriot", "Commander",
                        "Liberty", "CJ", "J10", "J20", "Scrambler", "Comanche", "Laredo", "Trailhawk", "Trackhawk", "Overland"
                };
                salvar(15, modelosHONDA);



                String[] modelosVW = {
                        "Gol", "Voyage", "Polo", "Virtus", "Golf", "Jetta", "Passat", "Fusca", "Nivus", "T-Cross",
                        "Tiguan", "Taos", "Touareg", "Fox", "Up", "Saveiro", "Amarok", "Kombi", "ID.3", "ID.4", "Santana", "Brasilia", "Parati"
                };
                salvar(8, modelosVW);






            }
        });
        return view;

    }






    public void salvar(int id, String[] a)
    {
        DAO dao = new DAO(getContext());
        Marca marca = new Marca();
        marca.setId(id);
        for (String m : a) {
            Modelo modelo = new Modelo();

            modelo.setNome(m);
            modelo.setMarca(marca);
            dao.inserirModelo(modelo);
        }

    }

}