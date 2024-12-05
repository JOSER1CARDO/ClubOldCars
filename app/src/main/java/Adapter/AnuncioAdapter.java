package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cluboldcars.R;

import java.util.List;

import Entity.Anuncio;
import Entity.Foto;
import Entity.FotoAnuncios;
import Metodos.ImagemBase64;

public class AnuncioAdapter extends ArrayAdapter<Anuncio> {

    public AnuncioAdapter(Context context, List<Anuncio> persons) {
        super(context, 0, persons);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Anuncio person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_anuncio, parent, false);
        }




        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(person.getFoto());

        TextView titulo = convertView.findViewById(R.id.txtTitulo);
        TextView ano = convertView.findViewById(R.id.txtAno);
        TextView preco = convertView.findViewById(R.id.txtPreco);
        ImageView imagem = convertView.findViewById(R.id.imgCarro);

        titulo.setText(person.getTitulo());
        ano.setText(person.getAnoFabricacao() + "/" + person.getAnoModelo());
        preco.setText(person.getPreco());
        imagem.setImageBitmap(bitmap);


        return convertView;
    }


}
