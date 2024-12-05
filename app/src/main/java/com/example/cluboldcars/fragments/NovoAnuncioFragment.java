package com.example.cluboldcars.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cluboldcars.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DAO.DAO;
import Entity.Marca;
import Entity.Modelo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NovoAnuncioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NovoAnuncioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_ID = "id";

    private Button btnVeiculo, btnPeca;
    private Button selectedButton;
    private HashMap<String, Integer> marcaMap;
    private HashMap<String, Integer> modeloMap;
    private List<Modelo> modelos = new ArrayList<>();
    private ArrayAdapter<String> adapterModelos;
    private List<String> nomesModelos = new ArrayList<>();


    private String cor,combustivel,troca,kilometragem,cambio;

    private int idMarca, idModelo,anoFab,anoMod;

    private int id;






    public NovoAnuncioFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NovoAnuncioFragment newInstance(int id) {
        NovoAnuncioFragment fragment = new NovoAnuncioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_novo_anuncio, container, false);

         DAO dao =  new DAO(getContext());

         List<Marca> marcas = dao.marcas();





        Spinner spinnerMarcas = view.findViewById(R.id.spinnerMarcas);
        Spinner spinnerModelos = view.findViewById(R.id.spinnerModelos);
        Spinner spinnerCor = view.findViewById(R.id.spinnerCor);
        Spinner spinnerCambio = view.findViewById(R.id.spinnerCambio);

        Spinner spinnerAnoFab = view.findViewById(R.id.spinnerAnoFab);
        Spinner spinnerAnoMod = view.findViewById(R.id.spinnerAnoMod);
        Spinner spinnerComb = view.findViewById(R.id.spinnerCombustivel);
        Spinner spinnerTroca = view.findViewById(R.id.spinnerTroca);

        ImageView btnAvancar = view.findViewById(R.id.btnAvancar);
        EditText txtKm = view.findViewById(R.id.txtKm);





        marcaMap = new HashMap<>();
        modeloMap = new HashMap<>();

        List<String> nomesMarcas = new ArrayList<>();
        for (Marca marca : marcas) {
            nomesMarcas.add(marca.getNome());
            marcaMap.put(marca.getNome(), marca.getId());
        }

        ArrayAdapter<String> adapterMarcas = new ArrayAdapter<>(getContext(), R.layout.spinner_item, nomesMarcas);
        adapterMarcas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarcas.setAdapter(adapterMarcas);

        adapterModelos = new ArrayAdapter<>(getContext(), R.layout.spinner_item, nomesModelos);
        adapterModelos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModelos.setAdapter(adapterModelos);





        spinnerMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view1, int position, long id) {
                String selectedNome = (String) parent.getItemAtPosition(position);
                Integer selectedId = marcaMap.get(selectedNome);
                if (selectedId != null) {
                    modelos = dao.modelosMarca(selectedId);
                    nomesModelos.clear();
                    modeloMap.clear();
                    for (Modelo modelo : modelos) {
                        nomesModelos.add(modelo.getNome());
                        modeloMap.put(modelo.getNome(), modelo.getId());
                    }
                    adapterModelos.notifyDataSetChanged();
                    idMarca = selectedId;
                    //Toast.makeText(getContext(), "ID selecionado: " + selectedId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case where no item is selected
            }
        });

        spinnerModelos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view1, int position, long id) {
                String selectedNome = (String) parent.getItemAtPosition(position);
                Integer selectedId = modeloMap.get(selectedNome);
                if (selectedId != null) {
                    idModelo = selectedId;
                    //Toast.makeText(getContext(), "ID selecionado: " + selectedId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case where no item is selected
            }
        });




        String[] colors = {"Amarelo", "Amarelo-metálico", "Azul", "Azul-claro", "Azul-escuro", "Azul-marinho", "Azul-metálico", "Bege", "Branco", "Branco-neve", "Branco-pérola", "Bronze", "Cinza", "Cinza-chumbo", "Cinza-claro", "Cinza-escuro", "Cinza-metálico", "Dourado", "Dourado-metálico", "Laranja", "Laranja-metálico", "Marrom", "Marrom-metálico", "Preto", "Preto-fosco", "Preto-metálico", "Prata", "Prata-fosco", "Rosa", "Rosa-metálico", "Roxo", "Roxo-metálico", "Verde", "Verde-claro", "Verde-escuro", "Verde-metálico", "Vermelho", "Vermelho-claro", "Vermelho-escuro", "Vermelho-metálico"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                colors
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCor.setAdapter(adapter);
        spinnerCor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o item selecionado
                String selectedColor = (String) parent.getItemAtPosition(position);
                cor = selectedColor;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação quando nada é selecionado
            }
        });




        String[] tiposDeCambios = {"Câmbio Manual", "Câmbio Automático", "Câmbio Automatizado", "Câmbio de Dupla Embreagem", "Câmbio CVT", "Câmbio Sequencial", "Câmbio Semi-Automático", "Câmbio Tiptronic", "Câmbio i-MMD", "Câmbio DSG"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tiposDeCambios
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCambio.setAdapter(adapter2);
        spinnerCambio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o item selecionado
                String selected = (String) parent.getItemAtPosition(position);
                cambio = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação quando nada é selecionado
            }
        });



        String[] AceitaTroca = {"Sim", "Nao"};
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                AceitaTroca
        );
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTroca.setAdapter(adapter5);
        spinnerTroca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o item selecionado
                String selected = (String) parent.getItemAtPosition(position);
                troca = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação quando nada é selecionado
            }
        });





        int anoAtual = 2024; // Altere para o ano atual
        Integer[] anos = new Integer[150]; // Para anos de 2004 a 2024
        for (int i = 0; i < anos.length; i++) {
            anos[i] = anoAtual - i;
        }



        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, anos);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnoMod.setAdapter(adapter3);
        spinnerAnoMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o item selecionado
                int selected = (int) parent.getItemAtPosition(position);
                anoMod = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação quando nada é selecionado
            }
        });
        spinnerAnoFab.setAdapter(adapter3);
        spinnerAnoFab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o item selecionado
                int selected = (int) parent.getItemAtPosition(position);
                anoFab = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação quando nada é selecionado
            }
        });


        String[] tiposDeCombustivel = {"GNV", "Gasolina", "Etanol", "Elétrico", "Diesel", "Biodiesel", "Elétrico", "Flex", "Híbrido"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tiposDeCombustivel
        );
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComb.setAdapter(adapter4);
        spinnerComb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o item selecionado
                String selected = (String) parent.getItemAtPosition(position);
                combustivel = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ação quando nada é selecionado
            }
        });



        VideoView videoView = view.findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.anuncio_veicular);
        videoView.setVideoURI(videoUri);

        videoView.setOnCompletionListener(mp -> {
            // Congelar no último frame
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(requireActivity(), videoUri);
            Bitmap lastFrame = retriever.getFrameAtTime(-400); // Pega o último frame
            videoView.setBackground(new BitmapDrawable(getResources(), lastFrame));
            try {
                retriever.release(); // Chama o release no retriever
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        videoView.start();









        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kmText = txtKm.getText().toString().trim();
                if (kmText.isEmpty()) {
                    Toast.makeText(getContext(), "nulo", Toast.LENGTH_SHORT).show();
                } else {
                    kilometragem = kmText;
                    NovoAnuncio2Fragment novoFragment = NovoAnuncio2Fragment.newInstance(
                            idMarca, idModelo, anoFab, anoMod, combustivel, kilometragem, cor, cambio, troca, id
                    );

                    // Troque o fragmento
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, novoFragment); // Substitua pelo ID do seu container
                    fragmentTransaction.addToBackStack(null); // Para permitir voltar ao fragment anterior
                    fragmentTransaction.commit();
                }

            }
        });

















        return view;
    }











}