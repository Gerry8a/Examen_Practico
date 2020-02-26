package com.gerardochoa.examenpractico.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gerardochoa.examenpractico.R;
import com.gerardochoa.examenpractico.modelos.Persona;

import java.util.List;

public class AdaptadorPersona extends BaseAdapter {


    private Context context;
    private List<Persona> list;
    private int layout;

    public AdaptadorPersona(Context context, List<Persona> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Persona getItem(int position) {
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
            vh = new ViewHoder();
            vh.nombre = convertView.findViewById(R.id.adaptadorNombre);
            vh.edad = convertView.findViewById(R.id.adaptadorEdad);
            vh.nss = convertView.findViewById(R.id.adaptadorNss);
            vh.sexo = convertView.findViewById(R.id.adaptadorSexo);
            vh.peso = convertView.findViewById(R.id.adaptadorPeso);
            vh.altura = convertView.findViewById(R.id.adaptadorAltura);
            vh.estado = convertView.findViewById(R.id.adaptadorEstado);
            convertView.setTag(vh);
        } else {
            vh = (ViewHoder) convertView.getTag();
        }

        Persona persona  = list.get(position);
        vh.nombre.setText(persona.getNombre());
        vh.edad.setText(String.valueOf(persona.getEdad()));
        vh.nss.setText(persona.getNSS());
        vh.sexo.setText(persona.getSexo());
        vh.peso.setText(String.valueOf(persona.getPeso()));
        vh.altura.setText(String.valueOf(persona.getAltura()));
        vh.estado.setText(persona.getEstado());



        return convertView;
    }

    public class ViewHoder{
        TextView nombre, edad, nss, sexo, peso, altura, estado;

    }



}
