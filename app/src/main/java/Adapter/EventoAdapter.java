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
import Entity.Evento;
import Metodos.ImagemBase64;

public class EventoAdapter extends ArrayAdapter<Evento> {

    public EventoAdapter(Context context, List<Evento> persons) {
        super(context, 0, persons);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Evento person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_evento, parent, false);
        }




        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(person.getFoto());

        ImageView imagem = convertView.findViewById(R.id.imagem);
        TextView titulo = convertView.findViewById(R.id.txtTitulo);
        TextView data = convertView.findViewById(R.id.txtData);


        titulo.setText(person.getTitulo());
        data.setText(person.getData());
        imagem.setImageBitmap(bitmap);

        return convertView;
    }


}