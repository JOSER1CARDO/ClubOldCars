package com.example.cluboldcars;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.cluboldcars.fragments.AdmAceitarEventosFragment;
import com.example.cluboldcars.fragments.AnunciosFragment;
import com.example.cluboldcars.fragments.DenunciasAnunciosFragment;
import com.example.cluboldcars.fragments.EventosFragment;
import com.example.cluboldcars.fragments.HomeAdmFragment;
import com.example.cluboldcars.fragments.HomeFragment;
import com.example.cluboldcars.fragments.MeusAnunciosFragment;
import com.example.cluboldcars.fragments.MeusEventosFragment;
import com.example.cluboldcars.fragments.NovoAdmFragment;
import com.example.cluboldcars.fragments.NovoAnuncioFragment;
import com.example.cluboldcars.fragments.NovoEventoFragment;
import com.google.android.material.navigation.NavigationView;

import DAO.DAO;
import Entity.Usuario;
import Metodos.ImagemBase64;

public class Home extends AppCompatActivity {

    DrawerLayout drawerLayout;

    ImageButton buttoDrawerToggle;

    NavigationView navigationView;

    Usuario usuario = new Usuario();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        drawerLayout  = findViewById(R.id.drawerLayout);
        buttoDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);



        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");


        View headerView = navigationView.getHeaderView(0);
        ImageView userImage = headerView.findViewById(R.id.userImage);
        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(usuario.getFoto());
        userImage.setImageBitmap(bitmap);

        TextView nome = headerView.findViewById(R.id.txtNome);
        nome.setText(usuario.getNome());

        if (savedInstanceState == null) {
            if((usuario.getAdm() == 1) || (usuario.getAdm() == 2))
            {
                HomeFragment homeFragment = HomeFragment.newInstance(usuario);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
            }
        }

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment perfilFragment = HomeAdmFragment.newInstance(usuario.getId());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, perfilFragment)
                        .addToBackStack(null) // Opcional: Adiciona a transação à pilha de backstack
                        .commit();
                drawerLayout.close(); // Fecha o drawer após a transação do fragmento
            }
        });



        buttoDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarDadosUsuario();
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();



                if(itemId == R.id.navHome1){
                    atualizarDadosUsuario();
                    fragment = HomeFragment.newInstance(usuario);
                    Toast.makeText(Home.this, "Home selecionado", Toast.LENGTH_SHORT).show();
                }

                if(itemId == R.id.navHome){
                    atualizarDadosUsuario();
                    fragment = NovoAnuncioFragment.newInstance(usuario.getId());
                    Toast.makeText(Home.this, "Novo anuncio selecionado", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navNovoEvento) {
                    fragment = NovoEventoFragment.newInstance(usuario.getId());
                    Toast.makeText(Home.this, "Novo Evento", Toast.LENGTH_SHORT).show();
                }



                if(itemId == R.id.anuncios){
                    fragment = AnunciosFragment.newInstance(usuario.getId());
                    Toast.makeText(Home.this, "Anuncio selecionado", Toast.LENGTH_SHORT).show();
                }

                if(itemId == R.id.navEventos){
                    fragment = EventosFragment.newInstance(usuario.getId());
                    Toast.makeText(Home.this, "Eventos", Toast.LENGTH_SHORT).show();
                }

                ////////////////////////////////

                if(itemId == R.id.navMeusEventos){
                    fragment = MeusEventosFragment.newInstance(usuario.getId());
                    Toast.makeText(Home.this, "Meus Eventos", Toast.LENGTH_SHORT).show();
                }

                if(itemId == R.id.navMeusAnuncios){
                    fragment = MeusAnunciosFragment.newInstance(usuario.getId());
                    Toast.makeText(Home.this, "Meus anuncios", Toast.LENGTH_SHORT).show();
                }



                ///////////////////////////////////
                if(itemId == R.id.navAceitarEventos){
                    if(usuario.getAdm() == 1){
                        fragment = new AdmAceitarEventosFragment();

                    }else{
                        Toast.makeText(Home.this, "Apenas Administradores", Toast.LENGTH_SHORT).show();
                    }

                }
                if(itemId == R.id.navAdm){
                    if(usuario.getAdm() == 1){
                        fragment = new NovoAdmFragment();

                    }else{
                        Toast.makeText(Home.this, "Apenas Administradores", Toast.LENGTH_SHORT).show();
                    }

                }




                if(itemId == R.id.navRemoverAnuncios){
                    if(usuario.getAdm() == 1){
                        fragment = new DenunciasAnunciosFragment();
                        Toast.makeText(Home.this, "Denuncias", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(Home.this, "Apenas Administradores", Toast.LENGTH_SHORT).show();
                    }

                }


                drawerLayout.close();

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null) // Opcional: Adiciona a transação à pilha de backstack
                            .commit();
                }

                return false;
            }
        });




    }
    private void atualizarDadosUsuario() {
        DAO dao = new DAO(getApplicationContext());
        usuario = dao.usuarioId(usuario.getId());
        View headerView = navigationView.getHeaderView(0);
        ImageView userImage = headerView.findViewById(R.id.userImage);
        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(usuario.getFoto());
        userImage.setImageBitmap(bitmap);

        TextView nome = headerView.findViewById(R.id.txtNome);
        nome.setText(usuario.getNome());
    }


}