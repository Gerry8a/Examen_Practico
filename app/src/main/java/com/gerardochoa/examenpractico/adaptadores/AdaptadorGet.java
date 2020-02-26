package com.gerardochoa.examenpractico.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gerardochoa.examenpractico.R;
import com.gerardochoa.examenpractico.modelos.Cliente;
import com.gerardochoa.examenpractico.modelos.Persona;

import java.util.List;

public class AdaptadorGet extends BaseAdapter {

    private Context context;
    private List<Cliente> list;
    private int layout;

    public AdaptadorGet(Context context, List<Cliente> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Cliente getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder vh;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new AdaptadorGet.ViewHoder();
            vh.nombre = convertView.findViewById(R.id.getNombre);
            vh.apellidos = convertView.findViewById(R.id.getApellidos);
            vh.nombreUsuario = convertView.findViewById(R.id.getNombreUsuario);
            vh.correo = convertView.findViewById(R.id.getCorreo);
            vh.password = convertView.findViewById(R.id.getPassword);

            convertView.setTag(vh);
        } else {
            vh = (ViewHoder) convertView.getTag();
        }

        Cliente cliente = list.get(position);
        vh.nombre.setText(cliente.getNombre());
        vh.apellidos.setText(cliente.getApellidos());
        vh.nombreUsuario.setText(cliente.getNombreUsuario());
        vh.correo.setText(cliente.getCorreo());
        vh.password.setText(cliente.getPassword());

        return convertView;
    }

    public class ViewHoder{
        TextView nombre, apellidos, correo, password, nombreUsuario;

    }
}
