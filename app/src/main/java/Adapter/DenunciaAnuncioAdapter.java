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
import Entity.DenunciaAnuncio;
import Metodos.ImagemBase64;

public class DenunciaAnuncioAdapter extends ArrayAdapter<DenunciaAnuncio> {

    public DenunciaAnuncioAdapter(Context context, List<DenunciaAnuncio> persons) {
        super(context, 0, persons);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        DenunciaAnuncio person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_anuncio, parent, false);
        }




        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(person.getAnuncio().getFoto());

        TextView titulo = convertView.findViewById(R.id.txtTitulo);
        TextView ano = convertView.findViewById(R.id.txtAno);
        TextView preco = convertView.findViewById(R.id.txtPreco);
        ImageView imagem = convertView.findViewById(R.id.imgCarro);

        titulo.setText(person.getAnuncio().getTitulo());
        ano.setText(person.getAnuncio().getAnoFabricacao() + "/" + person.getAnuncio().getAnoModelo());
        preco.setText(person.getAnuncio().getPreco());
        imagem.setImageBitmap(bitmap);


        return convertView;
    }


}
