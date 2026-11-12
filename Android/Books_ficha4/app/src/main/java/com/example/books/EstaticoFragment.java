package com.example.books;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class EstaticoFragment extends Fragment {


    public EstaticoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estatico, container, false);

        ImageView ivCapa = view.findViewById(R.id.ivCapa);
        TextView tvTitulo = view.findViewById(R.id.tvTitulo);
        TextView tvSerie = view.findViewById(R.id.tvSerie);
        TextView tvAutor = view.findViewById(R.id.tvAutor);
        TextView tvAno = view.findViewById(R.id.tvAno);

        ivCapa.setImageResource(R.drawable.programarandroid1);
        tvTitulo.setText(R.string.livro_titulo);
        tvSerie.setText(R.string.livro_serie);
        tvAutor.setText(R.string.livro_autor);
        tvAno.setText(R.string.livro_ano);

        return view;
    }
}