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

import Entity.Evento;
import Entity.Usuario;
import Metodos.ImagemBase64;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    public UsuarioAdapter(Context context, List<Usuario> persons) {
        super(context, 0, persons);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Usuario person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_usuario, parent, false);
        }




        Bitmap bitmap = ImagemBase64.decodeBase64ToBitmap(person.getFoto());

        ImageView imagem = convertView.findViewById(R.id.img_usuario);
        TextView titulo = convertView.findViewById(R.id.txtNome);
        TextView data = convertView.findViewById(R.id.txtEmail);


        titulo.setText(person.getNome());
        data.setText(person.getEmail());
        imagem.setImageBitmap(bitmap);

        return convertView;
    }




}
